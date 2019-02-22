package gardening;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Menu extends JFrame implements Runnable{

	private boolean menuRunning = true;
	private int screenX = 1000;
	private int screenY = 600;
	private Canvas canvas = new Canvas();
	private KeyBoardListener keyListener = new KeyBoardListener();
	private BufferedImage background;
	MusicPlayer music = new MusicPlayer();
	GameWindow window;

	public Menu(GameWindow window) {
		
		this.window = window;
		canvas = window.getCanvas();
		
		
		//Add Listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);

		//Get background
		try {
			background = ImageIO.read(Loader.load("/Images/Title Screen.png"));

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

		//Push buffer to screen
		bufferStrategy.show();


	}


	public void run() {

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

	/*public static void main(String[] args) {
		Menu menu = new Menu();
		Thread menuThread = new Thread(menu);
		menuThread.start();
	}
	 */
}
