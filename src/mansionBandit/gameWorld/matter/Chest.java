package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;

import mansionBandit.gameWorld.areas.MansionArea;


/**
 * A chest with treasure in it that will benefit the
 * character
 * @author Liam De Grey
 *
 */
public class Chest extends Openable {
	private Dimensions position;
	private Face face;
	private String name;

	public Chest(String name, Face face, Dimensions position){
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
	public BufferedImage getImage() {
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
