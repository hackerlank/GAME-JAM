package klab.cys.zombietrain.model;

import java.util.List;

import klab.cys.zombietrain.ai.ZTAIMoveRoamingFiring;
import klab.cys.zombietrain.controller.ZTConstants;

import com.badlogic.gdx.math.MathUtils;

public class ZTPolice extends ZTHuman {
	public float timer;
	
	public ZTPolice(int x, int y, float timer) {
		super(x, y, ZTHuman.MOVEMENT_ROAM_FIRING);
		this.timer = timer;
		
		ZTAIMoveRoamingFiring ai = (ZTAIMoveRoamingFiring) getAI();
		ai.setBulletRange(MathUtils.random(5, 8));
	}
	
	public void fire(List<ZTBullet> bullets){
		ZTAIMoveRoamingFiring ai = (ZTAIMoveRoamingFiring) getAI();
		int direction = ai.getDirection();
		ZTBullet b;
		if(direction == ZTConstants.MOVE_DOWN){
			b = new ZTBullet(x, y, ZTHuman.MOVEMENT_DOWN, ai.getBulletRange());
		} else if(direction == ZTConstants.MOVE_UP){
			b = new ZTBullet(x, y, ZTHuman.MOVEMENT_UP, ai.getBulletRange());
		} else if(direction == ZTConstants.MOVE_LEFT){
			b = new ZTBullet(x, y, ZTHuman.MOVEMENT_LEFT, ai.getBulletRange());
		} else{
			b = new ZTBullet(x, y, ZTHuman.MOVEMENT_RIGHT, ai.getBulletRange());
		}
		bullets.add(b);
	}
}
