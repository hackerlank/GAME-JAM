package klab.cys.zombietrain.model;

public class ZTPriest extends ZTHuman {
	public float timer;
	
	public ZTPriest(int x, int y, float timer) {
		super(x, y, ZTHuman.MOVEMENT_ROAM);
		this.timer = timer;
	}

}
