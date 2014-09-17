package mansionBandit.gameWorld.items;


/**
 * All Items come under this title
 * @author Liam De Grey
 *
 */
public interface Item {
	
	/*
	 * returns the face that an item is on
	 */
	public Face getFace();
	
	/*
	 * returns the position of the item
	 */
	public Position getPosition();
	
}
