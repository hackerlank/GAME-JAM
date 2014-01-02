package klab.cys.zombietrain.model;

import java.util.ArrayList;
import java.util.List;

public class ZTBody {
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	
	public List<ZTPart> parts = new ArrayList<ZTPart>();
	public int direction;
	
	public ZTBody() {
		direction = DOWN;
		parts.add(new ZTPart(5,6));
		parts.add(new ZTPart(5,7));
		parts.add(new ZTPart(5,8));
	}
	/*
	 * Move
	 */
	public void turnLeft() {
		direction += 1;
		if (direction > RIGHT)
			direction = UP;
	}
	public void turnRight() {
		direction -= 1;
		if (direction < UP) 
			direction = RIGHT;
	}
	
	public void turnUp() {
		direction = UP;
	}
	public void turnDown() {
		direction = DOWN;
	}
	public void turnLeft2() {
		direction = LEFT;
	}
	public void turnRight2() {
		direction = RIGHT;
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
		
		if (direction == UP)
			head.y += 1;
		if (direction == LEFT)
			head.x -= 1;
		if (direction == DOWN)
			head.y -= 1;
		if (direction == RIGHT)
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
