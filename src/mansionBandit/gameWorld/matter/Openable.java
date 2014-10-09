package mansionBandit.gameWorld.matter;

/**
 * This class contains all the objects that are openable
 * @author Liam De Grey
 *
 */
public abstract class Openable extends GameMatter{

	public Openable(String name, String description, String image, Face face, Dimensions position) {
		super(name, description, image, face, position);
	}

}
