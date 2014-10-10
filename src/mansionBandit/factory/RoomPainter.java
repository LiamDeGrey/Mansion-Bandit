package mansionBandit.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

public class RoomPainter {
	private List<RoomPaint> paints;
	private Random random;

	public RoomPainter(String paintDefinitions){
		paints = new ArrayList<RoomPaint>();
		InputStream in = this.getClass().getResourceAsStream(paintDefinitions);
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine()){
			paints.add(new RoomPaint(scan.nextLine()));
		}
		
		random = new Random();
	}
	
	/**
	 * generates a random item on the given face
	 * 
	 * @param face to add the item to
	 * @return the new item
	 */
	public void paintRoom(Room room){
		paints.get(random.nextInt(paints.size())).paint(room);
	}
}
