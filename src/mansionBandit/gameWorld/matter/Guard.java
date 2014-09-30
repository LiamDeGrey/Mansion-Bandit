package mansionBandit.gameWorld.matter;

/**
 * Guard is an enemy of the bandit and can decrease the
 * bandits health bar
 * @author Liam De Grey
 *
 */
public class Guard extends Character {
	private Face face;
	private Dimensions pos;
	private String name;

	@Override
	public Face getFace() {
		return face;
	}

	@Override
	public Dimensions getPosition() {
		return pos;
	}

	@Override
	public String getName() {
		return name;
	}

}
