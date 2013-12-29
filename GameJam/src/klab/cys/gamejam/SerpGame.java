package klab.cys.gamejam;

import klab.cys.gamejam.view.LoadingScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class SerpGame extends Game {

	AssetManager assets;

	@Override
	public void create() {
	    Gdx.app.setLogLevel(Application.LOG_ERROR);
		Gdx.app.error("SerpGame", "create(), setLogLevel(LOG_error)");
		Gdx.app.log("SerpGame", "create(), going to LoadingScreen");
		setScreen(new LoadingScreen(this,assets));
		Gdx.app.log("SerpGame", "ended create()");
	}

    
}
