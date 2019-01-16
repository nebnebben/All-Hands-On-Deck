package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.concurrent.TimeUnit;

public class GameEndVisual extends ScreenAdapter {
    //data/logic parameters
    private Game game;
    private ScreenAdapter parent;
    private Boolean isWon;
    private int score;

    //visual paramters
    private Label endLabel;
    private Label scoreLabel;
    private Label.LabelStyle resourceStyle;
    private Stage endStage;

    //constructor call for establishing connection through parent game
    public GameEndVisual(Game game, ScreenAdapter parent, Boolean isWon, int score) {
        this.game = game;
        this.parent = parent;
        this.isWon = isWon;
        this.score = score;
        create();
    }

    //creation init
    public void create() {
        endStage = new Stage(new ScreenViewport());
        //only called if game over - only needs to check if the game has been won
        if (isWon) {
            getEndLabel("won");
        } else {
            getEndLabel("lost");
        }

        scoreLabel  = new Label("score",resourceStyle);
        scoreLabel.setText("Score: " + score);
        scoreLabel.setSize(10, 10);
        scoreLabel.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2 - 14);
        scoreLabel.setAlignment(Align.center);
        endStage.addActor(scoreLabel);
    }

    private void getEndLabel(String in){
        resourceStyle = new Label.LabelStyle();
        resourceStyle.fontColor = Color.RED;
        resourceStyle.font = new BitmapFont();

        endLabel = new Label("end",resourceStyle);
        endLabel.setText("You " + in);
        endLabel.setSize(10, 10);
        endLabel.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        endLabel.setAlignment(Align.center);
        endStage.addActor(endLabel);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        endStage.act();
        endStage.draw();
    }

    @Override
    public void dispose(){
        endStage.dispose();
    }

}
