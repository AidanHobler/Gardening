package gardening;

import java.awt.Image;
import java.util.ArrayList;

public class Log extends Entity{
	
	private boolean right = false;
	private double speed = 5;
	public Log(double x, double y, Game game) {
		super(x, y, game, 2);
		
		if(Math.random() <= 0.5) right = true;
		ArrayList<Image> rollRight = Animation.createAnimation("log", 12);
		addAnimation(rollRight);
		ArrayList<Image> rollLeft = Animation.createAnimationReverse("log", 12);
		addAnimation(rollLeft);
		
		
	}
	
	public void update() {
		super.update();
		
		if(right) setX(getX() + speed);
		else setX(getX() - speed);
		
		//left wall
		if(getX() < 175) {
			setX(175);
			right = true;
		}
		
		//right wall
		int width = getGame().getScreenWidth();
		if(getX() + 80 > width) {
			setX(width - 80);
			right = false;
		}
		
		//animations
		animationWork();
		
		//collision
		playerCollide();
		
	}
	
	/** Helper method to deal with animations **/
	private void animationWork() {
		int ani = getAnimation();
		
		if(right && ani != 0) setAnimation(0);
		if(!right && ani != 1) setAnimation(1);
		
	}
	
	private void playerCollide() {
		if(isCollidingEnt(getGame().getPlayer())){
			getGame().endGame();
		}
	}
	
	
}
