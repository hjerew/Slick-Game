

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Sprite {	
	// Attributes of a sprite
	private Image spriteImage;
	private int posX;
	private int posY;
	private String src;
	
	// Constructor creates an Image of the sprite using the source
	public Sprite(String src, int posX, int posY) throws SlickException {
		this.posX = posX;
		this.posY = posY;
		this.src = src;
		spriteImage = new Image(this.src);	
	}
	// Getters/Setters for attributes
	public String getSrc() {
		return src;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setSrc(String string) {
		src=string;
	}
	public void setPosX(int x) {
		posX=x;
	}
	public void setPosY(int y) {
		posY=y;
	}
	
	/** Sprite move method changes coordinates based on a direction case */
	public void move(int direction) {
		switch(direction) {
			//up
			case App.UP:
				this.posY--;
				break;
			//right
			case App.RIGHT:
				this.posX++;
				break;			
			//down
			case App.DOWN:
				this.posY++;
				break;
			//left
			case App.LEFT:
				this.posX--;
				break;			
		}
		
	}
	
	// Takes pixel coordinates of 0 position from world and draws the sprite after converting
	// the world coordinates into pixel coordinates
	public void render(Graphics g, float xZero, float yZero) throws SlickException {
		float x=0,y=0;
		x=xZero+(App.TILE_SIZE*this.posX);
		y=yZero+(App.TILE_SIZE*this.posY);
		spriteImage.draw(x,y);
	}
}

