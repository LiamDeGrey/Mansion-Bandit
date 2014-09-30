package mansionBandit.gameWorld.matter;

import java.util.ArrayList;
import java.util.List;


/**
 * This class simply creates all the items
 * to be used in the game
 * @author Liam De Grey
 *
 */
public class CreateItems {
	private List<GameMatter> items = new ArrayList<GameMatter>();

	public CreateItems(int numItems){
		populateList(numItems);
	}

	public void populateList(int numItems){
		int rand = (int)(Math.random()*5)+0;
		Face face = Face.getFace(rand);

		for(int i=0; i<numItems; i++){
			items.add(new Key("Key", face, new Dimensions(10, 10)));
		}
	}

	public List<GameMatter> getItems(){
		return items;
	}

}
