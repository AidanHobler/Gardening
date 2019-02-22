package gardening;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;


public class ScoreManager {
	
	private ArrayList<Integer> score;
	private GameWindow window;
	private Canvas canvas;
	private Graphics graphics;
	ImageReader imageReader;
	
	public ScoreManager(GameWindow window){
		
		this.window = window;
		score = new ArrayList<Integer>();
	}
	
	public void updateScore(int scoreVal) {
		
		score.clear();
		
		while(scoreVal > 0) {
			score.add(new Integer(scoreVal % 10));
			scoreVal /= 10;

		}
		Collections.reverse(score);
		if(score.isEmpty()) score.add(0);
		
	
	}
		
	public void displayScore(int x, int y) {
		int last = x;
		for(int i = 0; i < score.size(); i++){
			
			try {
				window.getCanvas().getBufferStrategy().getDrawGraphics().drawImage
				(ImageIO.read(Loader.load("/Images/" + score.get(i) + ".png")), last, y, null);
				last = last + ImageIO.read(Loader.load("/Images/" + score.get(i) + ".png")).getWidth() + 5;
				
			}
			catch(IOException e) {
				System.out.println("Couldn't find a number (this is written in ScoreManager)");
			}
		}
	}
	
	
}
