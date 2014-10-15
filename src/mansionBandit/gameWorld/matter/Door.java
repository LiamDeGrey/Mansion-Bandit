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
	private String keyNeeded = "Key2";

	public Door(String name, Face face, Dimensions position, boolean locked){
		super(name, null, null, face, position);
		this.locked = locked;
		chooseKey();
	}

	public void chooseKey(){
		if(locked){
			int rand = (int)(Math.random()*2);
			if(rand==0){
				keyNeeded = "Key1";
			}
		}
	}

	public boolean isLocked(){
		return locked;
	}

	public void unlock(){
		this.locked = false;
	}

	public String getKeyNeeded(){
		return keyNeeded;
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
