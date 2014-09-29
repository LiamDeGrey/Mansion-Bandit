package mansionBandit.gameWorld.matter;


/**
 * A Bandit is any person that has entered the game to play
 * as a thief
 * @author Liam De Grey
 *
 */
public class Bandit extends Character{
	private Face face;
	private Dimensions pos;
	private String name;

	public Bandit() {

	}

	public void setFace(Face f) {
		face = f;
	}

	@Override
	public Face getFace() {
		return face;
	}

	public void setPos(Dimensions p) {
		pos = p;
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
