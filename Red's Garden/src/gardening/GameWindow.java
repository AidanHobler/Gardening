package gardening;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

//TODO kill duplicate music
public class GameWindow extends JFrame{
	
	Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
	
	private int screenX = (int)screenDimensions.getWidth();
	private int screenY = (int)screenDimensions.getHeight();

	private Canvas canvas = new Canvas();

	public GameWindow() {
		//Exit on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set position and size of screen
		setBounds(0, 0, screenX, (int)(screenY * ImageScaler.sizeRatio));

		//Position game in center of screen
		setLocationRelativeTo(null);

		setExtendedState(Frame.MAXIMIZED_BOTH);
		setResizable(true);

		//Add graphics component canvas
		add(canvas);

		//Has to come before createBufferStrategy because that method
		//needs to know that there is a screen available
		setVisible(true);

		setTitle("Gardening");


		//3 buffers, one to paint to, one to display, one to go
		//between for smoothness
		canvas.createBufferStrategy(3);

		

		

	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public static void main(String[] args) {
		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		ImageScaler.setRatio(screenDimensions.getHeight());
		GameWindow window = new GameWindow();
		Menu menu = new Menu(window);
		Thread menuThread = new Thread(menu);
		menuThread.start();
	}

}
