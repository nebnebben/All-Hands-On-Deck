package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;

import java.util.ArrayList;

public class GrishasMistake2 extends ApplicationAdapter {

    //maybe testing, maybe leave in final product, idk if there's a better way
    public Integer totalManaCount;
    public Integer currentManaCount;
    public Integer health = 1;
    public Integer healthTotal = 1;
    public boolean cardPressed = false;
    //maybe testing, maybe leave in final product, idk if there's a better way


    //private Actor background;
    //testing background stuff too
    private Texture background;
    //here.

    SpriteBatch batch;

    BitmapFont font;
    Stage stage;

    @Override
    public void create () {
        stage = new Stage();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        //background = new Actor();
        //background.setPosition( 0, 0 );
        background = new Texture( Gdx.files.internal("ocean.png") );

        PlayerShip playerShip = new PlayerShip();
        EnemyShip enemyShip = new EnemyShip();
        testCard testCard = new testCard();

        //temp code for test
        Gdx.input.setInputProcessor(stage);
        enemyShip.setTouchable(Touchable.enabled);
        //temp code for test

        stage.addActor(playerShip);
        stage.addActor(enemyShip);
        stage.addActor(testCard);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        String manaBar = "("+ currentManaCount + "/" + totalManaCount + ")";
        String healthBar = "("+ health + "/" + healthTotal + ")";

        batch.begin();
        batch.draw(background,0,0);
        font.draw(batch, manaBar + healthBar, 100, 100);
        batch.end();

        stage.draw();
    }

    @Override
    public void dispose () {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    /**
     * Attempts to make the Ship parent class and the two types of ships, PC and NPC/enemy
     */

    public class Ship extends Actor {
        public Integer clock;
        public Integer manaTotal;
        public Integer mana;
        public ArrayList hand;
        public ArrayList desk;
        public Integer healthPointsTotal;
        public Integer healthPoints;
        public Integer clockDuration = 60; //Works on FPS terms. 60 = 1 second. 120 = 2 seconds, etc.
        public float shipX;
        public float shipY;


        public void modifyMana(Integer value) {
            mana+=value;
            updateMana();
        }

        //may not be needed?
        public void updateMana(){
            if (mana > manaTotal){
                mana = manaTotal;
            }
            //-------------------------currentManaCount =  mana;
        }

        public Integer getManaValue(){
            return mana;
        }

        public void modifyHealth(Integer value){
            healthPoints+=value;
            updateHealth();
        }

        //may not be needed?
        public void updateHealth(){
            if (healthPoints > healthPointsTotal){
                healthPoints = healthPointsTotal;
            }
            //-------------------------currentHealth =  healthPoints;
        }



    }

    public class PlayerShip extends Ship{
        Texture texture = new Texture(Gdx.files.internal("PlayerShip.png"));
        public PlayerShip(){
            shipX = 0;
            shipY = 250;
            //clockDuration = 120;
            healthPointsTotal = 15;
            healthPoints = (Integer)healthPointsTotal;
            manaTotal = 20;
            mana = 5;
            clock = 0;

            //temp??
            totalManaCount = manaTotal;
            currentManaCount = mana;
            health = healthPoints;
            healthTotal = healthPointsTotal;
            //temp??

            setBounds(shipX,shipY,texture.getWidth(),texture.getHeight());
        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture,shipX,shipY);
        }

        //@Override
        public void act(float delta){
            health = healthPoints;
            currentManaCount = mana;
            clock+=1;
            if(clock >10){
                clock = 0;
                modifyMana(1);
            }


        }
    }

    public class EnemyShip extends Ship{
        Texture texture = new Texture(Gdx.files.internal("EnemyShip.png"));
        public EnemyShip(){
            shipX = 470;
            shipY = 250;
            clockDuration = 120;
            healthPointsTotal = 10;
            healthPoints = (Integer)healthPointsTotal;
            manaTotal = 10;
            mana = 0;
            clock = 0;

            setBounds(shipX,shipY,texture.getWidth(),texture.getHeight());
        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture,shipX,shipY);
        }

        //@Override
        public void act(float delta){
            clock+=1;
            if(clock >10){
                clock = 0;
                modifyMana(1);
            }


        }

    }

    public class testCard extends Actor{
        Texture texture = new Texture(Gdx.files.internal("CardAttack.png"));
        Integer cardX;
        Integer cardY;
        public boolean started = false;
        public testCard() {
            cardX = 200;
            cardY = 50;
            setBounds(cardX,cardY,texture.getWidth(),texture.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((testCard)event.getTarget()).started = true;
                    return true;
                }
            });
        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture,cardX,cardY);
        }

        @Override
        public void act(float delta){
            if(started){

                started = false;
            }
        }
    }

}