package mansionBandit.gameWorld.matter;

/**
 * All items that can be picked up and put in
 * the inventory
 * @author Liam De Grey
 *
 */
public abstract class Grabable extends GameMatter {

	public Grabable(String name, String description, String image, Face face, Dimensions position){
		super(name, description, image, face, position);
	}

	public boolean useItemOn(Grabable playerItm, GameMatter itm){
		return false;
	}

}
