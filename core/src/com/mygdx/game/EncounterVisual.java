package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class EncounterVisual extends ScreenAdapter {
    //data/logic parameters
    private Game game;
    private ScreenAdapter parent;
    private GameLogic gameLogic;
    private Encounter encounter;

    //visual parameters
    private TextButton[] optionButtons;
    private Label descriptionLabel;
    private Label.LabelStyle resourceStyle;
    private TextButton.TextButtonStyle optionStyle;
    private Stage encounterStage;

    public EncounterVisual(Game game, ScreenAdapter parent, GameLogic gameLogic, Encounter encounter){
        this.game = game;
        this.parent = parent;
        this.encounter = encounter;
        this.gameLogic = gameLogic; //brought in to apply effects on the game
        //initializes optionButtons, individual buttons initialized together
        optionButtons = new TextButton[encounter.getOptions().length];


        //initializes standard labelstyle for the description to follow
        resourceStyle = new Label.LabelStyle();
        resourceStyle.fontColor = Color.RED;
        resourceStyle.font = new BitmapFont();
        encounterStage = new Stage(new ScreenViewport());

        //initiliazes the standard TextButtonStyle for the options to follow
        optionStyle = new TextButton.TextButtonStyle();
        optionStyle.fontColor = Color.BLACK;
        optionStyle.downFontColor = Color.BLUE;
        optionStyle.font = new BitmapFont();

        create();
    }

    public void create(){
        //show description at the top of the screen, then the list of options
        encounterStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(encounterStage);
        //description label
        descriptionLabel  = new Label("description",resourceStyle);
        descriptionLabel.setText(encounter.getDescription());
        descriptionLabel.setSize(10, 12);
        descriptionLabel.setPosition(0,Gdx.graphics.getHeight() - 14);
        descriptionLabel.setAlignment(Align.topLeft);
        encounterStage.addActor(descriptionLabel);

        createOptionButtons();
    }

    //creates the option buttons
    private void createOptionButtons(){
        //go through all options creating labels for each one
        for (int i=0;i<encounter.getOptions().length;i++){
            //offset in position between options
            int optionOffset = i*14;

            //gives each button an independent index which can be accessed by click listener - determines effect
            final int index = i;
            //option labels need to be buttons as well as labels to allow choices to be made by the user
            optionButtons[i]  = new TextButton("option"+Integer.toString(i),optionStyle);
            optionButtons[i].setText(encounter.getOptions()[i]);
            optionButtons[i].setSize(100, 12);
            //base offset for all options is 20 from the top, then an additional offset based on the number of options
            optionButtons[i].setPosition(20,Gdx.graphics.getHeight() - (40 + optionOffset) );
            //the label for the button needs to be aligned to the left, defaults to the center
            optionButtons[i].getLabel().setAlignment(Align.left);
            //click listener to allow the options to be clicked and as such chosen
            optionButtons[i].addListener(new ClickListener()
            {
                //on left click up - can only follow after a left click down
                @Override
                public void clicked(InputEvent event,float x, float y){
                    //every encounter has a score which is added on, then the actual choice is interpreted
                    gameLogic.addScore(encounter.getScore());
                    interpretEffect(encounter.getEffects()[index]);
                }
            });
            encounterStage.addActor(optionButtons[i]);
        }
    }

    //looks at a chosen effect and carries out said effect, then switches screen away from encounterVisual
    private void interpretEffect(String effect){
        String[] details = effect.split("-");
        //first character denotes effect type, rest following - denotes relevant data
        switch (details[0].charAt(0)) {
            //battle, details[1] contains details for the enemy ship
            case 'B':
                //insert battle change here
            //change supplies - details [1] indicates whether to add or subtract, details [2] by how much
            case 'S':
                if (details[1].equals("L")) {
                    gameLogic.addSupplies(0 - Integer.valueOf(details[2]));
                    dispose();
                    game.setScreen(parent);
                } else if (details[1].equals("G")) {
                    gameLogic.addSupplies(Integer.valueOf(details[2]));
                    dispose();
                    game.setScreen(parent);
                }
        }
    }
    //overriden methods
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        encounterStage.act();
        encounterStage.draw();
    }

    @Override
    public void dispose(){
        encounterStage.dispose();
    }


}
