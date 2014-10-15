package mansionBandit.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
		List<ItemTemplate> readTemplates = new ArrayList<ItemTemplate>();
		InputStream in = this.getClass().getResourceAsStream(itemDefinitions);
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine()){
			readTemplates.add(new ItemTemplate(scan.nextLine()));
		}
		scan.close();
		random = new Random();
		
		items = new ArrayList<ItemTemplate>();
		
		//check items for validity
		for (ItemTemplate template : readTemplates){
			if (template.getItem(Face.NORTHERN) != null){
				//item isnt null, so it is valid
				items.add(template);
			}
		}
	}

	/**
	 * generates a random item on the given face
	 *
	 * @param face to add the item to
	 * @return the new item
	 */
	public GameMatter getItem(Face face){
		if (!items.isEmpty()){
			return items.get(random.nextInt(items.size())).getItem(face);
		}
		return null;
	}
}
