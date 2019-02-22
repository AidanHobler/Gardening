package gardening;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
	
	private String tag = "basic";
	private double x;
	private double y;
	private int animationRate;
	private int rateLoc = 0;
	private Game game;
	
	private boolean animationIsDone = false;
	
	/**Deals with tag, which is used to identify the type of entity and is
	intended to be used for despawning specific types, etc*/
	public void setTag(String inTag) {
		this.tag = inTag;
	}
	
	public String getTag() {
		return this.tag;
	}
	
	/**Integer to keep track of which animation in outer array list
	is being displayed, first by default*/
	private int animationNum = 0;
	
	/**Keeps track of location of current frame for animation
	in the animations array list*/
	private int animationLoc = 0;
	
	/**List of animations for animation for this entity*/
	private List<ArrayList<Image>> animations;
	
	public Entity(double xPos, double yPos, Game game, int rate) {
		animations = new ArrayList<ArrayList<Image>>();
		animationRate = rate;
		x = xPos;
		y = yPos;
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	/**Spawn method, usually overridden*/
	public static void spawn(int x, int y) {
		
	}
	
	
	/**Update method, usually overridden*/
	public void update() {
		if(rateLoc == animationRate) {
			getNextImage();
			rateLoc = 0;
		}
		else
			rateLoc ++;
		
	}
	
	//Getters and setters for entity coordinates
	public void setX(double newX) {
		x = newX;
	}
	
	public void setY(double newY) {
		y = newY;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	
	
	/**Tool for use when smooth transitioning*/
	public boolean isDone() {
		return animationIsDone;
	}
	
	/**Check collision with a vertical line*/
	public boolean isCollidingVertical(int x) {
		if(this.getX() + this.getImage().getWidth(null) < x || this.getX() > x) {
			return false;
		}
		return true;
	}
	
	
	/**Check collision with another entity*/
	public boolean isCollidingEnt(Entity ent) {
		if(this.getX() > ent.getX() + ent.getImage().getWidth(null) ||
			this.getX() + this.getImage().getWidth(null) < ent.getX() ||
			this.getY() > ent.getY() + ent.getImage().getHeight(null) ||
			this.getY() + this.getImage().getHeight(null) < ent.getY()) {
			return false;
		}
		return true;
	}
	
	
	/**Change animation rate*/
	public void setRate(int rate) {
		animationRate = rate;
	}
	
	/**Add an animation to animations*/
	public void addAnimation(ArrayList<Image> animation) {
		animations.add(animation);
	}
	
	/**Get current image*/
	public Image getImage() {
		return animations.get(animationNum).get(animationLoc);
	}
	
	/**Set current animation*/
	public void setAnimation(int num) {
		animationIsDone = false;
		animationNum = num;
		animationLoc = 0;
	
	}
	
	/**Get current animation*/
	public int getAnimation() {
		return animationNum;
	}
	
	/**Make smooth transition between animations*/
	public void smoothAnimation(int targetLoc) {
		animationNum = targetLoc;
	}
	
	/**Move to next image in animation*/
	private void getNextImage() {
			
		if(animationLoc >= animations.get(animationNum).size() - 1) {
			animationLoc = 0;
			animationIsDone = true;
		}
		else {
			animationLoc ++;
			animationIsDone = false;
		}	
		
		}
	
	
		
	
	}
