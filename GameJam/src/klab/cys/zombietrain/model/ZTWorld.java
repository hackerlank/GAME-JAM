package klab.cys.zombietrain.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import klab.cys.zombietrain.ai.ZTAIMoveRoamingFiring;
import klab.cys.zombietrain.controller.ZTConstants;
import klab.cys.zombietrain.view.ZTGameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class ZTWorld {
	public static final int WORLD_WIDTH = 20;
	public static final int WORLD_HEIGHT = 30;
	public static final int SCORE_INCREMENT = 10;
	public static final float TICK_INITIAL = 0.50f;
	public static final float TICK_DECREMENT = 0.05f;
	public static final float TIME_INITIAL_CREATE_PRIEST = 15;
	public static final float TIME_INITIAL_CREATE_POLICE = 10;
	
	private ZTBody snake;
	private ZTHuman stain;
	private List<ZTPriest> priest = new ArrayList<ZTPriest>();
	private List<ZTPolice> police = new ArrayList<ZTPolice>();
	private List<ZTBullet> bullets = new ArrayList<ZTBullet>();
	
	private boolean gameOver = false;
	private int score = 0;
	
	private boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	private float tickTime = 0;
	private static float tick;
	private float priestTime = 0;
	private static float createPriestTime;
	private float policeTime = 0;
	private static float createPoliceTime;
	
	private int level = 1;
	
	public ZTWorld(){
		tick = TICK_INITIAL;
		createPriestTime = TIME_INITIAL_CREATE_PRIEST;
		createPoliceTime = TIME_INITIAL_CREATE_POLICE;
		snake = new ZTBody();
		placeHuman(ZTConstants.HUMAN);
	}

	public void placeHuman(int type){
		for (int x=0; x < WORLD_WIDTH; x++){
			for (int y = ZTGameScreen.WORLD_LOWER_BOUND; y < WORLD_HEIGHT; y++){
				fields[x][y] = false; // buttonUILoc <= y <= WORLD_HEIGHT, 0 to buttonUILoc are placed our direction buttons
			}
		}
		
		int len = snake.parts.size();
		for (int i = 0; i < len; i++) {
			ZTPart part = snake.parts.get(i);
			fields[part.x][part.y] = true;
		}
		
		len = priest.size();
		for (int i = 0; i < len; i++){
			ZTPriest p = priest.get(i);
			fields[p.x][p.y] = true; 
		}

		
		int stainX = MathUtils.random(WORLD_WIDTH-1);
		Gdx.app.log("World", "placeStain(); stainX: "+stainX);
		int stainY = MathUtils.random(ZTGameScreen.WORLD_LOWER_BOUND, WORLD_HEIGHT-1);
		Gdx.app.log("World", "placeStain(); stainY: "+stainY);
		
		while (true) {
			if (fields[stainX][stainY] == false)
				break;
			stainX +=1;
			if (stainX >= WORLD_WIDTH) 
				stainX = 0;
			stainY += 1;
			if (stainY >= WORLD_HEIGHT)
				stainY = ZTGameScreen.WORLD_LOWER_BOUND;
		}
		
		if(type == ZTConstants.HUMAN){
			createHuman(stainX, stainY);
		} else if(type == ZTConstants.PRIEST){
			createPriest(stainX, stainY);
		} else if(type == ZTConstants.POLICE){
			createPolice(stainX, stainY);
		}
	}
	
	private void createHuman(int stainX, int stainY){
		if(level < 3){
			stain = new ZTHuman(stainX, stainY, ZTHuman.MOVEMENT_STAY);
		} else if(level < 5){
			stain = new ZTHuman(stainX, stainY, MathUtils.random(2), 3);
		} else{
			int percent = MathUtils.random(100);
			if(percent > 50){
				stain = new ZTHuman(stainX, stainY, MathUtils.random(3), MathUtils.random(4, 6));
			} else if(percent > 15){
				stain = new ZTHuman(stainX, stainY, MathUtils.random(2), 3);
			} else{
				stain = new ZTHuman(stainX, stainY, ZTHuman.MOVEMENT_STAY);
			}
		}
	}
	
	private void createPriest(int stainX, int stainY){
		Gdx.app.error("Priest", "created at x: "+stainX+" y: "+stainY);
		priest.add(new ZTPriest(stainX, stainY, MathUtils.random(15, 30)));
	}
	
	private void createPolice(int stainX, int stainY){
		Gdx.app.error("Police", "created at x: "+stainX+" y: "+stainY);
		police.add(new ZTPolice(stainX, stainY, MathUtils.random(10, 20)));
	}
	
	/*
	 * Updating the world
	 */
	public void update(float deltaTime) {
		if (gameOver) {
			return;
		}

		tickTime += deltaTime;
		
		if (!priest.isEmpty()){
			for (Iterator<ZTPriest> it = priest.iterator(); it.hasNext();){
				ZTPriest p = it.next();
				p.timer -= deltaTime;
				if (p.timer <= 0)
					it.remove();
			}
		}

		if (!police.isEmpty()){
			for (Iterator<ZTPolice> it = police.iterator(); it.hasNext();){
				ZTPolice p = it.next();
				p.timer -= deltaTime;
				if (p.timer <= 0)
					it.remove();
			}
		}
		
		priestTime += deltaTime;
		if(priestTime > createPriestTime){
			priestTime -= createPriestTime;
			if(level >= 5 && MathUtils.random(1) == 1){
				placeHuman(ZTConstants.PRIEST);
			}
		}
		
		policeTime += deltaTime;
		if(policeTime > createPoliceTime){
			policeTime -= createPoliceTime;
			if(level >= 3 && MathUtils.random(1) == 1){
				placeHuman(ZTConstants.POLICE);
			}
		}

		while (tickTime > tick){
			tickTime -= tick;
			snake.advance();

			ZTPart head = snake.parts.get(0);
			boolean isOutOfStage = head.x < 0 || head.x >= WORLD_WIDTH ||
								head.y < ZTGameScreen.WORLD_LOWER_BOUND || head.y >= WORLD_HEIGHT; 
			if (snake.checkBitten() || isOutOfStage) {
				gameOver = true;
				return;
			}
			
			if(stain.type != ZTHuman.MOVEMENT_STAY){
				moveHuman(stain);
			}
			
			if(!priest.isEmpty()){
				int priestSize = priest.size();
				for(int i = 0; i < priestSize; i++){
					moveHuman(priest.get(i));
				}
			}
			
			if(!police.isEmpty()){
				for (Iterator<ZTPolice> it = police.iterator(); it.hasNext();){
					ZTPolice p = it.next();
					int percent = MathUtils.random(100);
					if(percent >= 75){
						ZTAIMoveRoamingFiring ai = (ZTAIMoveRoamingFiring) p.getAI();
						if(ai.isSnakeInFront(snake)){
							p.fire(bullets);
						}
					} else{
						moveHuman(p);
					}
				}
			}
			
			if(!bullets.isEmpty()){
				for (Iterator<ZTBullet> it = bullets.iterator(); it.hasNext();){
					ZTBullet p = it.next();
					
					if(p.getAI().getRemainingMove() == 0){
						it.remove();
						continue;
					}
					
					p.move();
					if(isSnakePartHit(p)){
						snake.reduce();
						it.remove();
						if (snake.parts.size() == 0) {
							gameOver = true;
							return;
						}
					}
				}
			}
			
			if (head.x == stain.x && head.y == stain.y){ // eats a stain
				score += SCORE_INCREMENT;
				snake.grow();
				if (snake.parts.size() == WORLD_HEIGHT * WORLD_WIDTH) {
					gameOver = true;
					return;
				} else {
					placeHuman(ZTConstants.HUMAN);
				}
				checkLevelUpdate();
			} else{
				if (!priest.isEmpty()){
					for (Iterator<ZTPriest> it = priest.iterator(); it.hasNext();){
						ZTPriest p = it.next();
						if (head.x == p.x && head.y == p.y){
							gameOver = true;
							return;
						}
					}
				}
				
				if (!police.isEmpty()){
					for (Iterator<ZTPolice> it = police.iterator(); it.hasNext();){
						ZTPolice p = it.next();
						if (head.x == p.x && head.y == p.y){
							snake.grow();
							score += SCORE_INCREMENT;
							checkLevelUpdate();
							it.remove();
						}
					}
				}
			}
		}
	}
	
	private void checkLevelUpdate(){
		// Increase the speed every time the score reaches multiple of 100
		if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
			tick -= TICK_DECREMENT;
			level++;
			if(level > 5){
				createPoliceTime -= TICK_DECREMENT;
			}
		}
	}
	
	private void moveHuman(ZTHuman h){
		int prevX = h.x;
		int prevY = h.y;
		h.move();

		if(isSnakePartHit(h)){
			h.getAI().setRemainingMove(0);
			h.x = prevX;
			h.y = prevY;
		}
	}
	
	public boolean isSnakePartHit(ZTHuman human){
		int len = snake.parts.size();
		for (int i = 0; i < len; i++) {
			ZTPart part = snake.parts.get(i);
			if(human.x == part.x && human.y == part.y){
				return true;
			}
		}
		return false;
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

	public List<ZTPriest> getPriestList() {
		return priest;
	}

	public List<ZTPolice> getPoliceList() {
		return police;
	}
	
	public List<ZTBullet> getBulletList() {
		return bullets;
	}
}
