package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handle.managers.GameStateManager;
import com.mygdx.game.states.EntryState;


public class MyGdxGame extends Game {

	SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
		GameStateManager.pushState(new EntryState());
	}


	@Override
	public void render () {
		GameStateManager.getState().update(1/12f);
		GameStateManager.getState().render(batch);
	}
	
	@Override
	public void dispose () {//Uygulama bitince
			while(GameStateManager.isEmpty()){
				GameStateManager.getState().dispose();
				GameStateManager.popState();
			}

			GameStateManager.clear();
			System.err.println("Everything is dispose");
	}
}
