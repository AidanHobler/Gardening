package gardening;

import java.awt.Image;
import java.util.ArrayList;

public class Flower extends Entity {
	
	private Game game;
	private double bobSpeed = 0.5;
	private double bobAcceleration = 0.05;
	
	
	public Flower(Game game, int x, int y) {
		//super(game.getScreenWidth() - 120, Game.groundlevel - 150, game, 1);
		super(x, y, game, 1);
		ArrayList<Image> idle = Animation.createAnimation("flower", 3);
		addAnimation(idle);
		this.game = game;
	}
	
	
	public @Override void update() {
		
		super.update();
		//Collision with Player
		if(isCollidingEnt(game.getPlayer())) {
			game.despawn(this);
			game.getPlayer().setHasFlower(true);
		}
		
		bobSpeed += bobAcceleration;
		if(Math.abs(bobSpeed) > 2) {
			bobAcceleration = -bobAcceleration;
		}
		setY(getY() + bobSpeed);
		
		
		
		
	
	}
	
	/*
	private double bobSpeed = 0.5;
	private double bobAcceleration = 0.05;
	private double ySpeed;
	private double yAcceleration;
	private boolean isGettable = true;
	private boolean initialDrop = true;
	
	public Flower(Game game) {
		super(game.getScreenWidth() - 120, Game.groundlevel - 150, game, 1);
		
		ArrayList<Image> idle = Animation.createAnimation("flower", 3);
		addAnimation(idle);
	}
	
	public void setGettable(boolean bool) {
		
	}
	

	public void update() {
		super.update();
		
		//TODO fix stuff here
		/*
		if(!getGame().getIsEating()) {
			bobSpeed += bobAcceleration;
			if(Math.abs(bobSpeed) > 2) {
				bobAcceleration = -bobAcceleration;
			}
			setY(getY() + bobSpeed);
		}
		
		if(isCollidingEnt(getGame().getPlayer()) && isGettable){
			attachToPlayer();
			
		}
		
		if(getGame().getIsEating()) {
			if(initialDrop) {
				setY(400);
			}
			else {
				setY(getY() + 1);
			}
			initialDrop = false;
			setX(210);
			
			if(getY() > Game.groundlevel - 50) {
				getGame().score();
				getGame().setIsEating(false);
				setX(1800);
				setY(Game.groundlevel - 150);
				isGettable = true;
			}
		}
	


		ySpeed += yAcceleration;
		setY(getY() + ySpeed);
		
	}
	
	public void attachToPlayer() {
		getGame().despawn(this);
		getGame().getPlayer().setHasFlower(true);
		
		
	}
	*/
}
