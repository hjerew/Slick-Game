/**
 * Project skeleton for SWEN20003: Object Oriented Software Development 2017
 * by Eleanor McMurtry
 */


import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 */
public class App extends BasicGame
{
 	// screen width, in pixels 
    public static final int SCREEN_WIDTH = 800;
    // screen height, in pixels 
    public static final int SCREEN_HEIGHT = 600;
    // size of the tiles, in pixels 
    public static final int TILE_SIZE = 32;
    // location of level files (split into two strings with level in between)
    public static final String LEVELSTRING1 = "res/levels/";
    public static final String LEVELSTRING2 = ".lvl";
    // location of sprite images
	public static final String WALL_LOCATION = "res/wall.png";
	public static final String FLOOR_LOCATION = "res/floor.png";
	public static final String STONE_LOCATION = "res/stone.png";
	public static final String TARGET_LOCATION = "res/target.png";
	public static final String PLAYER_LOCATION = "res/player_left.png";
	public static final String CRACK_LOCATION = "res/cracked_wall.png";
	public static final String DOOR_LOCATION = "res/door.png";
	public static final String EXPLOSION_LOCATION = "res/explosion.png";
	public static final String ICE_LOCATION = "res/ice.png";
	public static final String MAGE_LOCATION = "res/mage.png";
	public static final String ROGUE_LOCATION = "res/rogue.png";
	public static final String SKULL_LOCATION = "res/skull.png";
	public static final String SWITCH_LOCATION = "res/switch.png";
	public static final String TNT_LOCATION = "res/tnt.png";
    // comma used to separate values in CSV files 
    public static final String CSVSEPARATOR = ",";
    // player label in csv file 
    public static final String PLAYERCSV = "player";
    // max number of sprites you can load into the array 
	public static final int MAXSPRITEARRAYSIZE = 500;
	// number of directions a player can move 
	// directions
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int NODIRECTION = 4;
	// used to half floats so no magic numbers
    public static final int HALFDIVISOR = 2;
    // max number of worlds
    public static final int MAXWORLDS = 5;
    // npc auto-move timers
	public static final int ICETIMER = 250;
	public static final int SKELETONTIMER = 1000;
	public static final int EXPLOSIONTIMER = 400;
    
	// initialises world array using max number of worlds
    World[] world = new World[MAXWORLDS];
    // initial level is 0, with 0 moves made
    int level=0,moves=0;
    // initialising ArrayList of backup worlds from previous moves
	private ArrayList<World> previousMoves= new ArrayList<World>();
    
    
    public App()
    {    	
        super("Shadow Blocks");
    }

    @Override               // ----------------- INITIALISATION ----------------- // 
    public void init(GameContainer gc)
    throws SlickException
    {	
    	// Clears previous moves made for a new level
    	previousMoves.clear();
    	moves=0;
    	
    	// Uses the integer level value with the level strings to put together a string
    	// describing the location of the next level (assuming .lvl convention used)
    	String levelLocation;
    	levelLocation = LEVELSTRING1 + level + LEVELSTRING2;
    	
    	// Loads the new level and resets moves within to 0
    	world[level] = Loader.load(levelLocation);
    	world[level].setMoves(moves);
    	
    }
    
    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        // Calls the update method of the current level.
        world[level].update(input, delta);
        
        // Adds current level to the previousMoves ArrayList if there are less elements in the List than moves
        if(moves<world[level].getMoves()) {
        	this.previousMoves.add(world[level]);
        	moves=world[level].getMoves();
        }
        
        // Restarts level if key R is pressed
        if (input.isKeyPressed(Input.KEY_R)) {
        	init(gc);
        }
        
        // Checks if level is passed using a world method (doesn't work if this is the final level so that
        // game doesn't crash. Also allows skipping of the level if N pressed and prints the level completed
        // as well as the number of moves.
        if((world[level].isLevelPassed() || (input.isKeyPressed(Input.KEY_N))) && (level<MAXWORLDS-1)) {
        	System.out.println("Completed level " + level + " in " + moves + " moves.");
        	level++;
        	init(gc);
        }
        // Quits game if esc pressed
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
        // Gonna save you a bit of time here, undoing moves is NOT WORKING
        // Ran out of time so couldn't implement a deep copy, it does however store previous
        // worlds each time a move is made but that's as far as this goes.
        if (input.isKeyPressed(Input.KEY_U)) {
        	if(moves > 0) {
        		world[level]=null;
        		world[level]=undoMove(moves); 		
		
        	}
        }
        // Checks if player within World is dead
        if(world[level].isPlayerDead()) {
        	init(gc);
        }
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
    	world[level].render(g);
    	g.drawString("moves: " + moves, 680, 50);
    }
    
    /** Not working because a deep copy is required */
 	public World undoMove(int moves){
 		World prevWorld = this.previousMoves.get(moves-2);
 		return prevWorld;
 	}

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new App());
        // setShowFPS(true), to show frames-per-second.
        app.setShowFPS(true);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }

}