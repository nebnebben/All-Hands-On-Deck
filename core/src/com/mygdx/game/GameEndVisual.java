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
    private Game game;
    private ScreenAdapter parent;
    private Boolean isWon;
    private Label endLabel;
    private Label.LabelStyle resourceStyle;
    private Stage endStage;

    //constructor call for establishing connection through parent game
    public GameEndVisual(Game game, ScreenAdapter parent, Boolean isWon) {
        this.game = game;
        this.parent = parent;
        this.isWon = isWon;
        create();
    }

    //creation init
    public void create() {
        endStage = new Stage(new ScreenViewport());
        //only called if game over - only needs to check if the game has been won
        if (isWon) {
            getEndLabel("won");
            System.out.println("won");
        } else {
            getEndLabel("lost");
        }
    }

    private void getEndLabel(String in){
        resourceStyle = new Label.LabelStyle();
        resourceStyle.fontColor = Color.RED;
        resourceStyle.font = new BitmapFont();

        endLabel = new Label("turn",resourceStyle);
        endLabel.setText("You " + in);
        endLabel.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        endLabel.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
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
