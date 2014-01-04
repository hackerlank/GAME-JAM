package klab.cys.zombietrain.model;

import java.util.ArrayList;
import java.util.List;

import klab.cys.zombietrain.controller.ZTConstants;

public class ZTBody {
	public List<ZTPart> parts = new ArrayList<ZTPart>();
	public int direction;
	
	public ZTBody() {
		direction = ZTConstants.MOVE_DOWN;
		parts.add(new ZTPart(5,6));
		parts.add(new ZTPart(5,7));
		parts.add(new ZTPart(5,8));
	}
	/*
	 * Move
	 */
	public void turnUp() {
		direction = ZTConstants.MOVE_UP;
	}
	public void turnDown() {
		direction = ZTConstants.MOVE_DOWN;
	}
	public void turnLeft() {
		direction = ZTConstants.MOVE_LEFT;
	}
	public void turnRight() {
		direction = ZTConstants.MOVE_RIGHT;
	}
	public void turnDownOrLeft() {
		if (direction == ZTConstants.MOVE_LEFT || direction == ZTConstants.MOVE_RIGHT)
			direction = ZTConstants.MOVE_DOWN;
		else if (direction == ZTConstants.MOVE_UP || direction == ZTConstants.MOVE_DOWN)
			direction = ZTConstants.MOVE_LEFT;
	}
	public void turnUpOrRight() {
		if (direction == ZTConstants.MOVE_LEFT || direction == ZTConstants.MOVE_RIGHT)
			direction = ZTConstants.MOVE_UP;
		else if (direction == ZTConstants.MOVE_UP || direction == ZTConstants.MOVE_DOWN)
			direction = ZTConstants.MOVE_RIGHT;
	}
	public void advance(){
		ZTPart head = parts.get(0);
		
		int len = parts.size() - 1;
		for (int i = len; i > 0; i --){
			ZTPart before = parts.get(i-1);
			ZTPart part = parts.get(i);
			part.x = before.x;
			part.y = before.y;
		}
		
		if (direction == ZTConstants.MOVE_UP)
			head.y += 1;
		if (direction == ZTConstants.MOVE_LEFT)
			head.x -= 1;
		if (direction == ZTConstants.MOVE_DOWN)
			head.y -= 1;
		if (direction == ZTConstants.MOVE_RIGHT)
			head.x += 1;
		// Move the head to opposite side when it reach the side
//		if (head.x < 0)
//			head.x = 9;
//		if (head.x > 9)
//			head.x = 0;
//		if (head.y < 2)
//			head.y = 14;
//		if (head.y > 14)
//			head.y = 2;
	}
	/*
	 * Others
	 */
	public void grow() {
		ZTPart end = parts.get(parts.size()-1);
		parts.add(new ZTPart(end.x,end.y));
	}
	public void reduce() {
		parts.remove(parts.size()-1);
	}
	public boolean checkBitten() {
		int len = parts.size();
		ZTPart head = parts.get(0);
		for (int i = 1; i<len; i++){
			ZTPart part = parts.get(i);
			if (part.x == head.x && part.y == head.y)
				return true;
		}
		return false;
	}
}
