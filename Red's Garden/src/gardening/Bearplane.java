
package gardening;

import java.awt.Image;
import java.util.ArrayList;

public class Bearplane extends Entity{
	

	private static final double xConst = 5;
	private double speedX = -xConst;
	private double speedY = 0;
	private double yAcceleration = 0;
	private double maxY;
	private int swoopCoolDown = 0;
	
	public Bearplane(int xParam, int yParam, boolean direction, Game game) {
		super(xParam, yParam, game, 2);
		
		if(direction) {
			speedX = -speedX;
		}
		maxY = yParam;
		ArrayList<Image> flyStraight = Animation.createAnimation("bearplane", 1);
		addAnimation(flyStraight);
		ArrayList<Image> flyStraightLeft = Animation.createAnimation("bearplaneleft", 1);
		addAnimation(flyStraightLeft);
	}

	
	public void update() {
		super.update();
		
		if(getX() + getImage().getWidth(null) >= getGame().getScreenWidth()) {
			speedX = -xConst;
		}
		
		if(getX() < 0) {
			speedX = xConst;
		}

		setX(getX() + speedX);
		
		//Deal with Y coords and swooping
		
		
		if(getY() <= maxY) {
			speedY = 0;
			yAcceleration = 0;
			
		}
		
		//Check to swoop from left
		if(speedX > 0 && getGame().getPlayer().getX() - this.getX() > 0 && getGame().getPlayer().getX() - this.getX() < 300 && swoopCoolDown == 0) {
			swoop();
		}
		
		//Swoop from right
		if(speedX < 0 && getGame().getPlayer().getX() - this.getX() < 0 && getGame().getPlayer().getX() - this.getX() > -300 && swoopCoolDown == 0) {
			swoop();
		}
		
		//Animations
		if(speedX < 0 && getAnimation() != 1) {
			setAnimation(1);
		}
		
		if(speedX > 0 && getAnimation() != 0) {
			setAnimation(0);
		}
		
		setY(getY() + speedY);
		speedY += yAcceleration;
		
		//Cooldown swoop
		if(swoopCoolDown > 0) {
			swoopCoolDown--;
		}
		
		if(swoopCoolDown < 0) {
			swoopCoolDown = 0;
		}
		
		//Collisions with player
		if(isCollidingEnt(getGame().getPlayer())) {
			if(getGame().getPlayer().getY() > getY()) {
				getGame().endGame();
			}
			if(getGame().getPlayer().getY() <= getY()) {
				getGame().getPlayer().forceJump();
				getGame().despawn(this);
			}
		}
		

	}
	
	private void swoop() {
		speedY = 13;
		yAcceleration = -0.3;
		swoopCoolDown = 180;
	}
}
