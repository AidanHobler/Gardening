package gardening;

import java.awt.Image;
import java.util.ArrayList;

public class BrickBoy extends Entity{
	
	private Game game;
	public BrickBoy(int x, Game game) {
		super(x, Game.groundlevel - 300, game, 1);
		this.game = game;
		
		ArrayList<Image> idle = Animation.createAnimation("brickboy", 1);
		addAnimation(idle);
	}
	
	public @Override void update() {
		if(isCollidingEnt(game.getPlayer())) {
			//game.getPlayer().tellCoords();
		}
	}
}
