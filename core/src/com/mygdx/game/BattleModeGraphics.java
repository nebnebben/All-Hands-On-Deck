package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


//Json attempts. Delete when done. -----------------
//import org.json.JSONArray;
//import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Json attemps. Delete when done.---------------------

public class BattleModeGraphics extends ScreenAdapter {
    protected Screen screen;
    private Game game;
    private ScreenAdapter parent;
    private GameLogic gameLogic;
    private Texture background;
    private TextButton exitButton;
    private TextButton.TextButtonStyle optionStyle;
    SpriteBatch batch;
    BitmapFont playerFont;
    BitmapFont enemyFont;
    BitmapFont pointsFont;
    BitmapFont goldFont;
    Stage stage;
    Sprite sprite;



    BattleMode battleMode;
    private Ship playerShip;
    private Ship enemyShip;

    //temp?
    private Integer playerCurrentMana;
    private Integer playerTotalMana;
    private Integer playerCurrentHealth;
    private Integer playerTotalHealth;
    private Integer enemyCurrentMana;
    private Integer enemyTotalMana;
    private Integer enemyCurrentHealth;
    private Integer enemyTotalHealth;

    private Boolean gameOver; //If playerShip or enemyShip are dead.
    //temp?


    public class PlayerShip extends Actor {
        String filename = "Ship2_Undamaged.png";
        Texture texture = new Texture(Gdx.files.internal(filename));
        public boolean started = false;



        public PlayerShip() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());}

        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(texture, this.getX(), getY(),150,150);
        }

        @Override
        public void act(float delta){ //Changes sprite depending on how much damage the ship has taken
            float hpPercentage = battleMode.getShipHealthPercentage("player");
            if(hpPercentage <= 0 && !(filename == "Ship2_destroyed.jpg")){
                filename = "Ship2_destroyed.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if(hpPercentage <= 80 && hpPercentage > 60 && !(filename == "Ship2_damage1.png")){
                filename = "Ship2_damage1.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if(hpPercentage <= 60 && hpPercentage > 40 && !(filename == "Ship2_damage2.png")){
                filename = "Ship2_damage2.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if(hpPercentage <= 40 && hpPercentage > 20 && !(filename == "Ship2_damage3.png")){
                filename = "Ship2_damage3.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if(hpPercentage <= 20 && hpPercentage > 0 && !(filename == "Ship2_damage4.png")){
                filename = "Ship2_damage4.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if (hpPercentage <= 0){
                //Another check is necessary here to give it the correct sprite, to-do.
                if(!(filename == "Ship2_Undamaged.png" )){
                    filename = "Ship2_Undamaged.png";
                    texture = new Texture(Gdx.files.internal(filename));
                }
            }
        }
    }

    public class EnemyShip extends Actor {
        String filename = "Ship1_Undamaged.png";
        Texture texture = new Texture(Gdx.files.internal(filename));
        public boolean started = false;


        public EnemyShip() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        }

        @Override
        public void draw(Batch batch, float alpha) {batch.draw(texture, this.getX(), getY(),150,150);}

        @Override
        public void act(float delta){ //Changes sprite depending on how much damage the ship has taken
            float hpPercentage = battleMode.getShipHealthPercentage("enemy");
            if(hpPercentage <= 0 && !(filename == "Ship1_destroyed.jpg")){
                filename = "Ship1_destroyed.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if(hpPercentage <= 80 && hpPercentage > 60 && !(filename == "Ship1_damage1.png")){
                filename = "Ship1_damage1.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if(hpPercentage <= 60 && hpPercentage > 40 && !(filename == "Ship1_damage2.png")){
                filename = "Ship1_damage2.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if(hpPercentage <= 40 && hpPercentage > 20 && !(filename == "Ship1_damage3.png")){
                filename = "Ship1_damage3.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if(hpPercentage <= 20 && hpPercentage > 0 && !(filename == "Ship1_damage4.png")){
                filename = "Ship1_damage4.png";
                texture = new Texture(Gdx.files.internal(filename));
            } else if (hpPercentage <= 0){
                //Another check is necessary here to give it the correct sprite, to-do.
                if(!(filename == "Ship1_Undamaged.png" )){
                    filename = "Ship1_Undamaged.png";
                    texture = new Texture(Gdx.files.internal(filename));
                }
            }
        }
    }

    public class Card1 extends Actor {
        //The fileName will be the card's name. Make sure each card is named.
        String fileName = battleMode.getCardName(0) + ".png";
        Texture texture = new Texture(Gdx.files.internal(fileName));
        public boolean started = false;


        public Card1() {
            //setScale(0.5f);
            //setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((Card1)event.getTarget()).started = true;
                    return true;
                }
            });

        }

        @Override
        public void draw(Batch batch, float alpha) {batch.draw(texture, this.getX(), getY());
        }

        @Override
        public void act(float delta){
            //In theory, you can make it so that apply Card is a function that returns true or false,
            //true if the card is there and it went through, false if there's not enough mana or if the card is not present.
            //I will not do this now, however.
            if(started){
                battleMode.applyCard(battleMode.playerPlayCard(0), "player");
                started = false;
            }
            if(battleMode.isCardEmpty(0)){
                if (!(fileName == "CardEmpty_small.png")){
                    texture = new Texture(Gdx.files.internal("CardEmpty_small.png"));
                }
            } else{
                if (!(fileName == (battleMode.getCardName(0) + ".png"))){
                    //The fileName will be the card's name. Make sure each card is named.
                    fileName = battleMode.getCardName(0) + ".png";
                    texture = new Texture(Gdx.files.internal(fileName));
                }
            }
        }
    }

    public class Card2 extends Actor {
        //The fileName will be the card's name. Make sure each card is named.
        String fileName = battleMode.getCardName(1) + ".png";
        Texture texture = new Texture(Gdx.files.internal(fileName)); //default card type
        public boolean started = false;

        public Card2() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((Card2)event.getTarget()).started = true;
                    return true;
                }
            });
        }


        @Override
        public void draw(Batch batch, float alpha) {batch.draw(texture, this.getX(), getY());}



        @Override
        public void act(float delta){
            if(started){
                battleMode.applyCard(battleMode.playerPlayCard(1), "player");
                started = false;
            }
            if(battleMode.isCardEmpty(1)){
                if (!(fileName == "CardEmpty_small.png")){
                    texture = new Texture(Gdx.files.internal("CardEmpty_small.png"));
                }
            } else{
                if (!(fileName == (battleMode.getCardName(0) + ".png"))){
                    //The fileName will be the card's name. Make sure each card is named.
                    fileName = battleMode.getCardName(1) + ".png";
                    texture = new Texture(Gdx.files.internal(fileName));
                }
            }
        }
    }

    public class Card3 extends Actor {
        //The fileName will be the card's name. Make sure each card is named.
        String fileName = battleMode.getCardName(2) + ".png";
        Texture texture = new Texture(Gdx.files.internal(fileName)); //default card type
        public boolean started = false;

        public Card3() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((Card3)event.getTarget()).started = true;
                    return true;
                }
            });
        }

        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(texture, this.getX(), getY());
        }

        @Override
        public void act(float delta){
            if(started){
                battleMode.applyCard(battleMode.playerPlayCard(2), "player");
                started = false;
            }
            if(battleMode.isCardEmpty(2)){
                if (!(fileName == "CardEmpty_small.png")){
                    texture = new Texture(Gdx.files.internal("CardEmpty_small.png"));
                }
            } else{
                if (!(fileName == (battleMode.getCardName(2) + ".png"))){
                    //The fileName will be the card's name. Make sure each card is named.
                    fileName = battleMode.getCardName(2) + ".png";
                    texture = new Texture(Gdx.files.internal(fileName));
                }
            }
        }
    }

    public class Card4 extends Actor {
        //The fileName will be the card's name. Make sure each card is named.
        String fileName = battleMode.getCardName(3) + ".png";
        Texture texture = new Texture(Gdx.files.internal(fileName)); //default card type
        public boolean started = false;

        public Card4() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((Card4)event.getTarget()).started = true;
                    return true;
                }
            });
        }

        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(texture, this.getX(), getY());
        }

        @Override
        public void act(float delta){
            if(started){
                battleMode.applyCard(battleMode.playerPlayCard(3), "player");
                started = false;
            }
            if(battleMode.isCardEmpty(3)){
                if (!(fileName == "CardEmpty_small.png")){
                    texture = new Texture(Gdx.files.internal("CardEmpty_small.png"));
                }
            } else{
                if (!(fileName == (battleMode.getCardName(3) + ".png"))){
                    //The fileName will be the card's name. Make sure each card is named.
                    fileName = battleMode.getCardName(3) + ".png";
                    texture = new Texture(Gdx.files.internal(fileName));
                }
            }
        }
    }



    public BattleModeGraphics(Game game, ScreenAdapter parent, GameLogic gameLogic, Ship enemyShip){
        this.game = game;
        this.playerShip = gameLogic.getPlayer().getPlayerShip();
        this.enemyShip = enemyShip;
        this.parent = parent;
        this.gameLogic = gameLogic;
        battleMode = new BattleMode(playerShip, enemyShip, gameLogic.currentGold );

        //initiliazes the standard TextButtonStyle for the exit button to follow
        optionStyle = new TextButton.TextButtonStyle();
        optionStyle.fontColor = Color.BLACK;
        optionStyle.downFontColor = Color.BLUE;
        optionStyle.font = new BitmapFont();

        create();
    }


    public void create() {
        stage = new Stage();
        batch = new SpriteBatch();
        playerFont = new BitmapFont();
        playerFont.setColor(Color.GREEN);
        enemyFont = new BitmapFont();
        enemyFont.setColor(Color.RED);
        pointsFont = new BitmapFont();
        pointsFont.setColor(Color.YELLOW);
        goldFont = new BitmapFont();
        goldFont.setColor(Color.YELLOW);



        gameOver = false;

        background = new Texture( Gdx.files.internal("ocean.png") );
        Gdx.input.setInputProcessor(stage);

        PlayerShip playerShipActor = new PlayerShip();
        EnemyShip enemyShipActor = new EnemyShip();
        Card1 card1Actor = new Card1();
        Card2 card2Actor = new Card2();
        Card3 card3Actor = new Card3();
        Card4 card4Actor = new Card4();



        playerShipActor.setPosition(10,250);
        enemyShipActor.setPosition(470,250);
        card1Actor.setPosition(50, 0);
        card2Actor.setPosition(180, 0);
        card3Actor.setPosition(310, 0);
        card4Actor.setPosition(440, 0);
       // card1Actor.setScale(0.3f);
        ///card1Actor.setSize(150,200);
        //card2Actor.setScale(0.3f);
        //card3Actor.setScale(0.3f);
        //card4Actor.setScale(0.3f);




        stage.addActor(playerShipActor);
        stage.addActor(enemyShipActor);
        stage.addActor(card1Actor);
        stage.addActor(card2Actor);
        stage.addActor(card3Actor);
        stage.addActor(card4Actor);
    }

    //creates the exit button which switches back to the parent stage
    private void createExitButton(){
        exitButton = new TextButton("exit", optionStyle);
        exitButton.setText("Exit");
        exitButton.setSize(100,12);
        exitButton.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        exitButton.getLabel().setAlignment(Align.left);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ///when the screen is exited gold and score rewards are added to global gameLogic controller
                gameLogic.currentGold += battleMode.getGoldToGain();
                gameLogic.addScore(battleMode.getScoreToGain());
                gameLogic.getPlayer().getPlayerShip().setCurrentHealth(battleMode.getPlayerShipHealth());
                //then dispose
                dispose();
                game.setScreen(parent);
            }
        });
        stage.addActor(exitButton);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        String playerManaBar = battleMode.showHealthBar("player");
        String playerHealthBar = battleMode.showManaBar("player");
        String enemyManaBar = battleMode.showHealthBar("enemy");
        String enemyHealthBar = battleMode.showManaBar("enemy");
        String pointsAmount = "Points: " + battleMode.showPoints();
        String goldAmount = "Gold: " + battleMode.showGold();



        //battleMode.updateMana("player", 1);
        //battleMode.updateMana("enemy", 1);


        //battleMode.updateClock();
        //battleMode.basicEnemyAI();
        if(battleMode.gameIsOver()){
            gameOver = true;
            createExitButton();
        }
        if(!(gameOver)){
            battleMode.updateClock();
            battleMode.basicEnemyAI();
        }



        batch.begin();
        batch.draw(background,0,0);
        playerFont.draw(batch, playerManaBar + playerHealthBar, 75, 450);
        enemyFont.draw(batch, enemyManaBar + enemyHealthBar, 475, 450);
        pointsFont.draw(batch, pointsAmount, 300, 450);
        goldFont.draw(batch, goldAmount, 300, 390);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }


    @Override
    public void dispose() {
        batch.dispose();
        playerFont.dispose();
        enemyFont.dispose();
        pointsFont.dispose();
        goldFont.dispose();
        stage.dispose();
    }
}
