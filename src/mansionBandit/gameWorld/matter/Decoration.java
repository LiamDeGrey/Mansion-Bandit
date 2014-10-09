package mansionBandit.gameWorld.matter;

/**
 * A type of game matter that can not be interacted with
 * @author Liam De Grey
 *
 */
public abstract class Decoration extends GameMatter{

	public Decoration(String name, String description, String image, Face face, Dimensions position) {
		super(name, description, image, face, position);
	}

}
