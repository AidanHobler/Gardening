package gardening;

import java.awt.Image;

public class ImageScaler {
	public static double sizeRatio = 1;

	/** Sets the ratio for future images based on height of the screen
	 * 
	 * @param screenHeight the height of the screen
	 */
	public static void setRatio(double screenHeight) {
		sizeRatio = screenHeight/1080;
	}

	/**
	 * Given [image], return an identical Image scaled by the ratio appropriate for this game.
	 * @param image
	 * @return a new scaled Image
	 */
	public static Image scaleImage(Image image) {
		return image.getScaledInstance((int)(image.getWidth(null) * sizeRatio), 
				(int)(image.getHeight(null) * sizeRatio), Image.SCALE_SMOOTH);
	}
}
