package mansionBandit.gameWorld.matter;

/**
 * Guard is an enemy of the bandit and can decrease the
 * bandits health bar
 * @author Liam De Grey
 *
 */
public class Guard extends Character {

	public Guard(String name, Face face, Dimensions position) {
		super(name, null, null, face, position);
	}

	@Override
	public String getImage(){
		return "guard";
	}

	@Override
	public String getDescription(){
		return "Oh no! It's a guard!";
	}


}
