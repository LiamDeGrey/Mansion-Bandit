package mansionBandit.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.areas.StartSpace;

/**
 * The RoomPainter will apply wall, floor, and ceiling textures to a room
 * 
 * reads a list of defined paint templates, and chooses one at random for each room
 * 
 * @author Andy
 *
 */
public class RoomPainter {
	private List<RoomPaint> paints;
	private Random random;

	public RoomPainter(String paintDefinitions){
		paints = new ArrayList<RoomPaint>();
		//load paint definition file, and pass each line to a new RoomPaint
		InputStream in = this.getClass().getResourceAsStream(paintDefinitions);
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine()){
			paints.add(new RoomPaint(scan.nextLine()));
		}
		scan.close();
		random = new Random();
	}
	
	/**
	 * applies a textures to the given room
	 * @param room the room to apply textures to
	 */
	public void paintRoom(MansionArea room){
		paints.get(random.nextInt(paints.size())).paint(room);
	}
}
