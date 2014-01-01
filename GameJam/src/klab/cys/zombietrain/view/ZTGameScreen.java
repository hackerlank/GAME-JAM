package klab.cys.zombietrain.view;

import klab.cys.zombietrain.controller.SwipeGestureDetector;
import klab.cys.zombietrain.controller.SwipeGestureDetector.SwipeListener;
import klab.cys.zombietrain.model.ZTBody;
import klab.cys.zombietrain.model.ZTHuman;
import klab.cys.zombietrain.model.ZTPart;
import klab.cys.zombietrain.model.ZTSettings;
import klab.cys.zombietrain.model.ZTWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;


public class ZTGameScreen extends ZTScreen {
	float vwidth = 320f; // v from virtual
	float vheight = 480f;
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	float width, height = 0;

	SpriteBatch spriteBatch;
	ShapeRenderer shaperenderer;

	Preferences settings;
	Preferences highscores;
	
	Texture background;
	Texture ready;
	Texture pause;
	Texture gameover;
	Texture numbers;
	Texture buttons;
	Texture headup;
	Texture headleft;
	Texture headdown;
	Texture headright;
	Texture tail;
	Texture stain1;
	Texture stain2;
	Texture stain3;
	
	Sound click;
	Sound eat;
	Sound bitten;
	
	enum GameState {
		Ready,
		Running,
		Paused,
		GameOver
	}
	
	GameState state = GameState.Ready;
	ZTWorld world;
	int oldScore = 0;
	String score = "0";

	public ZTGameScreen(Game game){
		super(game);
		Gdx.app.error("GameScreen", "Constructor: super(game) job done!");

		world = new ZTWorld();
		
		spriteBatch = new SpriteBatch();
		
		shaperenderer = new ShapeRenderer();
		shaperenderer.setColor(Color.BLACK);
		
		settings = ZTSettings.serpSettings;
		highscores = ZTSettings.serpHighscores;
		
		background = ZTAssets.background;
		ready = ZTAssets.ready;
		pause = ZTAssets.pause;
		gameover = ZTAssets.gameOver;
		numbers = ZTAssets.numbers;
		buttons = ZTAssets.buttons;
		headup = ZTAssets.headUp;
		headleft = ZTAssets.headLeft;
		headdown = ZTAssets.headDown;
		headright = ZTAssets.headRight;
		tail = ZTAssets.tail;
		stain1 = ZTAssets.stain1;
		stain2 = ZTAssets.stain2;
		stain3 = ZTAssets.stain3;
		
		click = ZTAssets.click;
		eat = ZTAssets.eat;
		bitten = ZTAssets.bitten;

		setUpSwipeListener();
	}
	public ZTGameScreen(Game game, AssetManager assets) {
		super(game,assets);
		Gdx.app.error("GameScreen", "Constructor: super(game,assets) job done!");

		world = new ZTWorld();

		spriteBatch = new SpriteBatch();
		
		shaperenderer = new ShapeRenderer();
		shaperenderer.setColor(Color.BLACK);
		
		settings = ZTSettings.serpSettings;
		highscores = ZTSettings.serpHighscores;

		background = assets.get("background.png", Texture.class);
		ready = assets.get("ready.png", Texture.class);
		pause = assets.get("pausemenu.png", Texture.class);
		gameover = assets.get("gameover.png", Texture.class);
		numbers = assets.get("numbers.png", Texture.class);
		buttons = assets.get("buttons.png", Texture.class);
		headup = assets.get("headup.png", Texture.class);
		headleft = assets.get("headleft.png", Texture.class);
		headdown = assets.get("headdown.png", Texture.class);
		headright = assets.get("headright.png", Texture.class);
		tail = assets.get("tail.png", Texture.class);
		stain1 = assets.get("stain1.png", Texture.class);
		stain2 = assets.get("stain2.png", Texture.class);
		stain3 = assets.get("stain3.png", Texture.class);
		
		click = assets.get("click.ogg", Sound.class);
		eat = assets.get("eat.ogg", Sound.class);
		bitten = assets.get("bitten.ogg", Sound.class);
		
		setUpSwipeListener();
	}

	/**
	 * Set up swipe listener
	 */
	private void setUpSwipeListener(){
		Gdx.input.setInputProcessor(new SwipeGestureDetector(new SwipeListener() {
			ZTBody snake = world.getSnake();
			
			@Override
			public void onUp() {
				Gdx.app.error("Swipe", "UP");
				if(state == GameState.Running){
					snake.turnUp();
				}
			}

			@Override
			public void onRight() {
				Gdx.app.error("Swipe", "RIGHT");
				if(state == GameState.Running){
					snake.turnRight2();
				}
			}

			@Override
			public void onLeft() {
				Gdx.app.error("Swipe", "LEFT");
				if(state == GameState.Running){
					snake.turnLeft2();
				}
			}

			@Override
			public void onDown() {
				Gdx.app.error("Swipe", "DOWN");
				if(state == GameState.Running){
					snake.turnDown();
				}
			}
		}));
	}
	/*
	 * Screen methods
	 */
	@Override
	public void render(float delta) {
		Gdx.app.log("GameScreen", "render()ing");
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		
		spriteBatch.draw(background, 0, 0, width, height);

		drawWorld(world);
		
		if (state == GameState.Ready){
			inputReady();
			drawReadyUI();
		}
		if (state == GameState.Running){
			inputRunning(delta);
			drawRunningUI();
		}
		if (state == GameState.Paused){
			inputPaused();
			drawPausedUI();
		}
		if (state == GameState.GameOver){
			inputGameOver();
			drawGameOverUI();
		}
		drawText(score, ((int)vwidth / 2 - score.length()*10), 10);
		
		spriteBatch.end();
		shaperenderer.begin(ShapeType.Line);
		shaperenderer.line(0, 64*ppuY, width, 64*ppuY);
		shaperenderer.end();

		Gdx.app.log("GameScreen", "ended render()");
	}
	@Override
	public void pause() {
		Gdx.app.log("GameScreen", "pause()ing");
		if (state == GameState.Running)
			state = GameState.Paused;
	}
	@Override
	public void dispose() {
		Gdx.app.log("GameScreen", "dispose()ing");
		spriteBatch.dispose();
		shaperenderer.dispose();
	}
	@Override
	public void resize(int width, int height) {
		Gdx.app.log("GameScreen", "resize()ing");
		this.width = width;
		this.height = height;
		//this.ratio = this.width/this.height;
		ppuX = (float)width / vwidth;
		Gdx.app.log("Stress", "resize().ppuX: "+ppuX);
		ppuY = (float)height / vheight;
		Gdx.app.log("Stress", "resize().ppuY: "+ppuY);
		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);

	}
	@Override
	public void resume() {
		// Auto-generated method stub
		Gdx.app.log("GameScreen", "resume()ing");
	}
	@Override
	public void show() {
		// Auto-generated method stub
		Gdx.app.log("GameScreen", "show()ing");
	}
	@Override
	public void hide() {
		// Auto-generated method stub
		Gdx.app.log("GameScreen", "hide()ing");
	}
	@Override
	public void drawText(String line, int x, int y) {
		/*
		 * Usar siempre entre spriteBatch.begin() y end()
		 */
		Gdx.app.log("GameScreen", "drawText()");
        int len = line.length();
        
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
    
            if (character == ' ') {
                x += 20;
                continue;
            }
    
            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }
    
            spriteBatch.draw(numbers, x*ppuX, y*ppuY, srcWidth*ppuX, 32*ppuY, 
            		srcX, 0, srcWidth, 32, false, false);
            x += srcWidth;
        }
	}

	private void drawWorld(ZTWorld world){
		
		ZTBody snake = world.getSnake();
		ZTPart head = snake.parts.get(0);
		ZTHuman stain = world.getStain();
		
		int x,y;

		Texture stainPixmap = null;
		if (stain.type == ZTHuman.TYPE_1)
			stainPixmap = stain1;
		if (stain.type == ZTHuman.TYPE_2)
			stainPixmap = stain2;
		if (stain.type == ZTHuman.TYPE_3)
			stainPixmap = stain3;
		x = stain.x * 32;
		y = stain.y * 32;
		spriteBatch.draw(stainPixmap, x*ppuX, y*ppuY, 32*ppuX, 32*ppuY);
		
		int len = snake.parts.size();
		for (int i = 1; i < len; i++){
			ZTPart part = snake.parts.get(i);
			x = part.x * 32;
			y = part.y * 32;
			spriteBatch.draw(tail, x*ppuX, y*ppuY, 32*ppuX, 32*ppuY);
		}
		
		Texture headPixmap = null;
		if (snake.direction == ZTBody.UP)
			headPixmap = headup;
		if (snake.direction == ZTBody.LEFT)
			headPixmap = headleft;
		if (snake.direction == ZTBody.DOWN)
			headPixmap = headdown;
		if (snake.direction == ZTBody.RIGHT)
			headPixmap = headright;
		x = head.x * 32 + 16;
		y = head.y * 32 + 16;
		if (headPixmap == null)
			Gdx.app.error("GameScreen", "drawWorld(), headPixmap == null");
		spriteBatch.draw(headPixmap, x*ppuX - (headPixmap.getWidth() / 2)*ppuX, 
				y*ppuY - (headPixmap.getHeight() /2)*ppuY, 42*ppuX, 42*ppuY);
	}
	/*
	 * state.ready
	 */
	private void inputReady() {
		if (Gdx.input.justTouched())
			state = GameState.Running;
	}
	private void drawReadyUI() {
				
		spriteBatch.draw(ready, width/2 - 112*ppuX, height/2 - 48*ppuY, 225*ppuX, 96*ppuY);
	}
	/*
	 * state.Running
	 */
	private void inputRunning(float deltaTime) {
		ZTBody snake = world.getSnake();
		if ((Gdx.input.justTouched())){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if (inBounds(touchPos, 0, 0, 64*ppuX, 64*ppuY)) {
				if (settings.getBoolean("soundOn"))
					click.play(1);
				state = GameState.Paused;
				return;
			} else if (inBounds(touchPos, 0, height-64*ppuY, 64*ppuX, 64*ppuY)){
				snake.turnLeft();
			} else if (inBounds(touchPos, width-64*ppuX, height-64*ppuY, 64*ppuX, 64*ppuY)){
				snake.turnRight();
			}
		}
			
		world.update(deltaTime);
		if (world.isGameOver()) {
			if (settings.getBoolean("soundOn"))
				bitten.play(1);
			state = GameState.GameOver;
			if (highscores.getInteger("4")< world.getScore())
				ZTSettings.addScore(world.getScore());
		}
		if (oldScore != world.getScore()) {
			oldScore = world.getScore();
			score = "" + oldScore;
			if (settings.getBoolean("soundOn"))
				eat.play(1);
		}
	}
	private void drawRunningUI() {
		// pause button
		spriteBatch.draw(buttons, 0, height-64*ppuY, 64*ppuX, 64*ppuY,
				64, 128, 64, 64, false, false);
		// turn left button
		spriteBatch.draw(buttons, 0, 0, 64*ppuX, 64*ppuY,
				64, 64, 64, 64, false, false); 
		// turn right button
		spriteBatch.draw(buttons, width -64*ppuX, 0, 64*ppuX, 64*ppuY,
				0, 64, 64, 64, false, false);  
	}
	/*
	 * state.paused
	 */
	private void inputPaused() {
		if ((Gdx.input.justTouched())){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if (inBounds(touchPos, width/2 - 80*ppuX, height/2 - 48*ppuY, 160*ppuX, 48*ppuY)) {
				if (settings.getBoolean("soundOn"))
					click.play(1);
				state = GameState.Running;
				return;
			} else if (inBounds(touchPos, width/2 - 80*ppuX, height/2, 160*ppuX, 48*ppuY)){
				if (settings.getBoolean("soundOn"))
					click.play(1);
				if (this.assets == null)
					game.setScreen(new ZTGameScreen(game));
				if (this.assets != null)
					game.setScreen(new ZTGameScreen(game,assets));
				return;
			}
		}
	}
	private void drawPausedUI() {
		
		spriteBatch.draw(pause, width/2 - 80*ppuX, height/2 - 48*ppuY, 160*ppuX, 96*ppuY);
	}
	/*
	 * state.GameOver
	 */
	private void inputGameOver() {
		if ((Gdx.input.justTouched())){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if (inBounds(touchPos, width/2 -32*ppuX, (height/3)*2 -32*ppuY, 64*ppuX, 64*ppuY)) {
				if (settings.getBoolean("soundOn"))
					click.play(1);
				if (this.assets == null)
					game.setScreen(new ZTGameScreen(game));
				if (this.assets != null)
					game.setScreen(new ZTGameScreen(game,assets));
				return;
			}
		}
	}
	private void drawGameOverUI() {
		
		spriteBatch.draw(gameover, width/2 - 98*ppuX, (height/3)*2 - 25*ppuY, 196*ppuX, 50*ppuY);
		spriteBatch.draw(buttons, width/2 - 32*ppuX, height/3 -32*ppuY, 64*ppuX, 64*ppuY,
				0, 128, 64, 64, false, false);
	}
	
}
