package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;

/**
 * Guard is an enemy of the bandit and can decrease the
 * bandits health bar
 * @author Liam De Grey
 *
 */
public class Guard extends Character {
	private Face face;
	private Dimensions pos;
	private String name;

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
