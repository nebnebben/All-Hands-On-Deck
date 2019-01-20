package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CollegeVisual extends ScreenAdapter {
    //data/logic parameters
    private Game game;
    private ScreenAdapter parent;
    private GameLogic gameLogic;
    private CollegeNode collegeNode;
    final private ScreenAdapter clickThis;

    //visual parameters
    private Label nameLabel;
    private Label.LabelStyle resourceStyle;
    private TextButton cardButton;
    private TextButton supplyButton;
    private TextButton attackButton;
    private TextButton exitButton;
    private TextButton.TextButtonStyle optionStyle;
    private Stage collegeStage;

    public CollegeVisual(Game game, ScreenAdapter parent, GameLogic gameLogic, CollegeNode collegeNode){
        this.game = game;
        this.parent = parent;
        this.gameLogic = gameLogic;
        this.collegeNode = collegeNode;
        clickThis = parent;
        collegeStage = new Stage(new ScreenViewport());

        //initiliazes the standard TextButtonStyle for the options to follow
        optionStyle = new TextButton.TextButtonStyle();
        optionStyle.fontColor = Color.BLACK;
        optionStyle.downFontColor = Color.BLUE;
        optionStyle.font = new BitmapFont();

        //name style
        resourceStyle = new Label.LabelStyle();
        resourceStyle.fontColor = Color.RED;
        resourceStyle.font = new BitmapFont();

        //create builds the actual visuals
        create();
    }

    private void create(){
        //displays college name
        nameLabel = new Label("name", resourceStyle);
        nameLabel.setText(collegeNode.getCollegeName()+", Allied: "+Integer.toString(collegeNode.getCollegeStatus()));
        nameLabel.setSize(100,12);
        nameLabel.setPosition(10, Gdx.graphics.getHeight()-15);
        nameLabel.setAlignment(Align.left);
        collegeStage.addActor(nameLabel);

        //buys a card
        cardButton = new TextButton("card", optionStyle);
        cardButton.setText("1. Buy card");
        cardButton.setSize(100,12);
        cardButton.setPosition(20, Gdx.graphics.getHeight() - 40);
        cardButton.getLabel().setAlignment(Align.left);
        cardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //if the player can afford the card, buy it
                if (gameLogic.currentGold >= collegeNode.buyCard().getGoldCost()){
                    gameLogic.getPlayer().getPlayerShip().addCard(collegeNode.buyCard());
                    gameLogic.currentGold -= collegeNode.buyCard().getGoldCost();
                }
            }
        });
        collegeStage.addActor(cardButton);

        //Buys supplies
        createSupplyButton();
        //attacks department
        createAttackButton();
        //exits
        createExitButton();
    }

    //buys supplies with gold
    private void createSupplyButton(){
        //initiliazes a constant supply cost
        final int supplyCost = 10;
        supplyButton = new TextButton("supply", optionStyle);
        supplyButton.setText("2. Buy Supplies");
        supplyButton.setSize(100,12);
        supplyButton.setPosition(20, Gdx.graphics.getHeight() - 55);
        supplyButton.getLabel().setAlignment(Align.left);
        supplyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //if it can afford supplies, buy it
                if (gameLogic.currentGold >= supplyCost){
                    gameLogic.addSupplies(1);
                    gameLogic.currentGold -= supplyCost;
                }
            }
        });
        collegeStage.addActor(supplyButton);
    }

    //attacks the college, which triggers a unique battle
    private void createAttackButton(){
        attackButton = new TextButton("attack",optionStyle);
        attackButton.setText("3. Attack college");
        attackButton.setSize(100, 12);
        attackButton.setPosition(20, Gdx.graphics.getHeight() - 70);
        //the label for the button needs to be aligned to the left, defaults to the center
        attackButton.getLabel().setAlignment(Align.left);
        //click listener to allow the options to be clicked and as such chosen
        attackButton.addListener(new ClickListener()
        {
            //on left click up - can only follow after a left click down
            @Override
            public void clicked(InputEvent event, float x, float y){
                //can't attack an already allied college
                if (collegeNode.getCollegeStatus() != 1) {
                    Ship enemyShip = collegeNode.getBossShip();
                    dispose();
                    game.setScreen(new BattleModeGraphics(game, clickThis, gameLogic, enemyShip));
                    collegeNode.setCollegeStatus(1);
                }
            }
        });
        collegeStage.addActor(attackButton);
    }

    //creates the exit button which switches back to the parent stage
    private void createExitButton(){
        exitButton = new TextButton("exit", optionStyle);
        exitButton.setText("4. Exit");
        exitButton.setSize(100,12);
        exitButton.setPosition(20, Gdx.graphics.getHeight() - 85);
        exitButton.getLabel().setAlignment(Align.left);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                game.setScreen(parent);
            }
        });
        collegeStage.addActor(exitButton);
    }

    @Override
    public void render(float delta){
        Gdx.input.setInputProcessor(collegeStage);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        collegeStage.act();
        collegeStage.draw();
    }

    @Override
    public void dispose(){
        collegeStage.dispose();
    }
}
