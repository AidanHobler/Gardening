package gardening;

import java.awt.Image;
import java.util.ArrayList;

//0 - Mouth closed
//1 - Mouth opening
//2 - Mouth closing

public class Durt extends Entity {
	
	private Game game;

	public Durt(Game game) {
		super(70, Game.groundlevel - 50, game, 2);
		
		this.game = game;

		ArrayList<Image> mouthClosed = Animation.createAnimation("durt", 1);
		addAnimation(mouthClosed);
		ArrayList<Image> mouthOpen = Animation.createAnimation("durt", 5);
		addAnimation(mouthOpen);
		ArrayList<Image> closeMouth = Animation.createAnimationReverse("durt", 5);
		addAnimation(closeMouth);
	}



	public void update() {
		super.update();

		if(game.getPlayer().isCollidingEnt(this) && game.getPlayer().getHasFlower()) {
			setAnimation(1);
			game.setIsEating(true);
			game.spawn(new EatenFlower(game));
			game.getPlayer().setHasFlower(false);

		}
		
		if(game.getIsEating() && getAnimation() != 1 && getAnimation() != 2) {
			setAnimation(1);
		}
		
		if(game.getIsEating() && getAnimation() == 1 && isDone()) {
			setAnimation(2);
		}
		
		if(game.getIsEating() && getAnimation() == 2 && isDone()) {
			setAnimation(0);
			game.setIsEating(false);
			game.score();
		}

	}
}
