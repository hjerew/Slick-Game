import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Mage extends InteractingSprite {
	// Inititalises variables for mage and constructor
	int[] prevTarget;
	boolean moveNextGo = true;
	public Mage(int x, int y) throws SlickException {
		super(App.MAGE_LOCATION, x, y);
	}
	
	/** Same as sgn function in specification*/ 
	public int sgn(int x) {
		if(x>0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	/** Sets the target coordinates for a mage and works out which direction to move */
	public void setTargetCoords(int[] coords, InteractingSprite[][] interactableSprites) {
		// Sets previous target to see if player has moved 
		if(super.getTargetCoords() != null) {
			this.prevTarget = super.getTargetCoords();
		} else {
			this.prevTarget=coords;
		}
		
		// Sets current target
		super.setTargetCoords(coords,interactableSprites);
		
		// Calculates X and Y distance from target
		int targetX=super.getTargetCoords()[0];
		int targetY=super.getTargetCoords()[1];
		int distX=targetX-super.getPosX();
		int distY=targetY-super.getPosY();
		
		// Moves in whichever distance is furthers away
		if (Math.abs(distX) > Math.abs(distY)) {
			// Works out first which direction to move, checks if that move is possible and if it isn't,
			// tries to move in the other direction (towards the player), done on a case by case basis depending
			// on the x and y distance of the mage from the player
			if(sgn(distX)>0) {
				super.setDirection(App.RIGHT);
				if(!(npcCheckMove(super.getPosX(),super.getPosY(),App.RIGHT,interactableSprites))) {
					if (sgn(distY)>0) {
						super.setDirection(App.DOWN);
					} else {
						super.setDirection(App.UP);
					}
				}
			} else {
				super.setDirection(App.LEFT);
				if(!(npcCheckMove(super.getPosX(),super.getPosY(),App.LEFT,interactableSprites))) {
					if (sgn(distY)>0) {
						super.setDirection(App.DOWN);
					} else {
						super.setDirection(App.UP);
					}
				}
			}
		} else if (sgn(distY)>0) {
			super.setDirection(App.DOWN);
			if(!(npcCheckMove(super.getPosX(),super.getPosY(),App.DOWN,interactableSprites))) {
				if (sgn(distX)>0) {
					super.setDirection(App.RIGHT);
				} else {
					super.setDirection(App.LEFT);
				}
			}
		} else {
			super.setDirection(App.UP);
			if(!(npcCheckMove(super.getPosX(),super.getPosY(),App.UP,interactableSprites))) {
				if (sgn(distX)>0) {
					super.setDirection(App.RIGHT);
				} else {
					super.setDirection(App.LEFT);
				}
			}
		}	
	}

	
	public void update(Input input, int delta, InteractingSprite[][] interactableSprites) {
		// Compares previous and current targets to see if player moved
		if((prevTarget[0] != super.getTargetCoords()[0]) || (prevTarget[1] != super.getTargetCoords()[1]) ) {
			if(moveNextGo) { // mage moves on alternate turns as it's impossible to complete level 
							 // if it moves every turn (Because mage updates after player, the initial algorithm
							 // is a bit OP really
				if(npcCheckMove(super.getPosX(),super.getPosY(),super.getDirection(),interactableSprites)) {
					// Moves mage in sprite array if the move is possible and doesn't allow them to move next go
					moveInSpriteArray(super.getPosX(),super.getPosY(), super.getDirection(), interactableSprites);
					this.moveNextGo = false;
				}
			} else {
				// Once one go is skipped, this boolean becomes true to ensure alternate goes are skipped
				this.moveNextGo=true;
			}
		}
		
	}
}