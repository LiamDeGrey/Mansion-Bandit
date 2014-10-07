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
	private String key;
	private String name;

	public Door(String name, boolean locked, String keyNeeded){
		this.name = name;
		this.locked = locked;
		this.key = keyNeeded;
	}


	public boolean isLocked(){
		return locked;
	}

	public void setLocked(boolean locked){
		this.locked = locked;
	}

	public String needsKey(){
		return key;
	}

	@Override
	public Face getFace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFace(Face f) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dimensions getDimensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDimensions(Dimensions d) {
		// TODO Auto-generated method stub

	}

	@Override
	public MansionArea getArea() {
		return null;
	}

	@Override
	public void setArea(MansionArea r) {

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getImage() {
		return "door.png";
	}

}
