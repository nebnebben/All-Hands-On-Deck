package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.ArrayList;

public class GrishasMistake1 extends ApplicationAdapter {
	private Stage stage;
	public Integer clock;
	public Integer totalManaCount;
	public Integer currentManaCount;
	public Integer health = 1;


	private SpriteBatch batch;
	private BitmapFont font;




	@Override
	public void create () {
		stage = new Stage();
		clock = 0;


		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);

		PlayerShip playerShip = new PlayerShip();
		EnemyShip enemyShip = new EnemyShip();

		//temp code for test
		Gdx.input.setInputProcessor(stage);
		enemyShip.setTouchable(Touchable.enabled);
		//temp code for test
		stage.addActor(playerShip);
		stage.addActor(enemyShip);
	}



	@Override
	public void render () {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		String manaBar = "("+ currentManaCount + "/" + totalManaCount + ")";
		String healthBar = "("+ health + ")";
		batch.begin();
		font.draw(batch, manaBar + healthBar, 100, 100);
		batch.end();

		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
		font.dispose();
	}












	public class ship extends BaseActor{
		public Integer manaTotal = 5;
		public ArrayList hand;
		public ArrayList deck;
		public Integer healthPoints;
		public Integer mana = 0;
		public Integer regenDuration = 120; //Works on FPS terms. 60 = 1 second. 120 = 2 seconds, etc.

		public void modifyMana(Integer value){
			mana+=value;
		}
	}


	public class PlayerShip extends ship {
		Texture texture = new Texture(Gdx.files.internal("PlayerShip.png"));
		public PlayerShip(){
			//Integer cast may not be necessary, wanting to assign mana as the value of the total rather than make it reference manaTotal
			mana = (Integer)manaTotal;
			healthPoints = 100;
		}


		@Override
		public void draw(Batch batch, float alpha){
			batch.draw(texture,0,250);
		}
	}

	public class EnemyShip extends ship {
		Texture texture = new Texture(Gdx.files.internal("EnemyShip.png"));
		float eneShipX = 470, eneShipY = 250;
		public boolean started = false;
		public EnemyShip(){
			//Integer cast may not be necessary, wanting to assign mana as the value of the total rather than make it reference manaTotal
			Integer mana = 0; //(Integer)manaTotal;

			//Temporary code to see if HP gets removed
			healthPoints = 100;

			currentManaCount = mana;
			health = healthPoints;
			totalManaCount = manaTotal;


			//Temporary code to see if HP gets removed

			setBounds(eneShipX,eneShipY,texture.getWidth(),texture.getHeight());
			addListener(new InputListener(){
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					((EnemyShip)event.getTarget()).started = true;
					return true;
				}
			});

		}


		public void updateMana()
		{
			if(mana > manaTotal){
				mana = manaTotal;
			}
			currentManaCount = mana;
		}

		public Integer getManaValue()
		{
			return mana;
		}

		public void updateHealth()
		{
			health = healthPoints;
		}

		@Override
		public void draw(Batch batch, float alpha){
			batch.draw(texture,eneShipX,eneShipY);
		}

		@Override
		public void act(float delta){
			clock+=1;
			if(clock >=10){
				clock = 0;
				modifyMana(1);
				updateMana();
			}
			if(started){
				if(healthPoints <= 0){
					eneShipY-=5;
				} else {

					if(getManaValue() >= 5){
						healthPoints-=10;
						updateHealth();
						started = false;
						modifyMana(-5);
						updateMana();
					} else{
						started = false;
						updateHealth();
						updateMana();
					}

				}

			}
		}
	}
}
