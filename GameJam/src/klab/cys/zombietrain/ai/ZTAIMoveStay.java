package klab.cys.zombietrain.ai;

import klab.cys.zombietrain.model.ZTHuman;

public class ZTAIMoveStay extends ZTAIMove{
	
	public ZTAIMoveStay(ZTHuman human, int moveSpace){
		super(human, moveSpace);
	}
	
	@Override
	public void findMoveLocation() {
		startX = human.x;
		startY = human.y;
		endX = startX;
		endY = startY;
	}

	@Override
	public void move() {
		
	}

}
