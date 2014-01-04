package klab.cys.zombietrain.view;

import klab.cys.zombietrain.model.ZTSettings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ZTLoadingScreen extends ZTScreen {

	Preferences settings = ZTSettings.serpSettings;
	Preferences highscores = ZTSettings.serpHighscores;
	
	public ZTLoadingScreen(Game game) {
		super(game);
		Gdx.app.log("LoadingScreen", "Constructor: job done!");

		this.game = game;
		loadGameAssets();
		loadSettings();
		loadHighscores();

	}
	public ZTLoadingScreen(Game game, AssetManager assets){
		super(game, assets);
		Gdx.app.log("LoadingScreen", "Constructor: job done!");
		Gdx.app.log("LoadingScreen", "LoadingScreen(game, assets), this.assets: "+ this.assets);
		this.assets = new AssetManager();
		Gdx.app.log("LoadingScreen", "LoadingScreen(game, assets), this.assets: "+ this.assets);
		loadAssetManager();
		Gdx.app.log("LoadingScreen", "LoadingScreen(game, assets), assets: "+ assets);
		loadSettings();
		loadHighscores();
	}
	
	/*
	 * two way assets loading
	 */
	public void loadGameAssets() {
		ZTAssets.background = new Texture(Gdx.files.internal("background.png"));
		ZTAssets.logo = new Texture(Gdx.files.internal("logo.png"));
		ZTAssets.mainMenu = new Texture(Gdx.files.internal("mainmenu.png"));
		ZTAssets.buttons = new Texture(Gdx.files.internal("buttons.png"));
		ZTAssets.help1 = new Texture(Gdx.files.internal("help1.png"));
		ZTAssets.help2 = new Texture(Gdx.files.internal("help2.png"));
		ZTAssets.help3 = new Texture(Gdx.files.internal("help3.png"));
		ZTAssets.numbers = new Texture(Gdx.files.internal("numbers.png"));
		ZTAssets.ready = new Texture(Gdx.files.internal("ready.png"));
		ZTAssets.pause = new Texture(Gdx.files.internal("pausemenu.png"));
		ZTAssets.gameOver = new Texture(Gdx.files.internal("gameover.png"));
		ZTAssets.headUp = new Texture(Gdx.files.internal("headup.png"));
		ZTAssets.headLeft = new Texture(Gdx.files.internal("headleft.png"));
		ZTAssets.headDown = new Texture(Gdx.files.internal("headdown.png"));
		ZTAssets.headRight = new Texture(Gdx.files.internal("headright.png"));
		ZTAssets.tail = new Texture(Gdx.files.internal("tail.png"));
		ZTAssets.stain1 = new Texture(Gdx.files.internal("stain1.png"));
		ZTAssets.stain2 = new Texture(Gdx.files.internal("stain2.png"));
		ZTAssets.stain3 = new Texture(Gdx.files.internal("stain3.png"));
		ZTAssets.poo = new Texture(Gdx.files.internal("poo.png"));
		ZTAssets.priest = new Texture(Gdx.files.internal("priest.png"));
		ZTAssets.human = new Texture(Gdx.files.internal("human.png"));
		ZTAssets.police = new Texture(Gdx.files.internal("police.png"));
	    
		ZTAssets.click = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));
		ZTAssets.eat = Gdx.audio.newSound(Gdx.files.internal("eat.ogg"));
		ZTAssets.bitten = Gdx.audio.newSound(Gdx.files.internal("bitten.ogg"));
    }
	private void loadAssetManager() {
		// Using assets as an AssetManager
		Gdx.app.log("LoadingScreen", "starting loadGameAssets()");
		assets.load("background.png", Texture.class);
		assets.load("logo.png", Texture.class);
		assets.load("mainmenu.png", Texture.class);
		assets.load("buttons.png", Texture.class);
		assets.load("help1.png", Texture.class);
		assets.load("help2.png", Texture.class);
		assets.load("help3.png", Texture.class);
		assets.load("numbers.png", Texture.class);
		assets.load("ready.png", Texture.class);
		assets.load("pausemenu.png", Texture.class);
		assets.load("gameover.png", Texture.class);
		assets.load("headup.png", Texture.class);
		assets.load("headleft.png", Texture.class);
		assets.load("headdown.png", Texture.class);
		assets.load("headright.png", Texture.class);
		assets.load("tail.png", Texture.class);
		assets.load("stain1.png", Texture.class);
		assets.load("stain2.png", Texture.class);
		assets.load("stain3.png", Texture.class);
		assets.load("poo.png", Texture.class);
		assets.load("priest.png", Texture.class);
		assets.load("human.png", Texture.class);
		assets.load("police.png", Texture.class);

		assets.load("click.ogg", Sound.class);
		assets.load("eat.ogg", Sound.class);
		assets.load("bitten.ogg", Sound.class);
		
		assets.finishLoading();
		Gdx.app.log("LoadingScreen", "ended loadGameAssets()");
	}

	/*
	 * Load Settings and Highscores
	 */
	public void loadSettings(){
		Gdx.app.log("LoadingScreen", "loadSettings(), soundOn: "+
				settings.getBoolean("soundOn"));
		settings.getBoolean("soundOn", true);
		settings.flush();
	}
	public void loadHighscores(){
		Gdx.app.log("LoadingScreen", "loadHighscores(), serpHighscore.0: "+
				highscores.getInteger(""+0));
		if (highscores.getInteger(""+0) == 0){
			int[] hs = { 100, 80, 50, 30, 10 };
			
			for (int i = 0; i< hs.length; i++){
				highscores.putInteger(""+i, hs[i]);
			}
			highscores.flush();
		}
	}
	/*
	 * Screen methods
	 */
	@Override
	public void render(float delta) {
		Gdx.app.log("LoadingScreen", "starting render()");
		Gdx.app.log("LoadingScreen", "render(), this.assets: "+ this.assets);
		Gdx.app.log("LoadingScreen", "render(), assets: "+ assets);
		
		if (this.assets == null)
			game.setScreen(new ZTGameScreen(game));
		if (this.assets != null)
			game.setScreen(new ZTGameScreen(game,assets));
		
		Gdx.app.log("LoadingScreen", "ended render()");
	}
	@Override
	public void resize(int width, int height) {
		Gdx.app.log("LoadingScreen", "resize()ing");
	}
	public void show(){
		Gdx.app.log("LoadingScreen", "show()ing");
		
		//Settings.load(game.getFileIO());
		Gdx.app.log("LoadingScreen", "finished show()ing");
	}
	@Override
	public void hide() {
		Gdx.app.log("LoadingScreen", "hide()ing");
	}
	@Override
	public void pause() {
		Gdx.app.log("LoadingScreen", "pause()ing");
	}
	@Override
	public void resume() {
		Gdx.app.log("LoadingScreen", "resume()ing");
	}
	@Override
	public void dispose() {
		Gdx.app.log("LoadingScreen", "dispose()ing");
		//assets.dispose();
	}
	@Override
	public void drawText(String line, int x, int y) {
		Gdx.app.log("LoadingScreen", "drawText()");
	}
}
