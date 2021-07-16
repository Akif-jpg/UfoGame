package com.mygdx.game;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	private boolean isDoubleBackPress = false;
	private long firstPressMilis = 0;
	MyGdxGame game;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Toast toast = Toast.makeText(AndroidLauncher.this,"Your Game Opened ♥♥♥" ,
				Toast.LENGTH_LONG);
		toast.show();
		game = new MyGdxGame();

		initialize(game, config);
	}

	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - firstPressMilis) / 1000 < 2) {
			super.onBackPressed();
		} else {
			Toast toast = Toast.makeText(AndroidLauncher.this, "two times tap for exit", Toast.LENGTH_LONG);
			toast.show();
		}

		firstPressMilis = System.currentTimeMillis();
	}

	@Override
	public void exit() {
		game.dispose();
		super.exit();
	}
}
