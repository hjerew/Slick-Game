import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tests.SpriteSheetFontTest;

import java.io.FileNotFoundException;

public class Loader {
	
	/**
	 * Loads the sprites from a given file.
	 * @param filename
	 * @return spriteArray
	 * @throws SlickException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */

		// Creates objects for sprites, player and world and returns world to App
	
 	public static World load(String filename) throws SlickException {  
	    
		//  Initialisation of variables
	    int mapWidth;
	    int mapHeight;
	    int playerX=0;
	    int playerY=0;
	    
	    
	    // Reads CSV file line by line  
	    
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			//  First line contains map width and height
			//  line string is split into string array coords
			
    		String line = br.readLine();
    		String coords[] = line.split(App.CSVSEPARATOR);
    		mapWidth = Integer.parseInt(coords[0]);
    		mapHeight = Integer.parseInt(coords[1]);
    		
    		//  Initialisation of the Sprite arrays using map dimensions
    		Sprite[][] unchangedSprites = new Sprite[mapWidth][mapHeight];
			InteractingSprite[][] interactableSprites = new InteractingSprite[mapWidth][mapHeight];
    		
	    	while ((line = br.readLine()) != null) {
	    		
	    		//  String array words contains the split line that has been read
	    		String words[] = line.split(App.CSVSEPARATOR);
	    		
	    		// words[0], words[1], words[2] used because of format: type,tile_x,tile_y
	    		int xPos = Integer.parseInt(words[1]); 
	    		int yPos = Integer.parseInt(words[2]);
	    		
	    		//  Deals with the type, constructs corresponding Sprite type within the corresponding Array
	    		switch (words[0]) {
	    			case "floor":
	    				unchangedSprites[xPos][yPos] = new Floor(xPos,yPos);
	    				break;
	    			case "target":
	    				unchangedSprites[xPos][yPos]= new Target(xPos,yPos);
	    				break;
	    			case "stone":
	    				interactableSprites[xPos][yPos] = new Stone(xPos,yPos);
	    				break;
	    			case "wall":
	    				interactableSprites[xPos][yPos] = new Wall(xPos,yPos);
	    				break;
	    			case "ice":
	    				interactableSprites[xPos][yPos]= new Ice(xPos,yPos);
	    				break;
	    			case "tnt":
	    				interactableSprites[xPos][yPos]= new Tnt(xPos,yPos);
	    				break;
	    			case "door":
	    				interactableSprites[xPos][yPos]= new Door(xPos,yPos);
	    				break;
	    			case "mage":
	    				interactableSprites[xPos][yPos]= new Mage(xPos,yPos);
	    				break;
	    			case "cracked":
	    				interactableSprites[xPos][yPos]= new Crack(xPos,yPos);
	    				break;
	    			case "rogue":
	    				interactableSprites[xPos][yPos]= new Rogue(xPos,yPos);
	    				break;
	    			case "skeleton":
	    				interactableSprites[xPos][yPos]= new Skeleton(xPos,yPos);
	    				break;
	    			case "switch":
	    				interactableSprites[xPos][yPos]= new Switch(xPos,yPos);
	    				break;
	    			// Player doesn't have a break; because it's not in the Arrays
	    			case "player":
	    				playerX=xPos;
	    				playerY=yPos;	
	    		}
	    		
	    	
	    	}
	    	
	    	
	    	// Constructs world through values read above
	    	World world = new World(mapWidth,mapHeight,unchangedSprites, interactableSprites);
	    	// Initialises the player within the new World
	    	world.setPlayer(playerX, playerY);
	    	
	    	// Returns world containing initialised sprite array and player object
	    	return world;
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
