package gardening;

import java.awt.Image;
import java.util.ArrayList;

public class Number extends Entity{
	
	String numString = "";
	
	
	public Number(int x, int y, Game game, int num) {	
		
		super(x, y, game, 1);
		setTag("number");
		
		numString += num; 
		
		ArrayList<Image> number = Animation.createAnimation(numString, 1);
		addAnimation(number);
		
	}
}
