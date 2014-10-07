package mansionBandit.gameWorld.matter;

import mansionBandit.gameWorld.areas.MansionArea;

/**
 * A type of game matter that can not be interacted with
 * @author Liam De Grey
 *
 */
public class Decoration implements GameMatter{
	private Face face;
	private Dimensions pos;
	private String name;

	public Decoration(String name, Face face, Dimensions pos){
		this.face = face;
		this.pos = pos;
		this.name = name;
	}

	@Override
	public Face getFace() {
		return face;
	}

	@Override
	public Dimensions getDimensions() {
		return pos;
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
