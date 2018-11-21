package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;




import java.util.ArrayList;

public class BattleModeMistake {



    public class ship extends BaseActor{
        public Integer manaTotal = 5;
        public ArrayList hand;
        public ArrayList deck;
        public Integer healthPoints;
    }


    public class PlayerShip extends ship{
        Texture texture = new Texture(Gdx.files.internal("PlayerShip.png"));
        public PlayerShip(){
            //Integer cast may not be necessary, wanting to assign mana as the value of the total rather than make it reference manaTotal
            Integer mana = (Integer)manaTotal;
            healthPoints = 100;
        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture,0,0);
        }
    }

    public class EnemyShip extends ship{
        Texture texture = new Texture(Gdx.files.internal("EnemyShip.png"));
        public EnemyShip(){
            //Integer cast may not be necessary, wanting to assign mana as the value of the total rather than make it reference manaTotal
            Integer mana = (Integer)manaTotal;
            healthPoints = 100;
        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture,200,0);
        }
    }
}

