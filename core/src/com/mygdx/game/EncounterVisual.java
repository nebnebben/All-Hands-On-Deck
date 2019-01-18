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

import java.util.ArrayList;

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

        //create builds the actual visuals
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
            optionButtons[i].setPosition(optionButtons[i].getText().length()*2,Gdx.graphics.getHeight() - (70 + optionOffset) );
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
        //+ allows for multiple effects to be stored in the same effect
        System.out.println(effect);
        String[] sumEffects = effect.split("&");
        System.out.println(sumEffects[0]);
        for (String e:sumEffects) {
            String[] details = e.split("-");
            //first character denotes effect type, rest following - denotes relevant data
            switch (details[0].charAt(0)) {
                //battle, details[1] contains details for the enemy ship
                case 'B':
                    //initializes a ship to be fought, then switches to the battle screen with its parent set as GameVisuals
                    //details of the ship to be fought are stored, but the ships deck is stored as cards with / separation
                    int shipMaxHealth = Integer.parseInt(details[1]);
                    int shipMana = Integer.parseInt(details[2]);
                    int shipRegen = Integer.parseInt(details[3]);
                    int shipPoints = Integer.parseInt(details[4]);
                    int shipGold = Integer.parseInt(details[5]);
                    String shipDeckString = details[6];
                    String[] shipCards = shipDeckString.split("/");
                    ArrayList<Card> shipDeck = new ArrayList<Card>();
                    for (int i = 0; i < shipCards.length; i++) {
                        //individual cards have their details split between periods
                        String[] cardDetails = shipCards[i].split(",");
                        String cardName = cardDetails[0];
                        String cardDesc = cardDetails[1];
                        int cardShop = Integer.parseInt(cardDetails[2]);
                        int cardGold = Integer.parseInt(cardDetails[3]);
                        int cardMana = Integer.parseInt(cardDetails[4]);
                        String cardEffect = cardDetails[5];
                        //then instantiated and added to the deck
                        Card inCard = new Card(cardName, cardDesc, cardShop, cardGold, cardMana, cardEffect);
                        shipDeck.add(inCard);
                    }
                    Ship enemyShip = new Ship(shipMaxHealth, shipMaxHealth, shipMana, shipRegen, shipPoints, shipGold, shipDeck, Boolean.FALSE);
                    dispose();
                    game.setScreen(new BattleModeGraphics(game, parent, gameLogic, enemyShip));
                    break;
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
                    break;
                    //change in health
                case 'H':
                    //loses health
                    int health = gameLogic.getPlayer().getPlayerShip().getCurrentHealth();
                    if (details[1].equals("L")) {
                        health -= Integer.parseInt(details[2]);
                        gameLogic.getPlayer().getPlayerShip().setHealth(health);
                        dispose();
                        game.setScreen(parent);
                        //gains health
                    } else if (details[1].equals("G")) {
                        health += Integer.parseInt(details[2]);
                        //if health exceeds maximum health, set health to max health. Otherwise, sethealth to health
                        if (health > gameLogic.getPlayer().getPlayerShip().getTotalHealth()) {
                            gameLogic.getPlayer().getPlayerShip().setHealth(gameLogic.getPlayer().getPlayerShip().getTotalHealth());
                            dispose();
                            game.setScreen(parent);
                        } else {
                            gameLogic.getPlayer().getPlayerShip().setHealth(health);
                            dispose();
                            game.setScreen(parent);
                        }
                        //maxes health
                    } else if (details[1].equals("M")) {
                        health = gameLogic.getPlayer().getPlayerShip().getTotalHealth();
                        gameLogic.getPlayer().getPlayerShip().setHealth(health);
                        dispose();
                        game.setScreen(parent);
                    }
                    break;
                    //gold
                case 'G':
                    //lose gold
                    if (details[1].equals("L")) {
                        gameLogic.currentGold -= Integer.parseInt(details[2]);
                    } else if (details[1].equals("G")) {
                        gameLogic.currentGold += Integer.parseInt(details[2]);
                    }
                    dispose();
                    game.setScreen(parent);
                    break;
                //max health
                case 'I':
                    int totHealth = gameLogic.getPlayer().getPlayerShip().getTotalHealth();
                    //lose max health
                    if (details[1].equals("L")){
                        totHealth -= Integer.parseInt(details[2]);
                        gameLogic.getPlayer().getPlayerShip().setTotalHealth(totHealth);
                    //gain total health
                    } else if (details[2].equals("G")){
                        totHealth += Integer.parseInt(details[2]);
                        gameLogic.getPlayer().getPlayerShip().setTotalHealth(totHealth);
                    }
                    dispose();
                    game.setScreen(parent);
                    break;
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
