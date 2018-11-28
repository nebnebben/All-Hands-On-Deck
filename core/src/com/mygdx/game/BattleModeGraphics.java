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
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BattleModeGraphics extends ApplicationAdapter {
    private Texture background;
    SpriteBatch batch;
    BitmapFont font;
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
    //temp?


    public class PlayerShip extends Actor {
        Texture texture = new Texture(Gdx.files.internal("PlayerShip.png"));
        public boolean started = false;



        public PlayerShip() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());


        }

        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(texture, this.getX(), getY());
        }
    }

    public class EnemyShip extends Actor {
        Texture texture = new Texture(Gdx.files.internal("EnemyShip.png"));
        public boolean started = false;
        Ship enemyShip = new Ship();

        public EnemyShip() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        }

        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(texture, this.getX(), getY());
        }
    }


    @Override
    public void create() {
        stage = new Stage();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        background = new Texture( Gdx.files.internal("ocean.png") );
        Gdx.input.setInputProcessor(stage);

        PlayerShip playerShipActor = new PlayerShip();
        EnemyShip enemyShipActor = new EnemyShip();

        playerShipActor.setPosition(0,250);
        enemyShipActor.setPosition(470,250);

        stage.addActor(playerShipActor);
        stage.addActor(enemyShipActor);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        String manaBar = battleMode.showHealthBar("player");
        String healthBar = battleMode.showManaBar("player");

        battleMode.updateMana("player", 1);

        batch.begin();
        batch.draw(background,0,0);
        font.draw(batch, manaBar + healthBar, 100, 100);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }


        @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }
}
