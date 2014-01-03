package klab.cys.zombietrain.ai;

import klab.cys.zombietrain.model.ZTHuman;

public abstract class ZTAIMove {
	protected int remainingMove, moveSpace;
	protected int startX, startY, endX, endY;
	protected int direction;

	ZTHuman human;
	
	public ZTAIMove(ZTHuman human, int moveSpace){
		this.human = human;
		this.moveSpace = moveSpace;
	}
	
	public abstract void findMoveLocation();
	public abstract void move();
	
	public void setRemainingMove(int remainingMove){
		this.remainingMove = remainingMove;
	}
	
	public int getRemainingMove(){
		return remainingMove;
	}

	public int getMoveSpace() {
		return moveSpace;
	}

	public void setMoveSpace(int moveSpace) {
		this.moveSpace = moveSpace;
	}
}
