package gardening;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game implements Runnable {
	
	//TODO add pow effect when you die
	
	
	//Create PIVs for game loop
	private boolean gameRunning = true;

	private int score = 0;
	
	//TODO write flash class, don't forget to delete old png and import new
	Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();

	private int listLoc = 0;
	private int screenX = (int)screenDimensions.getWidth();
	private int screenY = (int)screenDimensions.getHeight();
	private BufferedImage background;
	private double timeInSeconds = 0;
	public static final int groundlevel = 490;
	private MusicPlayer music = new MusicPlayer();
	private boolean isEating = false;
	private String currentSong = "Reds Song";
	private GameWindow window;
	private boolean listHasChanged = false;
	private Durt durt;
	private int freezeTime = 130;
	private boolean dead = false;
	
	/*
	private String[] messages = {
		"Feed Durt! He needs them flowers!",
		"Gardening",
		"Follow @michaelsoftpaint on Instagram",
		"This game is a party and you're invited!",
		"Doo doo doo doo doo doo doo doo doooo",
		"Jump over that guy!",
		"Hey it's me Mike, I'm going to the store, want anything?",
		"There's cereal in the cupboard",
		"I'm legally obligated to inform you that Beard Man is ripped",
		"Soft!"
	};
	*/
	
	private String[] spawnables = {
			"Bearplane",
			"Mullet",
			"Log"
	};
	

	private List<Entity> entList = new ArrayList<Entity>();
	private List<Entity> tempEntList = new ArrayList<Entity>();
	
	private Player player;
	
	private Canvas canvas;
	
	private KeyBoardListener keyListener = new KeyBoardListener();
	
	private ScoreManager scoreManage;
	
	//To be used by other entities to score a point
	public void score() {
		setScore(score + 1);
		tempEntList.clear();
		listHasChanged = true;
		masterSpawn();
		spawn(new Flash(this));
		SoundPlayer.playSound("Point Scored.wav");
	}
	
	//Methods to deal with spawning
	
	/**Control spawns for everything*/
	
	private void masterSpawn() {
		
		
		spawn(new BrickBoy(1000, this));
		spawn(new Flower(this, getScreenWidth() - 120, Game.groundlevel - 130));
		
		int max = (int)(score * Math.random() + 1);
		if(max > 7) max = 7;
		for(int i = 0; i < max; i++) {
			int spawnNum = (int)(Math.random() * spawnables.length);
			
			switch(spawnables[spawnNum]) {
			case "Bearplane":
				spawnBearplane();
				break;
			case "Mullet":
				spawnMullet();
				break;
			case "Log":
				spawnLog();
				break;
			}
		}
	}
	
	
	//Pick spawn x on right half of level
	/**Spawn a Bearplane*/
	private void spawnBearplane() {
		double x;
		double y;
		double mid = screenX/2;
		x = (Math.random() * mid) + mid;
		y = 100;
		
		spawn(new Bearplane((int)x, (int)y, false, this));
	}
	
	
	/**Spawn a Mullet*/
	private void spawnMullet() {
		double x;
		double y;
		double mid = screenX/2;
		x = Coords.calc((int)mid, screenX);
		y = Coords.calc(groundlevel + 70, groundlevel + 480);
		spawn(new Mullet((int)x, (int)y, this));
	}
	
	/**Spawn a Log*/
	private void spawnLog() {
		double x;
		x = Coords.calc(screenX - 800, screenX);
		
		spawn(new Log(x, Game.groundlevel - 60, this));
	}
	
	
	public Game(GameWindow window) {
		
		this.window = window;
		canvas = window.getCanvas();
		
		//Add Listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		
		//Get background
		try {
			background = ImageIO.read(Loader.load("/Images/background.png"));
			
		}
		catch(IOException e) {};
		
		//Set up score manager
		scoreManage = new ScoreManager(window);
		scoreManage.updateScore(this.score);
		
		durt = new Durt(this);
	}
	

	public int getScreenWidth() {
		return screenX;
	}
	
	public int getScreenHeight() {
		return screenY;
	}
	public int getListLoc() {
		return listLoc;
	}
	
	//TODO fix isEating stuff
	public void setIsEating(boolean eat) {
		isEating = eat;
	}
	
	public boolean getIsEating() {
		return isEating;
	}
	
	public String getCurrentSong() {
		return currentSong;
	}
	
	//Increase score and update it in manager
	public void setScore(int scoreParam) {
		score = scoreParam;
		scoreManage.updateScore(score);
	}
	
	public int getScore() {
		return score;
	}
	
	
	public KeyBoardListener getKeyListener(){
		return keyListener;
	}
	
	public MusicPlayer getMusicPlayer() {
		return music;
	}
	
	/**Adds entity to queued list*/
	public void spawn(Entity ent) {
		tempEntList.add(ent);
		listHasChanged = true;
	}
	
	/**Removes entity from queued list*/
	public void despawn(Entity ent) {
		tempEntList.remove(ent);	
		listHasChanged = true;
	}
	
	/**Despawns entities of a certain tag (newer, check here for location issues)*/
	public void despawnTag(String inTag) {
		for(int i = 0; i < entList.size(); i++) {
			if(entList.get(i).getTag().equals(inTag)) {
				tempEntList.remove(entList.get(i));
			}
		}
		listHasChanged = true;
		
	}
	
	
	
	/*
	public String getMessage() {
		return messages[(int)(Math.random() * messages.length)];
	}
	*/
	
	/**Get player*/
	public Player getPlayer() {
		return player;
	}
	
	
	/**Called when you die*/
	public void endGame() {
		music.stopPlaying();
		dead = true;
		
	}
	
	/**Called after slight pause when you die to actually quit to end screen*/
	private void quit() {
		Thread endThread = new Thread(new EndScreen(window, score));
		endThread.start();
		gameRunning = false;
	}
	
	public void render() {
		
		
		//Get current buffer
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		
		
		//Set that buffer as graphics context
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		//Draw background
		graphics.drawImage(background, 0, 0, null);
		
		//Render contents of entList
		for(Entity ent: entList) {
			graphics.drawImage(ent.getImage(), (int)ent.getX(), (int)ent.getY(), null);
		}
		
		graphics.drawImage(durt.getImage(), (int)durt.getX(), (int)durt.getY(), null);
		graphics.drawImage(player.getImage(), (int)player.getX(), (int)player.getY(), null);
		
		scoreManage.displayScore(20, 15);
		
		
		
		//Push buffer to screen
		bufferStrategy.show();
		

	}
	
	public void update() {


		if(dead) freezeTime--;

		else {
			player.update();
			durt.update();

			for(Entity i: entList) {
				i.update();
			}

			//TODO making a new arraylist every time is probably not ideal
			if(listHasChanged) {
				entList = new ArrayList<Entity>(tempEntList);
				listHasChanged = false;
			}

			//Pause at the end

		}
		
		if(freezeTime == 110) SoundPlayer.playSound("death.wav");
		if(freezeTime == 0) quit();
	}
	
	
	

	//IDEA: When flower is returned and point is scored, clear entList (except for player)
	public void run() {
		
		long lastTime = System.nanoTime();
		double nanoSecondConversion = 60/1000000000.0; //Double to convert nano seconds to seconds
		double deltaSeconds = 0;
		
		player = new Player(this);	
		
		masterSpawn();

		
		//Play Music
		
		music.playMusic(currentSong, 0);
	
		//Game Loop, formatted this way to short circuit
		while(gameRunning) {
			
			//to pause at the end when you die
			

			long now = System.nanoTime();
			
			
			/*
			//Some fun with the title
			if(timeInSeconds >= 15) {
				this.setTitle(getMessage());
				timeInSeconds = 0;
			}
			*/
			
			deltaSeconds += (now - lastTime) * nanoSecondConversion;
			
			while(deltaSeconds >= 1) {
				timeInSeconds += deltaSeconds / 60;
				update();
				render();
				deltaSeconds = 0;
			}
			
			
			lastTime = now;
		}
		music.stopPlaying();
 }


}
