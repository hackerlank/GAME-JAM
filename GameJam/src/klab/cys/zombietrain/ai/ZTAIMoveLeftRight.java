package klab.cys.zombietrain.ai;

import klab.cys.zombietrain.controller.ZTConstants;
import klab.cys.zombietrain.model.ZTHuman;
import klab.cys.zombietrain.model.ZTWorld;

public class ZTAIMoveLeftRight extends ZTAIMove {
	
	public ZTAIMoveLeftRight(ZTHuman human, int moveSpace){
		super(human, moveSpace);
	}

	@Override
	public void findMoveLocation() {
		startX = human.x;
		startY = human.y;
		endY = startY;
		
		int i;
		int leftDistance, rightDistance;

		// check direction going right
		for(i = 0; i < moveSpace; i++){
			endX = startX + i;
			if(endX >= ZTWorld.WORLD_WIDTH - 1){
				break;
			}
		}
		rightDistance = i;
		
		// check direction going left
		for(i = 0; i < moveSpace; i++){
			endX = startX - i;
			if(endX <= 0){
				break;
			}
		}
		leftDistance = i;
		
		if(rightDistance > leftDistance){
			endX = startX + rightDistance;
			direction  = ZTConstants.MOVE_RIGHT;
		} else{
			endX = startX - leftDistance;
			direction  = ZTConstants.MOVE_LEFT;
		}
	}

	@Override
	public void move() {
		if(remainingMove == 0){
			remainingMove = moveSpace;

			if(direction == ZTConstants.MOVE_LEFT){
				direction = ZTConstants.MOVE_RIGHT;
			} else{
				direction = ZTConstants.MOVE_LEFT;
			}
				
			int tmp;
			// Swap start and end Xs
			tmp = startX;
			startX = endX;
			endX = tmp;
		}
		
		if(direction == ZTConstants.MOVE_RIGHT){
			if(human.x != endX){
				human.x++;
			}
		} else if(direction == ZTConstants.MOVE_LEFT){
			if(human.x != endX){
				human.x--;
			}
		}
		
		remainingMove--;
	}

}
