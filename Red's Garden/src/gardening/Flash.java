package gardening;

import java.awt.Image;
import java.util.ArrayList;

public class Flash extends Entity {
	public Flash(Game game) {
		super(0, 0, game, 1);
		
		ArrayList<Image> flash = Animation.createAnimation("flash", 10);
		addAnimation(flash);
		
		}
	
		public void update() {
			super.update();
			if(isDone()) getGame().despawn(this);
		}
	
	
}
