package mansionBandit.gameWorld.matter;


/**
 * All Items come under this title
 * @author Liam De Grey
 *
 */
public interface GameMatter {

	/*
	 * returns the face that an item is on
	 */
	public Face getFace();

	/*
	 * returns the position of the item
	 */
	public Dimensions getPosition();

	/*
	 * returns the name of object, to be used for png files
	 */
	public String getName();

}
