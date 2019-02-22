package gardening;

import java.awt.Image;
import java.util.ArrayList;

public class Mullet extends Entity {
	
	private final double speed = 1;
	private double currentSpeed = speed;
	private boolean right = true;
	private double boostTimer = 0;
	
	
	public Mullet(double x, double y, Game game) {
		super(x, y, game, 1);
		if(Math.random() <= 0.5) right = false;
		ArrayList<Image> mulletRight = Animation.createAnimation("mullet", 1);
		addAnimation(mulletRight);
		
		ArrayList<Image> mulletLeft = Animation.createAnimation("mulletLeft", 1);
		addAnimation(mulletLeft);
		
		
	}
	
	private void boost() {
		currentSpeed = 8;
		boostTimer = 100;
	}
	
	
	public void update() {
		
		//Speed up when player is underground
		if(getGame().getPlayer().getY() >= this.getY() - 100) {
			boost();
		}
	
		
		//Check walls
		if(getX() < 0 && !right) {
			setX(0);
			right = true;
		}
		if(getX() + getImage().getWidth(null) > getGame().getScreenWidth() && right){
			setX(getGame().getScreenWidth()-this.getImage().getWidth(null));
			right = false;
		}
		
		//Do movement
		if(right) {
			this.setX(this.getX() + currentSpeed);
			setAnimation(0);
		}
		else {
			this.setX(this.getX() - currentSpeed);
			setAnimation(1);
		}
		
		//Collisions with player
		if(isCollidingEnt(getGame().getPlayer())) {
				getGame().endGame();
		}
		
		boostTimer--;
		
		if(boostTimer <= 0) {
			currentSpeed = speed;
		}
	}
}
