package klab.cys.gamejam.controller;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;

public class SwipeGestureDetector extends GestureDetector {
	public interface SwipeListener {
		void onLeft();

		void onRight();

		void onUp();

		void onDown();
	}

	public SwipeGestureDetector(SwipeListener swipeListener) {
		super(new SwipeGestureListener(swipeListener));
	}
	
	private static class SwipeGestureListener extends GestureAdapter{
		SwipeListener swipeListener;
		
		public SwipeGestureListener(SwipeListener swipeListener){
			this.swipeListener = swipeListener;
		}
		
		@Override
        public boolean fling(float velocityX, float velocityY, int button) {
			if(Math.abs(velocityX)>Math.abs(velocityY)){
				if(velocityX>0){
						swipeListener.onRight();
				}else{
						swipeListener.onLeft();
				}
			}else{
				if(velocityY>0){
						swipeListener.onDown();
				}else{                                  
						swipeListener.onUp();
				}
			}
			return super.fling(velocityX, velocityY, button);
        }

	}

}
