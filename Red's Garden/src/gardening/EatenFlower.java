package gardening;

import javax.imageio.ImageIO;

public class EatenFlower extends Flower {
	public EatenFlower(Game game) {
		super(game, 120, Game.groundlevel - 95);
	}
	
	public @Override void update() {
		if(getY() + 35 >= Game.groundlevel) {
			getGame().despawn(this);
		}
		setY(getY() + 1);
		
	}
}