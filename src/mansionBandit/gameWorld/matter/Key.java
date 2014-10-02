package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;


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
	private BufferedImage img;

	public Key(String name, Face face, Dimensions position){
		this.position = position;
		this.face = face;
		this.name = name;
		assignImage();
	}

	private void assignImage(){
		try {
			img = ImageIO.read(this.getClass().getResource("/GameMatter/key.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public BufferedImage getImage(){
		return img;
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
