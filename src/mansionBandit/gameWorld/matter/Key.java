package mansionBandit.gameWorld.matter;


/**
 * A key that can be put in inventory, needs to be in
 * inventory in order to unlock certain doors
 * @author Liam De Grey
 *
 */
public class Key extends Grabable{
	private Dimensions position;
	private Face face;
	private String name;

	public Key(String name, Face face, Dimensions position){
		this.position = position;
		this.face = face;
		this.name = name;
	}

	@Override
	public Face getFace() {
		return face;
	}

	@Override
	public Dimensions getPosition() {
		return position;
	}

	@Override
	public String getName() {
		return name;
	}



}
