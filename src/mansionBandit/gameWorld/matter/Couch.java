package mansionBandit.gameWorld.matter;

/**
 * A couch that can be moved when in certain places
 * @author Liam De Grey
 *
 */
public class Couch extends Movable {
	private Position position;
	private Face face;
	
	public Couch(Face face, Position position){
		this.position = position;
		this.face = face;
	}

	@Override
	public Face getFace() {
		return face;
	}

	@Override
	public Position getPosition() {
		return position;
	}

}
