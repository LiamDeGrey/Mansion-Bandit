package mansionBandit.gameWorld.matter;

/**
 * Door is the link between MansionAreas,
 * Looked at as a GameMatter(Just another object)
 * @author Liam De Grey
 *
 */
public class Door extends Openable{
	private boolean locked;
	private String keyNeeded = "Key2";

	public Door(String name, Face face, boolean locked){
		super(name, null, null, face, null);
		this.locked = locked;
		chooseKey();
	}


	private void chooseKey(){
		if(locked){
			int rand = (int)(Math.random()*2);
			if(rand==0){
				setKeyNeeded("Key1");
			}
		}
	}

	/**
	 * returns true if door is locked
	 * @return
	 */
	public boolean isLocked(){
		return locked;
	}

	/**
	 * unlocks the door
	 */
	public void unlock(){
		this.locked = false;
	}

	public void setKeyNeeded(String key){
		keyNeeded = key;
	}

	/**
	 * returns the string of the key needed to unlock the door
	 * @return
	 */
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

	@Override
	public Dimensions getDimensions(){
		return new Dimensions(50, 100, 70);
	}

}
