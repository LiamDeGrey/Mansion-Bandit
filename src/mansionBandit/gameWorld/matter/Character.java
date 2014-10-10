package mansionBandit.gameWorld.matter;

import mansionBandit.gameWorld.areas.MansionArea;


/**
 * A character can be a thief or guard(AI) and implements
 * GameMatter
 * @author Liam De Grey
 *
 */
public abstract class Character extends GameMatter{

	public Character(String name, String description, String image, Face face, Dimensions position) {
		super(name, description, image, face, position);
	}

	/**
	 * Kills the guard or the bandit
	 */
	public void kill(){
		return;
	}

}
