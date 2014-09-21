package mansionBandit.gameWorld.matter;

public class Guard extends Character {
	private Face face;
	private Position pos;

	@Override
	public Face getFace() {
		return face;
	}

	@Override
	public Position getPosition() {
		return pos;
	}

}
