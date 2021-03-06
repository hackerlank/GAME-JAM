package klab.cys.zombietrain;

import klab.cys.zombietrain.ZTGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Serp";
		cfg.useGL20 = true;
		cfg.width = 320;
		cfg.height = 480;
		
		new LwjglApplication(new ZTGame(), cfg);
	}
}
