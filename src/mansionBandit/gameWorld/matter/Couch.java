package mansionBandit.gameWorld.matter;

import mansionBandit.gameWorld.areas.MansionArea;

/**
 * A couch that can be moved when in certain places
 * @author Liam De Grey
 *
 */
public class Couch extends Movable {
	private Dimensions position;
	private Face face;
	private String name;

	public Couch(String name, Face face, Dimensions position){
		this.position = position;
		this.face = face;
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
	public String getImage() {
		return null;
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

}
