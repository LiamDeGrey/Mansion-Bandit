package mansionBandit.gameWorld.matter;

import mansionBandit.gameWorld.areas.MansionArea;


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
	public Dimensions getDimensions() {
		return position;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public MansionArea getArea() {
		return null;
	}

	@Override
	public void setFace(Face f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDimensions(Dimensions d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArea(MansionArea r) {
		// TODO Auto-generated method stub

	}


	@Override
	public String getImage() {
		return "key.png";
	}

	@Override
	public boolean useItemOn(Grabable playerItm, GameMatter itm){
		if(itm instanceof Door){
			if(((Door) itm).needsKey().equals(playerItm.getName())){
				((Door) itm).setLocked(false);
			}
		}
		return false;
	}



}
