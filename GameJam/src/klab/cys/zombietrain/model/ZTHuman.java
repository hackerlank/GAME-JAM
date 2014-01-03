package klab.cys.zombietrain.model;

import klab.cys.zombietrain.controller.ZTConstants;
import klab.cys.zombietrain.view.ZTGameScreen;

public class ZTHuman {
	public static final int TYPE_1 = 0;
	public static final int TYPE_2 = 1;
	public static final int TYPE_3 = 2;
	public int x, y;
	public int type;
	public int direction;
	
	public int remainingMove, moveSpace;
	private int startX, startY, endX, endY;
	
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
		this.moveSpace = moveSpace;

		remainingMove = moveSpace;
		findMovingLocation();
	}
	
	public void findMovingLocation(){
		startX = x;
		startY = y;
		if(type == TYPE_1){
			endX = startX;
			endY = startY;
		} else if(type == TYPE_2){ // move up and down
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
		} else if(type == TYPE_3){ // move left or right
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
			
			// check direction going down
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
	}
	
	public void move(){
		if(type == TYPE_1){
			return;
		} else if(type == TYPE_2){
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
				if(y != endY){
					y++;
				}
			} else if(direction == ZTConstants.MOVE_DOWN){
				if(y != endY){
					y--;
				}
			}
			
			remainingMove--;
		} else if(type == TYPE_3){
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
				if(x != endX){
					x++;
				}
			} else if(direction == ZTConstants.MOVE_LEFT){
				if(x != endX){
					x--;
				}
			}
			
			remainingMove--;
		}
	}
}
