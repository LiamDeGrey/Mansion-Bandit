package mansionBandit.gameWorld.matter;


/**
 * A chest with treasure in it that will benefit the 
 * character
 * @author Liam De Grey
 *
 */
public class Chest extends Openable {
	private Position position;
	private Face face;
	
	public Chest(Face face, Position position){
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
