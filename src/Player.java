import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Sprite {
	//  Player specific variables initialised
	private int direction;
	private int moves=0;
	private boolean dead=false;
	
	//  Constructor
	public Player(int x, int y) throws SlickException {
		super("res/Player_Left.png", x, y);
	}
	
	//  Getters/Setters
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction=direction;
	}
	public int getMoves() {
		return moves;
	}
	public void kill() {
		dead=true;
	}
	public boolean getDead() {
		return dead;
	}
	public void setMoves(int moves) {
		this.moves=moves;
	}
		
	
	public void update(Input input, int delta, InteractingSprite[][] interactableSprites) {
		// Gets direction from static method and sets to player
		int direction=World.getDirection(input);
		setDirection(direction);
		// Projects player's position based on coords and direction
		int[] proj = InteractingSprite.calcProjMove(super.getPosX(),super.getPosY(),direction);
		int playerProjX=proj[0];
		int playerProjY=proj[1];
		
		
		InteractingSprite projSpace = interactableSprites[playerProjX][playerProjY];
		// If a keyboard input is made, direction is anything but App.NODIRECTION
		if(direction != App.NODIRECTION){
			// Checks if a push is occuring in projected coordinates
			if(InteractingSprite.checkForPush(playerProjX,playerProjY,
							direction,interactableSprites,input,delta)) {
				// moves the player if push/move to projected coords satisfied and increases number of moves
				super.move(direction);
				this.moves++;
			}
			// If a player has walked into an npc, they are dead
			if((projSpace instanceof Mage) || (projSpace instanceof Rogue)
				|| (projSpace instanceof Skeleton)){
			dead=true;
			}
		}
	} 
}
