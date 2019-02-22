package gardening;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class EndScreen implements Runnable{

	private boolean menuRunning = true;
	private Canvas canvas = new Canvas();
	private KeyBoardListener keyListener = new KeyBoardListener();
	private BufferedImage background;
	private ScoreManager scoreManager;
	MusicPlayer music = new MusicPlayer();
	GameWindow window;


	public EndScreen(GameWindow window, int finalScore) {

		this.window = window;
		canvas = window.getCanvas();
		canvas.createBufferStrategy(3);

		//Add Listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		
		//Score manager to display final score
		scoreManager = new ScoreManager(window);
		scoreManager.updateScore(finalScore);

		//Get background
		try {
			background = ImageIO.read(Loader.load("/Images/end screen.png"));

		}
		catch(IOException e) {};
	}

	public void render() {

		//Get current buffer
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();


		//Set that buffer as graphics context
		Graphics graphics = bufferStrategy.getDrawGraphics();

		//Draw background
		graphics.drawImage(background, 0, 0, null);
		
		//Display score
		scoreManager.displayScore(425, 94);

		//Push buffer to screen
		bufferStrategy.show();


	}

	public @Override void run() {
		try {
			music.playMusic("Menu Song", 0);
		}
		catch(Exception e) {}
		
		while(menuRunning) {
			render();

			if(keyListener.space()) {
				menuRunning = false;
			}


			try {
				Thread.sleep(10);
			}
			catch(Exception e) {}
		}

		music.stopPlaying();
		SoundPlayer.playSound("Start Sound.wav");
		Game game = new Game(window);
		Thread gameThread = new Thread(game);
		gameThread.start();
		
	}

}
