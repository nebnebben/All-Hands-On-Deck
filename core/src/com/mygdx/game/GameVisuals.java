package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/*
Break down into 3 sections:
    - Top bar containing relevant game information such as the ability to see deck and game state stats like gold and turn
    - Node map containing an image of all the nodes as well as the ability to click on nodes in order to traverse - button
    - Background
 */
public class GameVisuals extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create () {
        GameController game = new GameController();
        stage = new Stage(new ScreenViewport());
        int GuideRows = 12;
        int GuideCols = 12;
        int row_height = Gdx.graphics.getHeight()/GuideRows;
        int col_width = Gdx.graphics.getWidth()/GuideCols;
        addBackgroundGrid(GuideCols,GuideRows);

        //top bar
        //Turn label
        Label.LabelStyle resourceStyle = new Label.LabelStyle();
        resourceStyle.fontColor = Color.BLACK;

        Label turnLabel = new Label(String.join("Current turn: ",String.valueOf(game.getCurrentTurn())),resourceStyle);
        turnLabel.setSize(col_width,row_height);
        turnLabel.setPosition(Gdx.graphics.getWidth()-col_width,0);
        turnLabel.setAlignment(Align.topLeft);
        stage.addActor(turnLabel);

        Label goldLabel = new Label(String.join("Current gold: ",String.valueOf(game.currentGold)),resourceStyle);
        goldLabel.setSize(col_width,row_height);
        goldLabel.setPosition(Gdx.graphics.getWidth()- 2*col_width,0);
        goldLabel.setAlignment(Align.topLeft);
        stage.addActor(goldLabel);

        Label supplyLabel = new Label(String.join("Current supplies: ",String.valueOf(game.getCurrentSupplies())),resourceStyle);
        supplyLabel.setSize(col_width,row_height);
        supplyLabel.setPosition(Gdx.graphics.getWidth()-3*col_width,0);
        supplyLabel.setAlignment(Align.topLeft);
        stage.addActor(supplyLabel);

        Label scoreLabel = new Label(String.join("Score: ",String.valueOf(game.getScore())),resourceStyle);


    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    //used for sizing judgements
    public void addBackgroundGrid(int cols, int rows) {
        //sets background texture and makes it repeating
        Texture texture = new Texture(Gdx.files.internal("background.jpg"));
        texture.setWrap(Texture.TextureWrap.MirroredRepeat,Texture.TextureWrap.MirroredRepeat);

        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(0,0,texture.getWidth()*cols,texture.getHeight()*rows);
        Image background = new Image(textureRegion);
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background.setPosition(0,0);
        stage.addActor(background);


    }

}
