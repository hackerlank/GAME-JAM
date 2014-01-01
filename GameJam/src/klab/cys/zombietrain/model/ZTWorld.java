package klab.cys.zombietrain.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class ZTWorld {
	static final int WORLD_WIDTH = 10;
	static final int WORLD_HEIGHT = 15;
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 0.75f;
	static final float TICK_DECREMENT = 0.05f;
	
	private ZTBody snake;
	private ZTHuman stain;
	private boolean gameOver = false;
	private int score = 0;
	
	private boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	private float tickTime = 0;
	private static float tick;
	
	public ZTWorld(){
		tick = TICK_INITIAL;
		snake = new ZTBody();
		placeStain();
	}

	public void placeStain(){
		for (int x=0; x < WORLD_WIDTH; x++){
			for (int y = 2; y < WORLD_HEIGHT; y++){
				fields[x][y] = false; // 2 <= y <=15, 0 and 1 are placed our direction buttons
			}
		}
		
		int len = snake.parts.size();
		for (int i = 0; i < len; i++) {
			ZTPart part = snake.parts.get(i);
			fields[part.x][part.y] = true;
		}
		
		int stainX = MathUtils.random(WORLD_WIDTH-1);
		Gdx.app.log("World", "placeStain(); stainX: "+stainX);
		int stainY = MathUtils.random(2, WORLD_HEIGHT-1);
		Gdx.app.log("World", "placeStain(); stainY: "+stainY);
		
		while (true) {
			if (fields[stainX][stainY] == false)
				break;
			stainX +=1;
			if (stainX >= WORLD_WIDTH) 
				stainX = 0;
			stainY += 1;
			if (stainY >= WORLD_HEIGHT)
				stainY = 2;
		}
		stain = new ZTHuman(stainX, stainY, MathUtils.random(2));
	}
	/*
	 * Updating the world
	 */
	public void update(float deltaTime) {
		if (gameOver) {
			return;
		}

		tickTime += deltaTime;
		while (tickTime > tick){
			tickTime -= tick;
			snake.advance();
			if (snake.checkBitten()) {
				gameOver = true;
				return;
			}
			ZTPart head = snake.parts.get(0);
			if (head.x == stain.x && head.y == stain.y){ // eats a stain
				score += SCORE_INCREMENT;
				snake.grow();
				if (snake.parts.size() == WORLD_HEIGHT * WORLD_WIDTH) {
					gameOver = true;
					return;
				} else {
					placeStain();
				}
				
				// Increase the speed everytime the score reaches multiple of 100
				if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
					tick -= TICK_DECREMENT;
				}
			}
		}
	}

	/**
	 * @return the snake
	 */
	public ZTBody getSnake() {
		return snake;
	}

	/**
	 * @param snake the snake to set
	 */
	public void setSnake(ZTBody snake) {
		this.snake = snake;
	}

	/**
	 * @return the stain
	 */
	public ZTHuman getStain() {
		return stain;
	}

	/**
	 * @param stain the stain to set
	 */
	public void setStain(ZTHuman stain) {
		this.stain = stain;
	}

	/**
	 * @return the gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * @param gameOver the gameOver to set
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the tick
	 */
	public static float getTick() {
		return tick;
	}

	/**
	 * @param tick the tick to set
	 */
	public static void setTick(float tick) {
		ZTWorld.tick = tick;
	}
}
