package klab.cys.zombietrain.ai;

import klab.cys.zombietrain.controller.ZTConstants;
import klab.cys.zombietrain.model.ZTBody;
import klab.cys.zombietrain.model.ZTHuman;
import klab.cys.zombietrain.model.ZTPart;
import klab.cys.zombietrain.model.ZTWorld;
import klab.cys.zombietrain.view.ZTGameScreen;

public class ZTAIMoveRoamingFiring extends ZTAIMoveRoaming {
	int bulletRange;
	
	public ZTAIMoveRoamingFiring(ZTHuman human, int moveSpace, int bulletRange) {
		super(human, moveSpace);
		this.bulletRange = bulletRange;
	}

	public boolean isSnakeInFront(ZTBody snake){
		if(direction == ZTConstants.MOVE_DOWN){
			int lastLoc = ZTGameScreen.WORLD_LOWER_BOUND;
			int start = human.y - 1 >= lastLoc ? human.y - 1 : lastLoc;
			int stop = start - bulletRange >= lastLoc ? start - bulletRange : lastLoc;
			
			for(ZTPart p : snake.parts){
				if(p.y <= start && p.y >= stop && p.x == human.x){
					return true;
				}
			}
		} else if(direction == ZTConstants.MOVE_UP){
			int lastLoc = ZTWorld.WORLD_HEIGHT - 1;
			int start = human.y + 1 <= lastLoc ? human.y + 1 : lastLoc;
			int stop = start + bulletRange <= lastLoc ? start + bulletRange : lastLoc;
			
			for(ZTPart p : snake.parts){
				if(p.y >= start && p.y <= stop && p.x == human.x){
					return true;
				}
			}
		} else if(direction == ZTConstants.MOVE_LEFT){
			int lastLoc = 0;
			int start = human.x - 1 >= lastLoc ? human.x - 1 : lastLoc;
			int stop = start - bulletRange >= lastLoc ? start - bulletRange : lastLoc;
			
			for(ZTPart p : snake.parts){
				if(p.x <= start && p.x >= stop && p.y == human.y){
					return true;
				}
			}
		} else{
			int lastLoc = ZTWorld.WORLD_WIDTH - 1;
			int start = human.x + 1 <= lastLoc ? human.x + 1 : lastLoc;
			int stop = start + bulletRange <= lastLoc ? start - bulletRange : lastLoc;
			
			for(ZTPart p : snake.parts){
				if(p.x >= start && p.x <= stop && p.y == human.y){
					return true;
				}
			}
		}
		
		return false;
	}

	public void setBulletRange(int bulletRange){
		this.bulletRange = bulletRange;
	}
	
	public int getBulletRange(){
		return bulletRange;
	}
}
