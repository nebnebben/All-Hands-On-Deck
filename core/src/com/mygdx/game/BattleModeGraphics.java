package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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



//Json attempts. Delete when done. -----------------
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Json attemps. Delete when done.---------------------

public class BattleModeGraphics extends ApplicationAdapter {
    private Texture background;
    SpriteBatch batch;
    BitmapFont playerFont;
    BitmapFont enemyFont;
    Stage stage;
    Sprite sprite;
    BattleMode battleMode = new BattleMode();

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
        Texture texture = new Texture(Gdx.files.internal("PlayerShip.png"));
        public boolean started = false;



        public PlayerShip() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());}

        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(texture, this.getX(), getY());
        }
    }

    public class EnemyShip extends Actor {
        Texture texture = new Texture(Gdx.files.internal("EnemyShip.png"));
        public boolean started = false;

        public EnemyShip() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        }

        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(texture, this.getX(), getY());
        }
    }

    public class AttackCard extends Actor {
        Texture texture = new Texture(Gdx.files.internal("CardAttack.png"));
        public boolean started = false;

        public AttackCard() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((AttackCard)event.getTarget()).started = true;
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
                battleMode.applyCard(battleMode.playerPlayCard(0), "enemy");
                started = false;
            }
        }
    }

    public class DefendCard extends Actor {
        Texture texture = new Texture(Gdx.files.internal("CardDefend.png"));
        public boolean started = false;

        public DefendCard() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((DefendCard)event.getTarget()).started = true;
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
                battleMode.applyCard(battleMode.playerPlayCard(1), "player");
                started = false;
            }
        }
    }




    @Override
    public void create() {
        stage = new Stage();
        batch = new SpriteBatch();
        playerFont = new BitmapFont();
        playerFont.setColor(Color.GREEN);
        enemyFont = new BitmapFont();
        enemyFont.setColor(Color.RED);



        gameOver = false;

        background = new Texture( Gdx.files.internal("ocean.png") );
        Gdx.input.setInputProcessor(stage);

        PlayerShip playerShipActor = new PlayerShip();
        EnemyShip enemyShipActor = new EnemyShip();
        AttackCard attackCardActor = new AttackCard();
        DefendCard defendCardActor = new DefendCard();

        playerShipActor.setPosition(0,250);
        enemyShipActor.setPosition(470,250);
        attackCardActor.setPosition(150, 0);
        defendCardActor.setPosition(350, 0);

        stage.addActor(playerShipActor);
        stage.addActor(enemyShipActor);
        stage.addActor(attackCardActor);
        stage.addActor(defendCardActor);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        String playerManaBar = battleMode.showHealthBar("player");
        String playerHealthBar = battleMode.showManaBar("player");
        String enemyManaBar = battleMode.showHealthBar("enemy");
        String enemyHealthBar = battleMode.showManaBar("enemy");



        //battleMode.updateMana("player", 1);
        //battleMode.updateMana("enemy", 1);


        //battleMode.updateClock();
        //battleMode.basicEnemyAI();
        if(battleMode.gameIsOver()){
            gameOver = true;
        }
        if(!(gameOver)){
            battleMode.updateClock();
            battleMode.basicEnemyAI();
        }



        batch.begin();
        batch.draw(background,0,0);
        playerFont.draw(batch, playerManaBar + playerHealthBar, 75, 450);
        enemyFont.draw(batch, enemyManaBar + enemyHealthBar, 475, 450);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }


        @Override
    public void dispose() {
        batch.dispose();
        playerFont.dispose();
        enemyFont.dispose();
        stage.dispose();

    }
}
