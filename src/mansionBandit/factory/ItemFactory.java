package mansionBandit.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
		items = new ArrayList<ItemTemplate>();
		InputStream in = this.getClass().getResourceAsStream(itemDefinitions + ".txt");
		System.out.println(in.toString());
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine()){
			items.add(new ItemTemplate(scan.nextLine()));
		}
		
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
}
