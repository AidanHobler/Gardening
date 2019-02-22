package gardening;

import java.awt.Image;
import java.util.ArrayList;

//****PLAYER ANIMATION LIST****
//0 - Run right
//1 - Run left
//2 - Idle
//3 - Jump right
//4 - Roll right
//5 - Roll left
//6 - Jump left
//7 - Dark roll right
//8 - Dark roll left
//9 - Flower run right
//10 - Flower run left
//11 - Pass right to left
//12 - Pass left to right
//13 - Left hand idle
//14 - Right hand idle

//Player is an entity that the user controls
public class Player extends Entity {

	private static final double gAbove = 1;
	private static final double gBelow = -0.75;
	private static final double jumpHeight = -20;
	private static final double maxSpeedY = 25;
	private double g = gAbove;
	private double speedY = 0;
	private static final double xConst = 6;
	private double speedX;
	private boolean touchedGround = true;
	private boolean rolling = false;
	private boolean isAbove = true;
	private int jumpCount = 0;
	private boolean hasReleasedSpace = true;
	private boolean isPlayingAbove = true;
	private MusicPlayer music;
	private boolean forceJump = false;
	private boolean hasFlower = false;
	//A few variables for flower animations
	private boolean isInRightHand;
	private boolean isPassing;
	
	public Player(Game game) {
		super(0, Game.groundlevel - 119, game, 1);
		
		music = getGame().getMusicPlayer();
		
		ArrayList<Image> runRight = Animation.createAnimation("run", 16);
		addAnimation(runRight);
		ArrayList<Image> runLeft = Animation.createAnimation("leftrun", 16);
		addAnimation(runLeft);
		ArrayList<Image> idle = Animation.createAnimation("idle", 1);
		addAnimation(idle);
		ArrayList<Image> jumpRight = Animation.createAnimation("jump", 5);
		addAnimation(jumpRight);
		ArrayList<Image> rollRight = Animation.createAnimation("roll", 8);
		addAnimation(rollRight);
		ArrayList<Image> rollLeft = Animation.createAnimation("leftroll", 8);
		addAnimation(rollLeft);
		ArrayList<Image> jumpLeft = Animation.createAnimation("leftjump", 5);
		addAnimation(jumpLeft);
		ArrayList<Image> darkRoll = Animation.createAnimation("darkroll", 8);
		addAnimation(darkRoll);
		ArrayList<Image> leftdarkRoll = Animation.createAnimation("leftdarkroll", 8);
		addAnimation(leftdarkRoll);
		ArrayList<Image> runRightWithFlower = Animation.createAnimation("flowerrun", 16);
		addAnimation(runRightWithFlower);
		ArrayList<Image> runLeftWithFlower = Animation.createAnimation("leftflowerrun", 16);
		addAnimation(runLeftWithFlower);
		ArrayList<Image> passFlowerRightToLeft = Animation.createAnimation("righttoleft", 8);
		addAnimation(passFlowerRightToLeft);
		ArrayList<Image> passFlowerLeftToRight = Animation.createAnimation("lefttoright", 8);
		addAnimation(passFlowerLeftToRight);
		ArrayList<Image> leftHandIdle = Animation.createAnimation("lefthandidle", 1);
		addAnimation(leftHandIdle);
		ArrayList<Image> rightHandIdle = Animation.createAnimation("righthandidle", 1);
		addAnimation(rightHandIdle);
		
		
		
		
		
	}
	
	
	public double getSpeedX() {
		return speedX;
	}
	public double getSpeedY() {
		return speedY;
	}
	
	public double getG() {
		return g;
	}
	
	public void forceJump() {
		forceJump = true;
	}
	
	public void setHasFlower(boolean give) {
		hasFlower = give;
	}
	
	public boolean getHasFlower() {
		return hasFlower;
	}
	
	public void jump() {
		forceJump = false;
		
		SoundPlayer.playSound("Jump.wav");
		jumpCount++;
		speedY = jumpHeight;
		if(g == gBelow) {
			speedY = -1 * jumpHeight;
		}
		touchedGround = false;
		hasReleasedSpace = false;
		
		if(speedX >= 0 && isAbove) {
			setAnimation(3);
		}
		if(speedX < 0 && isAbove) {
			setAnimation(6);
		}
	}

	
	
	public void update() {
		super.update();
		KeyBoardListener keyListener = getGame().getKeyListener();
		
		//First check if rolling
		if(keyListener.space() && !touchedGround) {
			rolling = true;
		}
		if(!keyListener.space()) {
			rolling = false;
		}
		
		//Spawn ripple animation and sound
		if(isAbove && rolling && getY() + getImage().getHeight(null) >= Game.groundlevel){
			isAbove = false;
			getGame().spawn(new Ripple(getX(), getGame()));
			SoundPlayer.playSound("Drip.wav");
		}
		
		//Set gravity
		if(getY() + getImage().getHeight(null) < Game.groundlevel) {
			g = gAbove;
			isAbove = true;
			//Switch back to light rolling
			if(getAnimation() == 7) {
				smoothAnimation(4);
			}
			if(getAnimation() == 8) {
				smoothAnimation(5);
			}
			
		}
		else if(getY() + getImage().getHeight(null) >= Game.groundlevel && rolling) {
			g = gBelow;
			//If under ground, set to dark rolling animation
			if(getAnimation() == 4) {
				smoothAnimation(7);
			}
			if(getAnimation() == 5) {
				smoothAnimation(8);
			}
		}
		
		
		//Press right on the ground
		if(keyListener.right() && !keyListener.left()) {
			if(speedX < xConst) {
				speedX += 1;
			}
			else {
				speedX = xConst;
			}
		
			setX(getX() + speedX);
			isInRightHand = true;
	
		}
		
		//Press left on the ground
		else if(keyListener.left() && !keyListener.right()) {
			if(speedX > -xConst) {
				speedX -= 1;
			}
			else {
				speedX = -xConst;
			}
			setX(getX() + speedX);
			isInRightHand = false;
			
		}
		
		else {
			if(Math.abs(speedX) > 1) {
				speedX = speedX/1.1;
			}
			else {
				speedX = 0;
			}
			setX(getX() + speedX);
		}
		
		
		//Check walls
		if(getX() < 0) {
			setX(0);
		}
		if(getX() + getImage().getWidth(null) > getGame().getScreenWidth()){
			setX(getGame().getScreenWidth() - this.getImage().getWidth(null));
		}
		
		
		if(!touchedGround && !keyListener.space()) {
			hasReleasedSpace = true;
		}
		//Press jump on the ground
		if((keyListener.space() && jumpCount < 2 && hasReleasedSpace) || forceJump) {
			jump();
		}

		
		
			
		//After all the math, move Y coord
		setY(getY() + speedY);
		
		
		//*************************************************
		//Handle Animations
		//*************************************************
		
		//Make sure other animations can play if passing is somehow interrupted
		if(getAnimation() != 11 && getAnimation() != 12) {
			isPassing = false;
		}
		//Switch to roll if jumping right
		if(getAnimation() == 3 && isDone()) {
			setAnimation(4);
		}

		//Switch to roll if jumping left
		if(getAnimation() == 6 && isDone()) {
			setAnimation(5);
		}
	
		//Idle state
		if(speedX == 0 && touchedGround && getAnimation() != 2 && !hasFlower) {
			setAnimation(2);	
			
		}
		
		//TODO more hand stuff, bug is in the following two if statements
		//Idle with flower
		//Pass right to left
		if(speedX == 0 && touchedGround && isInRightHand && hasFlower && !isPassing) {
			if(Math.random() > 0.99) {
				isPassing = true;
				setAnimation(11);
			}


		}
		

		//Pass left to right
		if(speedX == 0 && touchedGround && !isInRightHand && hasFlower && !isPassing) {
			if(Math.random() > 0.99) {
				isPassing = true;
				setAnimation(12);
				
			}
		}
		
		//Only do passing animation once, now in left hand.
		if(getAnimation() == 11 && isDone()) {
			isPassing = false;
			isInRightHand = false;
			setAnimation(13);
		}
		
		if(getAnimation() == 12 && isDone()) {
			isPassing = false;
			isInRightHand = true;
			setAnimation(14);
		}
		
		
		//Idle Flower Right Hand
		if(speedX == 0 && touchedGround && getAnimation() != 14 && 
		   isInRightHand && !isPassing && hasFlower) {

			setAnimation(14);
	
		}
		
		//Idle Flower Left Hand
		if(speedX == 0 && touchedGround && getAnimation() != 13 && 
		   !isInRightHand && !isPassing && hasFlower) {
			setAnimation(13);
		
		}
		
		
		//Run Right
		if(speedX > 0 && touchedGround && getAnimation() != 0 && !hasFlower) {
			setAnimation(0);	
			
		}
		
		
		//Run Left
		if(speedX < 0 && touchedGround && getAnimation() != 1 && !hasFlower) {
			setAnimation(1);
			
		}
		
		//Flower Run Right
		if(speedX > 0 && touchedGround && getAnimation() != 9 && hasFlower) {
			setAnimation(9);
		}
		
		//Flower Run Left
		if(speedX < 0 && touchedGround && getAnimation() != 10 && hasFlower) {
			setAnimation(10);
		}
		
		//Jump Right
		if(speedX > 0 && getAnimation() == 6) {
			setAnimation(3);
		}
		
		//Jump left
		if(speedX < 0 && getAnimation() == 3) {
			setAnimation(6);
		}
				
		
		//Roll Right
		if(speedX > 0 && getAnimation() == 5) {
			setAnimation(4);
		}
		
		//Roll Left
		if(speedX < 0 && getAnimation() == 4) {
			setAnimation(5);
		}
		
		//Dark Roll Right
		if(speedX > 0 && getAnimation() == 8) {
			setAnimation(7);
		}
		
		//Dark Roll Left
		if(speedX < 0 && getAnimation() == 7) {
			setAnimation(8);
			
		}
		
		//Apply acceleration to speed and check terminal velocity
		speedY = speedY + g;
		if(speedY > maxSpeedY) speedY = maxSpeedY;
		if(speedY < -1 * maxSpeedY) speedY = -1 * maxSpeedY;
		
		
		//Check ground
		if(getY() + getImage().getHeight(null) >= Game.groundlevel && !rolling && g > 0) {
			speedY = 0;
			setY(Game.groundlevel - getImage().getHeight(null));
			touchedGround = true;
			hasReleasedSpace = true;
			jumpCount = 0;

		}
		

	
		
		//****************************
		//Handle Music
		//****************************
		if(g > 0 && !isPlayingAbove) {
			music.stopPlaying();
			music.playMusic(getGame().getCurrentSong(), music.getMicroTime() % music.getMicroLength());
			isPlayingAbove = true;
		}
		
		if(g < 0 && isPlayingAbove) {
			music.stopPlaying();
			music.playMusic(getGame().getCurrentSong() + "Reverb", music.getMicroTime() % music.getMicroLength());
			isPlayingAbove = false;
		}
		
	}
	
	
}


