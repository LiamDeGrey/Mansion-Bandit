package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;


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
	public Dimensions getPosition() {
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

}
