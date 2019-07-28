import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Ice extends InteractingSprite {
	// Time variable initialised and constructor
	private int time=0;
	public Ice(int x, int y) throws SlickException {
		super(App.ICE_LOCATION, x, y);
	}
	
	/** Updates time and returns if the timer has been hit (resets timer if it has) */
	public boolean updateTime(int delta) {
		boolean triggered=false;
		time += delta;
		if (time>App.ICETIMER) {
			time=0;
			return triggered = true;
		} 
		return triggered;
	}
	
	
	public void update(Input input, int delta, InteractingSprite[][] interactableSprites) {
		// if the timer threshold is triggered, ice updates
		if(updateTime(delta)) {
			// Projects coordinates of next move
			int[] nextCoords = calcProjMove(super.getPosX(),super.getPosY(),super.getDirection());
			
			if (!(interactableSprites[nextCoords[0]][nextCoords[1]] instanceof InteractingSprite)) {
				// Moves the ice in the interactable sprite array and sets it's moving status to true
				moveInSpriteArray(super.getPosX(),super.getPosY(),super.getDirection(), interactableSprites);
				this.setMovingStatus(true);
				
			} else if (interactableSprites[nextCoords[0]][nextCoords[1]] instanceof Switch) {
				// Ice also moves onto a switch
				moveInSpriteArray(super.getPosX(),super.getPosY(),super.getDirection(), interactableSprites);
				this.setMovingStatus(true);
			} else {
				// Stops ice moving and clears the direction it's moving
				super.setMovingStatus(false);
				super.setDirection(App.NODIRECTION);
			}
		}
	}
}