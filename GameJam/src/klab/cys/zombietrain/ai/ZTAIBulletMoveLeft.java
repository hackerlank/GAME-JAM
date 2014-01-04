package klab.cys.zombietrain.ai;

import klab.cys.zombietrain.controller.ZTConstants;
import klab.cys.zombietrain.model.ZTHuman;
import klab.cys.zombietrain.model.ZTWorld;
import klab.cys.zombietrain.view.ZTGameScreen;

public class ZTAIBulletMoveLeft extends ZTAIMove {
	
	public ZTAIBulletMoveLeft(ZTHuman human, int moveSpace){
		super(human, moveSpace);
		remainingMove = moveSpace;
		direction  = ZTConstants.MOVE_LEFT;
	}
	
	@Override
	public void findMoveLocation() {
		int lastLoc = 0;
		startX = human.x  - 1 >= lastLoc ? human.x - 1 : lastLoc;
		startY = human.y;
		endX = startX - moveSpace >= lastLoc ? startX - moveSpace : lastLoc;
		endY = startY;
	}

	@Override
	public void move() {
		if(remainingMove == 0){
			return;
		}
	
		if(human.x != endX){
			human.x--;
		}
		
		remainingMove--;
	}

}
