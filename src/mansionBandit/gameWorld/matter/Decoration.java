package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;

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
	public Dimensions getPosition() {
		return pos;
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
