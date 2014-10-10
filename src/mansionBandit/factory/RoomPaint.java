package mansionBandit.factory;

import java.util.Random;
import java.util.Scanner;

import mansionBandit.gameWorld.areas.Room;

/**
 * a defined set of textures for painting a room
 * 
 * @author Andy
 *
 */
public class RoomPaint {
	private String wall, ceiling, floor;
	
	public RoomPaint(String definition){
		Scanner scan = new Scanner(definition);
		floor = scan.next();
		wall = scan.next();
		ceiling = scan.next();
	}
	
	/**
	 * applies the textures to a room 
	 * 
	 * @param room the room to apply the textures to
	 */
	public void paint(Room room){
		room.setTextures(wall, ceiling, floor);
	}
}