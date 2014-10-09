package mansionBandit.gameWorld.matter;

import mansionBandit.gameWorld.areas.MansionArea;

/**
 * Door is the link between MansionAreas,
 * Looked at as a GameMatter(Just another object)
 * @author Liam De Grey
 *
 */
public class Door extends Openable{
	private boolean locked;

	public Door(String name, Face face, Dimensions position, boolean locked){
		super(name, null, null, face, position);
		this.locked = locked;
	}

	public boolean isLocked(){
		return locked;
	}

	public void unlock(){
		this.locked = false;
	}

	@Override
	public String getImage() {
		return "door";
	}

	@Override
	public String getDescription(){
		return "this is a door";
	}

}
