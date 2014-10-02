package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;

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
