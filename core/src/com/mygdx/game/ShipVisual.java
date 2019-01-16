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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ShipVisual extends ScreenAdapter {
    //logic/data parameters
    private Game game;
    private ScreenAdapter parent;
    private Ship ship;

    //visual parameters
    private Label nameLabel;
    private Label healthLabel;
    private Label manaLabel;
    private Label regenLabel;
    private Label.LabelStyle resourceStyle;
    private TextButton exitButton;
    private TextButton.TextButtonStyle optionStyle;
    private Stage shipStage;

    public ShipVisual(Game game, ScreenAdapter parent, Ship ship){
        this.game = game;
        this.parent = parent;
        this.ship = ship;
        shipStage = new Stage(new ScreenViewport());

        //initiliazes the standard TextButtonStyle for the exit button to follow
        optionStyle = new TextButton.TextButtonStyle();
        optionStyle.fontColor = Color.BLACK;
        optionStyle.downFontColor = Color.BLUE;
        optionStyle.font = new BitmapFont();

        //label style
        resourceStyle = new Label.LabelStyle();
        resourceStyle.fontColor = Color.RED;
        resourceStyle.font = new BitmapFont();

        //actual creates visuals
        create();
    }

    public void create(){
        //name
        nameLabel = new Label("name", resourceStyle);
        nameLabel.setText(ship.getName());
        nameLabel.setPosition(0, Gdx.graphics.getHeight()-15);
        nameLabel.setSize(10,12);
        nameLabel.setAlignment(Align.topLeft);
        shipStage.addActor(nameLabel);

        //health
        healthLabel = new Label("health", resourceStyle);
        healthLabel.setText("Health: " + Integer.toString(ship.getHealth()));
        healthLabel.setPosition(0, Gdx.graphics.getHeight()-40);
        healthLabel.setSize(10,12);
        healthLabel.setAlignment(Align.topLeft);
        shipStage.addActor(healthLabel);

        //mana regen
        regenLabel = new Label("regen", resourceStyle);
        regenLabel.setText("Mana Regen: " + Integer.toString(ship.getManaRegenRate()));
        regenLabel.setPosition(0, Gdx.graphics.getHeight()-55);
        regenLabel.setSize(10,12);
        regenLabel.setAlignment(Align.topLeft);
        shipStage.addActor(regenLabel);

        //mana
        manaLabel = new Label("mana", resourceStyle);
        manaLabel.setText("Mana: " + Integer.toString(ship.getTotalMana()));
        manaLabel.setPosition(0, Gdx.graphics.getHeight()-70);
        manaLabel.setSize(10,12);
        manaLabel.setAlignment(Align.topLeft);
        shipStage.addActor(manaLabel);

        //exit
        createExitButton();

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
        shipStage.addActor(exitButton);
    }

    @Override
    public void render(float delta){
        Gdx.input.setInputProcessor(shipStage);
        //clears image
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shipStage.act();
        shipStage.draw();

    }

    @Override
    public void dispose(){
        shipStage.dispose();
    }




}
