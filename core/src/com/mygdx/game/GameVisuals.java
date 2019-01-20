package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/*
Break down into 3 sections:
    - Top bar containing relevant gameLogic information such as the ability to see deck and gameLogic state stats like gold and turn
    - Node map containing an image of all the nodes as well as the ability to click on nodes in order to traverse - button
    - Background
 */
public class GameVisuals extends ScreenAdapter {
    private Game game;
    private Stage mainStage; //stage for the majority of the gameLogic - pauses when encounter pop ups
    private GameLogic gameLogic; //actual gameLogic controller
    private Label.LabelStyle resourceStyle; //labelstyle for all labels
    private TextButton[] nodeButtons; //map of buttons - ID matches that of the gameLogic nodeList
    //top bar labels
    private Label turnLabel;
    private Label goldLabel;
    private Label supplyLabel;
    private Label scoreLabel;
    private TextButton shipButton; //allows for the ship to be viewed
    private TextButton.TextButtonStyle buttonStyle;
    private TextButton.TextButtonStyle clickStyle;
    private TextButton.TextButtonStyle curNodeStyle;
    private Encounter curEncounter;

    public GameVisuals(Game game){
        this.game = game;
        //builds visuals
        create();
    }
    public void create () {
        //init variables
        SpriteBatch batch = new SpriteBatch();
        gameLogic = new GameLogic(5,2,"Alcuin","p1");
        mainStage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(mainStage); //allows the mainStage and only the mainStage to have inputs

        //scaling guides
        int GuideRows = 12;
        int GuideCols = 12;
        int row_height = Gdx.graphics.getHeight()/GuideRows;
        int col_width = Gdx.graphics.getWidth()/GuideCols;

        //creating button styles for different nodes (current, neighbor, neither
        //non-neighbor non-current
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.RED;
        buttonStyle.downFontColor = Color.YELLOW;
        buttonStyle.font = new BitmapFont();

        //neighbor button
        clickStyle = new TextButton.TextButtonStyle();
        clickStyle.fontColor = Color.GREEN;
        clickStyle.downFontColor = Color.YELLOW;
        clickStyle.font = new BitmapFont();

        //current button
        curNodeStyle = new TextButton.TextButtonStyle();
        curNodeStyle.fontColor = Color.BLUE;
        curNodeStyle.downFontColor = Color.YELLOW;
        curNodeStyle.font = new BitmapFont();

        //initiliazation of the basic ui elements of the gameLogic
        addBackground(); //adds the background
        createTopLabels(row_height, col_width); //creates the set of top labels
        createNodeMap(3,2); //creates the nodeMap with x,y coordinates scaled to match window


    }

    //render includes necessary functionality used when moving through screens because it is called on screen change
    @Override
    public void render(float delta) {

        //gameLogic data may have changed on screen change, so the top labels have to be updated
        updateTopLabels();

        //actual render functionality
        //clears image
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //draws visuals
        mainStage.act();
        mainStage.draw();


        //when switching between screens, the input stage needs to be reset to the stage on this screen
        Gdx.input.setInputProcessor(mainStage);
        //switching between screens may have also resulted in a loss/win
        Boolean isWon = gameLogic.checkWin();
        Boolean isLost = gameLogic.checkLoss();
        if (isWon || isLost){
            endGame(isWon);
        }


    }

    @Override
    public void dispose () {
        mainStage.dispose();
    }

    //adds background
    public void addBackground() {
        //sets background texture
        Texture texture = new Texture(Gdx.files.internal("space.png"));

        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Image background = new Image(textureRegion);
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background.setPosition(0,0);
        mainStage.addActor(background);


    }

    public void createNodeMap(int xScale, int yScale){
        //variable init
        float nodeSize = 10;
        int nodeListLength = gameLogic.getNodeList().length;
        int x;
        int y;
        TextButton.TextButtonStyle aStyle;
        Node[] nodeList = gameLogic.getNodeList();

        //iterates through all nodes creating buttons and connecting lines
        nodeButtons = new TextButton[nodeListLength];
        for (int i=0;i < nodeListLength; i++){
            //scaled x and y values
            x = nodeList[i].getX() * xScale;
            y = nodeList[i].getY() * yScale;
            //gives each button an independent index which can be accessed by click listener - determines neighbor
            final int index = i;

            //constant screen parent for use within the button
            final ScreenAdapter parent = this;

            //node textButton creation
            //if the node is a neighbor node to the current node it has a different style as defined above
            if (gameLogic.getNeighborNodes().contains(i)){
                aStyle = clickStyle;
            } else if (gameLogic.getCurrentNode() != i) {
                aStyle = buttonStyle;
            } else {
                aStyle = curNodeStyle;
            }
            nodeButtons[i] = new TextButton(String.valueOf(i),aStyle);
            //map symbol dependent on node type
            if (nodeList[i].getNodeType().equals("College")){
                nodeButtons[i].setText("C");
            } else if (nodeList[i].getNodeType().equals("Department")){
                nodeButtons[i].setText("D");
            } else {
                nodeButtons[i].setText("0");
            }
            nodeButtons[i].setSize(nodeSize,nodeSize);
            nodeButtons[i].setPosition(x,y);
            nodeButtons[i].setZIndex(i);
            //listener is what allows the button to be clicked
            nodeButtons[i].addListener(new ClickListener()
            {
                //on left click up - can only follow after a left click down
                @Override
                public void clicked(InputEvent event,float x, float y){
                    //if it is one of the neighbor Nodes, turn change. If it is the current Node, check for college/department
                    if (gameLogic.getNeighborNodes().contains(index)){
                        turnChange(index);
                    } else if (gameLogic.getCurrentNode() == index){
                        //if the node is a college or department, screen change to the college/department visuals
                        if (gameLogic.getNodeList()[index].getNodeType().equals("College")){
                            CollegeNode node = (CollegeNode) gameLogic.getNodeList()[index];
                            game.setScreen(new CollegeVisual(game, parent , gameLogic, node));
                        } else if (gameLogic.getNodeList()[index].getNodeType().equals("Department")){
                            DepartmentNode node = (DepartmentNode) gameLogic.getNodeList()[index];
                            game.setScreen(new DepartmentVisual(game, parent, gameLogic, node));
                        }
                    }
                }
            });
            mainStage.addActor(nodeButtons[i]);

            //lines not currently working
            /*
            int iterConnect = nodeList[i].getConnectnodes().size();
            Node[] connected = new Node[iterConnect];
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            for (int j=0; j < iterConnect; j++){
                connected[j] = nodeList[nodeList[i].getConnectnodes().get(j)];
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(1,0,0,1);
                shapeRenderer.rectLine(nodeList[i].getX()*3,nodeList[i].getY()*2,connected[j].getX()*3,connected[j].getY()*2,5);
                shapeRenderer.end();
            }
            */

        }
    }

    public void createTopLabels(int row_height, int col_width){
        //initilizes standard labelstyle for the top labels to follow
        resourceStyle = new Label.LabelStyle();
        resourceStyle.fontColor = Color.RED;
        resourceStyle.font = new BitmapFont();

        //all labels follow this basic layout
        turnLabel = new Label("turn",resourceStyle);
        turnLabel.setText("Current Turn: " + String.valueOf(gameLogic.getCurrentTurn()));
        turnLabel.setSize(col_width,row_height);
        turnLabel.setPosition(Gdx.graphics.getWidth()-3*col_width,Gdx.graphics.getHeight()-row_height);
        turnLabel.setAlignment(Align.topLeft);
        mainStage.addActor(turnLabel);

        goldLabel = new Label("gold",resourceStyle);
        goldLabel.setText("Current Gold: " + String.valueOf(gameLogic.currentGold));
        goldLabel.setSize(col_width,row_height);
        goldLabel.setPosition(Gdx.graphics.getWidth()- 6*col_width,Gdx.graphics.getHeight()-row_height);
        goldLabel.setAlignment(Align.topLeft);
        mainStage.addActor(goldLabel);

        supplyLabel = new Label("supplies",resourceStyle);
        supplyLabel.setText("Current Supplies: " + String.valueOf(gameLogic.getCurrentSupplies()));
        supplyLabel.setSize(col_width,row_height);
        supplyLabel.setPosition(Gdx.graphics.getWidth()-9*col_width,Gdx.graphics.getHeight()-row_height);
        supplyLabel.setAlignment(Align.topLeft);
        mainStage.addActor(supplyLabel);

        scoreLabel = new Label("score",resourceStyle);
        scoreLabel.setText("Current Score: " + String.valueOf(gameLogic.getScore()));
        scoreLabel.setSize(col_width, row_height);
        scoreLabel.setPosition(Gdx.graphics.getWidth() - 12*col_width, Gdx.graphics.getHeight()-row_height);
        scoreLabel.setAlignment(Align.topLeft);
        mainStage.addActor(scoreLabel);

        final ScreenAdapter parent = this;
        shipButton = new TextButton("ship", buttonStyle);
        shipButton.setText("View ship & objectives");
        shipButton.setSize(col_width, row_height);
        shipButton.setPosition(10, Gdx.graphics.getHeight()-row_height-14);
        shipButton.getLabel().setAlignment(Align.topLeft);
        shipButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new ShipVisual(game,parent,gameLogic));
            }
        });
        mainStage.addActor(shipButton);
    }

    //resets the text of all the top labels to the most recent values from gameLogic
    public void updateTopLabels(){
        turnLabel.setText("Current Turn: " + String.valueOf(gameLogic.getCurrentTurn()));
        goldLabel.setText("Current Gold: " + String.valueOf(gameLogic.currentGold));
        supplyLabel.setText("Current Supplies: " + String.valueOf(gameLogic.getCurrentSupplies()));
        scoreLabel.setText("Current Score: " + String.valueOf(gameLogic.getScore()));
    }

    //called when a viable neighbor node is pressed - goes through turn change
    public void turnChange(int targetNode){
        //init values including the encounter triggered
        boolean isLost;
        boolean isWon;
        Encounter encounter;

        //traverses the node in gameLogic controller then sets encounter node to the new node
        gameLogic.traverseNode(targetNode);
        encounter = gameLogic.getNodeList()[gameLogic.getCurrentNode()].giveEncounter();

        //does completion check before encounter can trigger
        isLost = gameLogic.checkLoss();
        isWon = gameLogic.checkWin();
        //if either is true then the gameLogic is complete, and it ends the gameLogic using end gameLogic
        if (isLost || isWon) {
            endGame(isWon);
        }
        //triggers screen change based on whether the node is a college/department node or not
        if (gameLogic.getNodeList()[targetNode].getNodeType().equals("College")){
            CollegeNode node = (CollegeNode) gameLogic.getNodeList()[targetNode];
            game.setScreen(new CollegeVisual(game, this, gameLogic, node));
        } else if (gameLogic.getNodeList()[targetNode].getNodeType().equals("Department")) {
            DepartmentNode node = (DepartmentNode) gameLogic.getNodeList()[targetNode];
            game.setScreen(new DepartmentVisual(game,this, gameLogic, node));
        } else {
            //if the node was not a department or a college, it triggers and encounter
            game.setScreen(new EncounterVisual(game, this, gameLogic, encounter));
        }


        //checks whether the gameLogic is won or lost again - if it isn't the visuals are updated and turn change is complete
        if (isLost || isWon) {
            endGame(isWon);
        } else {
            updateTopLabels();
            updateNodeMap();
        }


    }

    //goes through and updates the button style of the node map.
    public void updateNodeMap() {
        for (int i=0; i<gameLogic.getNodeList().length; i++){
            if (gameLogic.getCurrentNode() == i){
                nodeButtons[i].setStyle(curNodeStyle);
            } else if (gameLogic.getNeighborNodes().contains(i)) {
                nodeButtons[i].setStyle(clickStyle);
            } else {
                nodeButtons[i].setStyle(buttonStyle);
            }
        }
    }

    //switches to the GameEndVisual screen
    public void endGame (Boolean win) {
        game.setScreen(new GameEndVisual(game, this, win, gameLogic.getScore()));
    }
}
