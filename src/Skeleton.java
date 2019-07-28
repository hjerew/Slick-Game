import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Skeleton extends InteractingSprite {
	// Time variable and constructor
	private int time;
	public Skeleton(int x, int y) throws SlickException {
		super(App.SKULL_LOCATION, x, y);
	}
	
	/** Updates time and returns if the timer has been hit (resets timer if it has) */
	public boolean updateTime(int delta) {
		boolean triggered=false;
		this.time += delta;
		if (time>App.SKELETONTIMER) {
			time=0;
			return triggered = true;
		} 
		return triggered;
	}
	
	
	
	public void update(Input input, int delta, InteractingSprite[][] interactableSprites) {
		if(updateTime(delta)){
			if(npcCheckMove(super.getPosX(),super.getPosY(),super.getDirection(),interactableSprites)) {
				// Moves in the sprite array if an npc move is possible there
				moveInSpriteArray(super.getPosX(),super.getPosY(), super.getDirection(), interactableSprites);
			} else {
				// Reverses direction if move isn't possible
				super.setDirection(InteractingSprite.reverseDirection(super.getDirection()));
			}
		}
	}
}