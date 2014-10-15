package mansionBandit.factory;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.areas.StartSpace;

/**
 * a defined set of textures for painting a room
 * 
 * @author Andy
 *
 */
public class RoomPaint {
	private String wall, ceiling, floor;
	private boolean corruptedPaint = false;
	
	public RoomPaint(String definition){
		Scanner scan = new Scanner(definition);
		try{
			floor = scan.next();
			wall = scan.next();
			ceiling = scan.next();
		} catch (NoSuchElementException e){
			//bad scan, reached end of line before all parameters parsed
			corruptedPaint = true;
			return;
		}
	}
	
	/**
	 * applies the textures to a room 
	 * 
	 * @param room the room to apply the textures to
	 */
	public void paint(MansionArea room){
		if (corruptedPaint){
			//bad paint definition, hope to salvage situation by applying floor texture
			//wont make a difference if floor1.png doesn't exist however
			room.setTextures("floor1", "floor1", "floor1");
		}
		room.setTextures(wall, ceiling, floor);
	}
}
