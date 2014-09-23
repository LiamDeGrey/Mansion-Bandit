package mansionBandit.gameWorld.matter;

/**
 * A type of game matter that can not be interacted with
 * @author Liam De Grey
 *
 */
public class Decoration implements GameMatter{
	private Face face;
	private Position pos;

	public Decoration(Face face, Position pos){
		this.face = face;
		this.pos = pos;
	}

	@Override
	public Face getFace() {
		return face;
	}

	@Override
	public Position getPosition() {
		return pos;
	}

}
