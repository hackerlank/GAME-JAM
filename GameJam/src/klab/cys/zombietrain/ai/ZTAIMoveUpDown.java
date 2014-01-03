package klab.cys.zombietrain.ai;

import klab.cys.zombietrain.controller.ZTConstants;
import klab.cys.zombietrain.model.ZTHuman;
import klab.cys.zombietrain.model.ZTWorld;
import klab.cys.zombietrain.view.ZTGameScreen;

public class ZTAIMoveUpDown extends ZTAIMove {
	
	public ZTAIMoveUpDown(ZTHuman human, int moveSpace){
		super(human, moveSpace);
	}
	
	@Override
	public void findMoveLocation() {
		startX = human.x;
		startY = human.y;
		endX = startX;
		
		int i;
		int upDistance, downDistance;
		
		// check direction going up
		for(i = 0; i < moveSpace; i++){
			endY = startY + i;
			if(endY == ZTWorld.WORLD_HEIGHT - 1){
				break;
			}
		}
		upDistance = i;
		
		// check direction going down
		for(i = 0; i < moveSpace; i++){
			endY = startY - i;
			if(endY == ZTGameScreen.WORLD_LOWER_BOUND){
				break;
			}
		}
		downDistance = i;
		
		if(upDistance > downDistance){
			endY = startY + upDistance;
			direction  = ZTConstants.MOVE_UP;
		} else{
			endY = startY - downDistance;
			direction  = ZTConstants.MOVE_DOWN;
		}
	}

	@Override
	public void move() {
		if(remainingMove == 0){
			remainingMove = moveSpace;

			if(direction == ZTConstants.MOVE_UP){
				direction = ZTConstants.MOVE_DOWN;
			} else{
				direction = ZTConstants.MOVE_UP;
			}
				
			int tmp;
			// Swap start and end Ys
			tmp = startY;
			startY = endY;
			endY = tmp;
		}
		
		if(direction == ZTConstants.MOVE_UP){
			if(human.y != endY){
				human.y++;
			}
		} else if(direction == ZTConstants.MOVE_DOWN){
			if(human.y != endY){
				human.y--;
			}
		}
		
		remainingMove--;
	}

}
