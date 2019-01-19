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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DepartmentVisual extends ScreenAdapter {
    //data/logic parameters
    private Game game;
    private ScreenAdapter parent;
    private GameLogic gameLogic;
    private DepartmentNode departmentNode;
    private final int upgradeCost;

    //visual parameters
    private Label nameLabel;
    private Label.LabelStyle resourceStyle;
    private TextButton upgradeButton;
    private TextButton supplyButton;
    private TextButton attackButton;
    private TextButton exitButton;
    private TextButton.TextButtonStyle optionStyle;
    private Stage departmentStage;

    public DepartmentVisual(Game game, ScreenAdapter parent, GameLogic gameLogic, DepartmentNode departmentNode){
        this.game = game;
        this.parent = parent;
        this.gameLogic = gameLogic;
        this.departmentNode = departmentNode;
        this.upgradeCost = departmentNode.getUpgradeCost();
        departmentStage = new Stage(new ScreenViewport());

        //initiliazes the standard TextButtonStyle for the options to follow
        optionStyle = new TextButton.TextButtonStyle();
        optionStyle.fontColor = Color.BLACK;
        optionStyle.downFontColor = Color.BLUE;
        optionStyle.font = new BitmapFont();

        //name style
        resourceStyle = new Label.LabelStyle();
        resourceStyle.fontColor = Color.RED;
        resourceStyle.font = new BitmapFont();

        //creates visuals
        create();
    }

    private void create(){
        //creates department label
        nameLabel = new Label("name", resourceStyle);
        nameLabel.setText(departmentNode.getDepartmentName()+", Allied: "+Integer.toString(departmentNode.getDepartmentStatus()));
        nameLabel.setSize(100,12);
        nameLabel.setPosition(20, Gdx.graphics.getHeight()-15);
        nameLabel.setAlignment(Align.left);
        departmentStage.addActor(nameLabel);

        //buy upgrade
        upgradeButton = new TextButton("upgrade", optionStyle);
        String[] upgradeString = departmentNode.upgrade2Str();
        upgradeButton.setText("1. Upgrade ship "+upgradeString[0]+" by "+upgradeString[1]);
        upgradeButton.setSize(100,12);
        upgradeButton.setPosition(upgradeButton.getText().length()*2, Gdx.graphics.getHeight() - 40);
        upgradeButton.getLabel().setAlignment(Align.left);
        upgradeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (gameLogic.currentGold >= upgradeCost){
                    gameLogic.currentGold -= upgradeCost;
                    shipUpgradeInterpreter(departmentNode.buyUpgrade());
                }
            }
        });
        departmentStage.addActor(upgradeButton);

        //buy supplies
        createSupplyButton();

        //attack department
        createAttackButton();

        //exit
        createExitButton();
    }

    //buys supplies with gold
    private void createSupplyButton(){
        //initiliazes a constant supply cost
        final int supplyCost = 10;
        supplyButton = new TextButton("supply", optionStyle);
        supplyButton.setText("2. Buy Supplies");
        supplyButton.setSize(100,12);
        supplyButton.setPosition(20, Gdx.graphics.getHeight() - 55);
        supplyButton.getLabel().setAlignment(Align.left);
        supplyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //if it can afford supplies, buy it
                if (gameLogic.currentGold >= supplyCost){
                    gameLogic.addSupplies(1);
                    gameLogic.currentGold -= supplyCost;
                }
            }
        });
        departmentStage.addActor(supplyButton);
    }

    //attacks the department, which triggers a unique battle
    private void createAttackButton(){
        //added to include parent screen in clickListener.
        final ScreenAdapter clickThis = parent;
        attackButton = new TextButton("attack",optionStyle);
        attackButton.setText("3. Attack department");
        attackButton.setSize(100, 12);
        attackButton.setPosition(20, Gdx.graphics.getHeight() - 70);
        //the label for the button needs to be aligned to the left, defaults to the center
        attackButton.getLabel().setAlignment(Align.left);
        //click listener to allow the options to be clicked and as such chosen
        attackButton.addListener(new ClickListener()
        {
            //on left click up - can only follow after a left click down
            @Override
            public void clicked(InputEvent event, float x, float y){
                //every encounter has a score which is added on, then the actual choice is interpreted
                //cant attack an already allied department
                if (departmentNode.getDepartmentStatus() != 1) {
                    Ship enemyShip = new Ship();
                    dispose();
                    game.setScreen(new BattleModeGraphics(game, clickThis, gameLogic, enemyShip));
                }
            }
        });
        departmentStage.addActor(attackButton);
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
        departmentStage.addActor(exitButton);
    }

    //interpreters the upgrade codes for ship upgrades
    private void shipUpgradeInterpreter(int[] in){
        Ship playerShip = gameLogic.getPlayer().getPlayerShip();
        switch (in[0]){
            case 1:
                //upgrading the max health also increases the health of the ship.
                playerShip.setTotalHealth(playerShip.getTotalHealth()+in[1]);
                playerShip.setHealth(playerShip.getHealth()+in[1]);
            case 2:
                int newTotal = playerShip.getTotalMana() + in[1];
                playerShip.setTotalMana(newTotal);
        }
    }

    @Override
    public void render(float delta){
        Gdx.input.setInputProcessor(departmentStage);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        departmentStage.act();
        departmentStage.draw();
    }

    @Override
    public void dispose(){
        departmentStage.dispose();
    }
}
