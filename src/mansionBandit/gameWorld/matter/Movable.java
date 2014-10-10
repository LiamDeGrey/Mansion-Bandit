package mansionBandit.gameWorld.matter;


/**
 * All the items that can be moved, e.g furniture
 * @author Liam De Grey
 *
 */
public abstract class Movable extends GameMatter{

	public Movable(String name, String description, String image, Face face, Dimensions position) {
		super(name, description, image, face, position);
	}

}
