package com.mygdx.game;


import com.badlogic.gdx.Game;

public class AllHandsOnDeckGame extends Game {
    private Game game;

    @Override
    public void create(){
        game = this;
        game.setScreen(new BattleModeGraphics(game, new Ship(), new Ship()));
        //game.setScreen(new GameVisuals(game));
    }

    public void render(){
        super.render();

    }
}
