package gardening;

import java.awt.Image;
import java.util.ArrayList;

public class Ripple extends Entity {
	public Ripple(double x, Game game) {
		super(x - 45, Game.groundlevel - 158, game, 1);
		
		ArrayList<Image> ripple = Animation.createAnimation("ripple", 17);
		addAnimation(ripple);
	}
	
	
	public void update() {
		super.update();
		if(isDone()) {
			getGame().despawn(this);
		}
	}
}
