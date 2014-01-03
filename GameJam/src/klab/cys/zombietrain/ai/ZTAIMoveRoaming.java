package klab.cys.zombietrain.ai;

import com.badlogic.gdx.math.MathUtils;

import klab.cys.zombietrain.controller.ZTConstants;
import klab.cys.zombietrain.model.ZTHuman;
import klab.cys.zombietrain.model.ZTWorld;
import klab.cys.zombietrain.view.ZTGameScreen;

public class ZTAIMoveRoaming extends ZTAIMove {
	
	public ZTAIMoveRoaming(ZTHuman human, int moveSpace) {
		super(human, moveSpace);
	}

	@Override
	public void findMoveLocation() {
		startX = human.x;
		startY = human.y;

		direction = MathUtils.random(4);

		if(direction == ZTConstants.MOVE_DOWN){
			endX = startX;
			for(int i = 0; i < moveSpace; i++){
				endY = startY - i;
				if(endY == ZTGameScreen.WORLD_LOWER_BOUND){
					break;
				}
			}
		} else if(direction == ZTConstants.MOVE_UP){
			endX = startX;
			for(int i = 0; i < moveSpace; i++){
				endY = startY + i;
				if(endY == ZTWorld.WORLD_HEIGHT - 1){
					break;
				}
			}
		} else if(direction == ZTConstants.MOVE_LEFT){
			for(int i = 0; i < moveSpace; i++){
				endX = startX - i;
				if(endX == 0){
					break;
				}
			}
		} else{
			for(int i = 0; i < moveSpace; i++){
				endX = startX + i;
				if(endX == ZTWorld.WORLD_WIDTH - 1){
					break;
				}
			}
		}
	}

	@Override
	public void move() {
		if(remainingMove == 0){
			moveSpace = MathUtils.random(ZTConstants.MIN_MOVE_SPACE, ZTConstants.MAX_MOVE_SPACE);
			remainingMove = moveSpace;
			
			findMoveLocation();
		}
		
		if(direction == ZTConstants.MOVE_DOWN){
			if(human.y != endY){
				human.y--;
			}
		} else if(direction == ZTConstants.MOVE_UP){
			if(human.y != endY){
				human.y++;
			}
		} else if(direction == ZTConstants.MOVE_LEFT){
			if(human.x != endX){
				human.x--;
			}
		} else{
			if(human.x != endX){
				human.x++;
			}
		}
		
		remainingMove--;
	}

}
