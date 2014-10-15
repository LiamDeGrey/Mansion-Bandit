package mansionBandit.gameWorld.matter;

/**
 * Kinfe can be used to attack other bandits, or Guards
 * 
 * @author Liam De Grey
 *
 */
public class Knife extends Grabable{

	public Knife(String name, String description, String image, Face face, Dimensions position) {
		super(name, description, image, face, position);
	}

	/**
	 * Checks to see if the knife is being used on a
	 * guard or bandit and then kills them
	 */
	@Override
	public boolean useItemOn(GameMatter itm){
		if(itm instanceof Character){
			((Character) itm).kill();
			return true;
		}
		return false;
	}

}
