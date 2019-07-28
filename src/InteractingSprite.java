import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class InteractingSprite extends Sprite{
	// Initialises variables and constructor
	private boolean moving;
	private int direction;
	private boolean canMove=true;
	int [] targetCoords;
	public InteractingSprite(String src, int posX, int posY) throws SlickException {
		super(src,posX,posY);
	}
	
	// Getters and Setters
	public void setTargetCoords(int[] coords, InteractingSprite[][] interactableSprites) {
		this.targetCoords=coords;
	}
	public int[] getTargetCoords() {
		return targetCoords;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction=direction;;
	}
	public boolean getCanMove() {
		return canMove;
	}
	public void setCanMove(boolean canMove) {
		this.canMove=canMove;
	}
	public boolean getMovingStatus() {
		return moving;
	}
	public void setMovingStatus(boolean moving) {
		this.moving=moving;
	}
	
	/** Empty method which is overridden in subclasses */
	public void update(Input input, int delta, InteractingSprite[][] interactableSprites) {
	}
	
	// Static methods
	/** Uses current x/y coords and direction to project the move, returns int array of projected
	 * coordinates */
	public static int[] calcProjMove(int x, int y, int direction) {
		int[] projected = {0,0};
		// Case by case basis of each direction 
		switch (direction) {
		case App.UP:
			y--;
			break;
		case App.RIGHT:
			x++;
			break;
		case App.DOWN:
			y++;
			break;
		case App.LEFT:
			x--;
			break;
		case App.NODIRECTION:
			break;
		}
		projected[0]=x;
		projected[1]=y;
		return projected;
	}
	/** Reverses direction inputted to it */
	public static int reverseDirection(int direction) {
		int reverse=App.NODIRECTION;
		switch(direction) {
		case App.UP:
			reverse=App.DOWN;
			break;
		case App.RIGHT:
			reverse=App.LEFT;
			break;
		case App.DOWN:
			reverse=App.UP;
			break;
		case App.LEFT:
			reverse=App.RIGHT;
			break;
		case App.NODIRECTION:
			break;
		
		}
		return reverse;
	}
	/** Check if a push is being made and if it is a valid move/push */
	public static boolean checkForPush(int x, int y, int direction, 
			InteractingSprite[][] interactableSprites, Input input, int delta) {
		// x and y in this method should be projected coordinates of the move
		boolean canBePushed = true;
		// can move default if next spot is null
		// if there is a wall, can not move
		if(interactableSprites[x][y] != null &&
				interactableSprites[x][y] instanceof Wall) {
			canBePushed=false;
			
		//if tnt/ice/stone, checks if they can be pushed
		} else if(interactableSprites[x][y] != null) { 
			// Projects move of the block
			int[] proj = calcProjMove(x,y,direction);
			int blockProjX=proj[0];
			int blockProjY=proj[1];
			// Checks if block move valid and if it is an interacting sprite which can be pushed
			if(checkMove(x,y,blockProjX, blockProjY, direction, interactableSprites)) {
				if (interactableSprites[x][y] instanceof Tnt) {
					moveInSpriteArray(x,y,direction,interactableSprites);
				} else if(interactableSprites[x][y] instanceof Ice && 
						!(interactableSprites[x][y].getMovingStatus())){
					interactableSprites[x][y].setDirection(direction);
					moveInSpriteArray(x,y,direction,interactableSprites);	
				} else if(interactableSprites[x][y] instanceof Stone) {
					moveInSpriteArray(x,y,direction,interactableSprites);
				} else {
					canBePushed=false;
				}
			} else {
				// If the move isn't valid it can't happen
				canBePushed=false;
			}
		}
		return canBePushed;	
	}
	/** Same as checkMove but for npc*/
	public static boolean npcCheckMove(int x,int y,int direction,InteractingSprite[][] interactableSprites) {
		// Default can move
		boolean canMove=true;
		// Projects coordinates of npc based on its direction
		int[] proj = calcProjMove(x,y,direction);
		int projX=proj[0];
		int projY=proj[1];
		// If the projected coordinate is an interacting sprite it cannot move
		if(interactableSprites[projX][projY] instanceof InteractingSprite  &&
				interactableSprites[projX][projY] != null) {
			canMove=false;
		}
		return canMove;
	}
	/** Checks if a move can be made*/
	public static boolean checkMove(int x, int y, int projX, int projY, int direction,
			InteractingSprite[][] interactableSprites) {
		// Defualt can move
		boolean canMove = true;
		// If the spot isn't null but is a switch, or if the current block is tnt going into a crack, the move
		// can happen
		if(interactableSprites[projX][projY] != null) {
			if ((interactableSprites[projX][projY] instanceof Switch) || 
					((interactableSprites[x][y] instanceof Tnt) && 
				    (interactableSprites[projX][projY] instanceof Crack))){ 
			} else {
				canMove = false;
			}
		} // If the projected spot is null, a move can be made there
		return canMove;
	}
	/** Moves the object in the sprite array as well as updating it's Sprite coords */
	public static void moveInSpriteArray(int x, int y, int direction, 
			InteractingSprite[][] interactableSprites) {
		int xNew=0, yNew = 0;
		switch(direction) {
		//up
		case App.UP:
			yNew=y-1;
			xNew=x;
			break;
		//right
		case App.RIGHT:
			xNew=x+1;
			yNew=y;
			break;			
		//down
		case App.DOWN:
			yNew=y+1;
			xNew=x;
			break;
		//left
		case App.LEFT:
			xNew=x-1;
			yNew=y;
			break;			
		}
		interactableSprites[x][y].move(direction);
		World.replaceObject(x,y,xNew,yNew,interactableSprites);
	}
}
