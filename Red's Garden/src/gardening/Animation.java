package gardening;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;


import javax.imageio.ImageIO;

public class Animation {
	
	
	public static ArrayList<Image> createAnimation(String name, int numFrames){
		ArrayList<Image> returnList = new ArrayList<Image>(); 
		for(int i = 1; i <= numFrames; i++) {
			String fileName = "/Images/" + name + i + ".png";
			
			try {
				returnList.add(ImageIO.read(Loader.load(fileName)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return returnList;
	}
	
	public static ArrayList<Image> createAnimationReverse(String name, int numFrames){
		ArrayList<Image> returnList = new ArrayList<Image>(); 
		for(int i = numFrames; i >= 1; i--) {
			String fileName = "/Images/" + name + i + ".png";
			
			try {
				returnList.add(ImageIO.read(Loader.load(fileName)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return returnList;
	}
}
