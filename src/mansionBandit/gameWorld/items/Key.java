package mansionBandit.gameWorld.items;


/**
 * A key that can be put in inventory, needs to be in
 * inventory in order to unlock certain doors
 * @author Liam De Grey
 *
 */
public class Key extends Grabable{
	private Position position;
	private Face face;
	
	public Key(Face face, Position position){
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
