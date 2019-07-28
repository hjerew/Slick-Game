import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Stone extends InteractingSprite{
	public Stone(int x, int y) throws SlickException {
		super(App.STONE_LOCATION, x, y);
	}
}
