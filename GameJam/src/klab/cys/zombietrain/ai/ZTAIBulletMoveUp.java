package klab.cys.zombietrain.ai;

import klab.cys.zombietrain.controller.ZTConstants;
import klab.cys.zombietrain.model.ZTHuman;
import klab.cys.zombietrain.model.ZTWorld;
import klab.cys.zombietrain.view.ZTGameScreen;

public class ZTAIBulletMoveUp extends ZTAIMove {
	
	public ZTAIBulletMoveUp(ZTHuman human, int moveSpace){
		super(human, moveSpace);
		remainingMove = moveSpace;
		direction  = ZTConstants.MOVE_UP;
	}
	
	@Override
	public void findMoveLocation() {
		int lastLoc = ZTWorld.WORLD_HEIGHT - 1;
		startX = human.x;
		startY = human.y + 1 <= lastLoc ? human.y + 1 : lastLoc;
		endX = startX;
		endY = startY + moveSpace <= lastLoc ? startY + moveSpace : lastLoc;
	}

	@Override
	public void move() {
		if(remainingMove == 0){
			return;
		}
	
		if(human.y != endY){
			human.y++;
		}
		
		remainingMove--;
	}

}
