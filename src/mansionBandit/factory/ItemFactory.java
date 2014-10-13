package mansionBandit.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * will create randomised objects on demand for placement in rooms
 * @author Andy
 *
 */
public class ItemFactory {
	private List<ItemTemplate> items;
	private Random random;
	
	public ItemFactory(String itemDefinitions){
		//create the ItemTemplate list, and populate it
		items = new ArrayList<ItemTemplate>();
		InputStream in = this.getClass().getResourceAsStream(itemDefinitions);
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine()){
			items.add(new ItemTemplate(scan.nextLine()));
		}
		scan.close();
		random = new Random();
	}
	
	/**
	 * generates a random item on the given face
	 * 
	 * @param face to add the item to
	 * @return the new item
	 */
	public GameMatter getItem(Face face){
		return items.get(random.nextInt(items.size())).getItem(face);
	}
	
	/**
	 * A smarter version of get Item, this version will try to make sure the new item
	 * does not overlap any doors a given room
	 * 
	 * @param room the room to check for doors
	 * @param face the wall to check and to add the item to
	 * @return
	 */
	public GameMatter getItem(MansionArea room, Face face){
		for (GameMatter item : room.getItems()){
			if (item.getFace() == face && item instanceof Door){
				return items.get(random.nextInt(items.size())).getItem(face, (Door) item);
			}
		}
		return items.get(random.nextInt(items.size())).getItem(face);
	}
}
