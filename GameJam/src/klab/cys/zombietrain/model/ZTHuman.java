package klab.cys.zombietrain.model;

import klab.cys.zombietrain.ai.ZTAIBulletMoveDown;
import klab.cys.zombietrain.ai.ZTAIBulletMoveLeft;
import klab.cys.zombietrain.ai.ZTAIBulletMoveRight;
import klab.cys.zombietrain.ai.ZTAIBulletMoveUp;
import klab.cys.zombietrain.ai.ZTAIMove;
import klab.cys.zombietrain.ai.ZTAIMoveLeftRight;
import klab.cys.zombietrain.ai.ZTAIMoveRoaming;
import klab.cys.zombietrain.ai.ZTAIMoveRoamingFiring;
import klab.cys.zombietrain.ai.ZTAIMoveStay;
import klab.cys.zombietrain.ai.ZTAIMoveUpDown;

public class ZTHuman {
	public static final int MOVEMENT_STAY = 0;
	public static final int MOVEMENT_UP_DOWN = 1;
	public static final int MOVEMENT_LEFT_RIGHT = 2;
	public static final int MOVEMENT_ROAM = 3;
	public static final int MOVEMENT_ROAM_FIRING = 4;
	public static final int MOVEMENT_UP = 10;
	public static final int MOVEMENT_DOWN = 11;
	public static final int MOVEMENT_LEFT = 12;
	public static final int MOVEMENT_RIGHT = 13;
	public int x, y;
	public int type;
	
	private ZTAIMove ai;
	
	public ZTHuman( int x, int y){
		this(x, y, 0, 0);
	}
	
	public ZTHuman( int x, int y, int type){
		this(x, y, type, 0);
	}
	public ZTHuman( int x, int y, int type, int moveSpace){
		this.x = x;
		this.y = y;
		this.type = type;
		
		if(type == MOVEMENT_STAY){
			ai = new ZTAIMoveStay(this, moveSpace);
		} else if(type == MOVEMENT_UP_DOWN){
			ai = new ZTAIMoveUpDown(this, moveSpace);
		} else if(type == MOVEMENT_LEFT_RIGHT){
			ai = new ZTAIMoveLeftRight(this, moveSpace);
		} else if(type == MOVEMENT_ROAM){
			ai = new ZTAIMoveRoaming(this, moveSpace);
		} else if(type == MOVEMENT_ROAM_FIRING){
			ai = new ZTAIMoveRoamingFiring(this, moveSpace, 5);
		} else if(type == MOVEMENT_UP){
			ai = new ZTAIBulletMoveUp(this, moveSpace);
		} else if(type == MOVEMENT_DOWN){
			ai = new ZTAIBulletMoveDown(this, moveSpace);
		} else if(type == MOVEMENT_LEFT){
			ai = new ZTAIBulletMoveLeft(this, moveSpace);
		} else if(type == MOVEMENT_RIGHT){
			ai = new ZTAIBulletMoveRight(this, moveSpace);
		}
		
		ai.findMoveLocation();
	}
	
	
	public void move(){
		ai.move();
	}
	
	public ZTAIMove getAI(){
		return ai;
	}
}
