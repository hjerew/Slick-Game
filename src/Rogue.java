import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Rogue extends InteractingSprite {
	// Initialises previous target and constructor
	private int[] prevTarget;
	public Rogue(int x, int y) throws SlickException {
		super(App.ROGUE_LOCATION, x, y);
		super.setDirection(App.LEFT);
	}
	
	/** Sets the target coordinates to work out if the player has moved */
	public void setTargetCoords(int[] coords, InteractingSprite[][] interactableSprites) {
		if(super.getTargetCoords() != null) {
			this.prevTarget = super.getTargetCoords();
		} else {
			this.prevTarget=coords;
		}
		super.setTargetCoords(coords,interactableSprites);
	}
	
	public void update(Input input, int delta, InteractingSprite[][] interactableSprites) {
		// Compares previous and current target to see if the player has moved
		if((prevTarget[0] != super.getTargetCoords()[0]) || (prevTarget[1] != super.getTargetCoords()[1]) ) {
			// Projects rogue coordinates
			int[] proj = InteractingSprite.calcProjMove(super.getPosX(),super.getPosY(),super.getDirection());
			int rogueProjX=proj[0];
			int rogueProjY=proj[1];
			
			if(InteractingSprite.checkForPush(rogueProjX,rogueProjY,
					super.getDirection(),interactableSprites,input,delta)) {
				// If the move is valid, moves in sprite array (checks for push as rogue can push blocks too)
				moveInSpriteArray(super.getPosX(),super.getPosY(),super.getDirection(),interactableSprites);
			} else {
				// Reverses direction if move invalid
				super.setDirection(InteractingSprite.reverseDirection(super.getDirection()));
			}
		}
	}
}