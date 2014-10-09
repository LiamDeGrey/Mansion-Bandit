package mansionBandit.gameWorld.matter;


/**
 * A chest with treasure in it that will benefit the
 * character
 * @author Liam De Grey
 *
 */
public class Chest extends Openable {

	public Chest(String name, Face face, Dimensions position){
		super(name, null, null, face, position);
	}

	@Override
	public String getImage(){
		return "chest";
	}

	@Override
	public String getDescription(){
		return "It's a chest!";
	}

}
