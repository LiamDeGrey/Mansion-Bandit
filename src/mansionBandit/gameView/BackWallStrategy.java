package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import mansionBandit.gameWorld.areas.Hallway;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * strategy for drawing items on back wall
 * @author Andy
 *
 */
public class BackWallStrategy implements SurfaceStrategy {
	private Surface surface;
	private BufferedImage surfaceTexture;
	private int surfaceX, surfaceY, surfaceWidth, surfaceHeight;
	private RoomView nextRoom = null;
	private static String fog = "/walls/fog.png";
	
	@Override
	public void paintSurface(Graphics g) {
		//draw back rooms first
		if (nextRoom != null){
			nextRoom.paintRoom(g);
		} else {
			//no room to draw so draw fog
			try {
				g.drawImage(ImageIO.read(this.getClass().getResource(fog)), surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//draw this rooms back wall
		g.drawImage(surfaceTexture, surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);
		
		//draw objects on the wall
		for (DrawnObject ob : surface.objects){
			BufferedImage obImage = null;
			try {
				obImage = ImageIO.read(this.getClass().getResource("/object/" + ob.getImage() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(obImage, ob.getBoundX(), ob.getBoundY(), ob.getWidth(), ob.getHeight(), null);
		}
	}

	@Override
	public void setupSurface(Surface surface, Face face) {
		this.surface = surface;
		try {
			//set image for the view
			surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + surface.roomView.room.getWallTexture() + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set bounds for the surface
		surfaceWidth = surface.roomView.width / 2;
		surfaceX = surface.roomView.boundX + (surfaceWidth / 2);
		surfaceHeight = surface.roomView.height / 2;
		surfaceY = surface.roomView.boundY + (surfaceHeight /2);
		
		int depth = surface.roomView.depth;
		
		//here we check to see if we need to draw more rooms in the distance (eg are we in a hallway)
		if (surface.roomView.room instanceof Hallway && depth < surface.roomView.viewDepthMAX){
			MansionArea next = null;
			switch (face){
			case NORTHERN:
				next = surface.roomView.room.getNorth();
				break;
			case WESTERN:
				next = surface.roomView.room.getWest();
				break;
			case SOUTHERN:
				next = surface.roomView.room.getSouth();
				break;
			case EASTERN:
				next = surface.roomView.room.getEast();
			}
			if (next != null && next instanceof Hallway){
				nextRoom = new RoomView(next, face, surfaceX, surfaceY, surfaceWidth, surfaceHeight, depth + 1);
			}
		}
		
		//create object list for surface
		createGameObjects(surface.roomView.room, face);
	}
	
	/**
	 * wraps objects to be drawn into DrawnObjects
	 * with appropriate size distortion
	 * 
	 * @param wall
	 */
	private void createGameObjects(MansionArea room, Face face){
		List<DrawnObject> obs = new ArrayList<DrawnObject>();
		//loop through objects on wall, and resize them
		for (GameMatter item : room.getItems()){
			if (item.getFace() != face){
				continue;
			}
			int scale = (int) ((((double) item.getDimensions().getScale()) / 100) * surfaceHeight);
			int left = (int) (surfaceX + (item.getDimensions().getX() * ((double) surfaceWidth / 100)) - (scale / 2));
			int top = (int) (surfaceY + (item.getDimensions().getY() * ((double) surfaceHeight / 100)) - scale);
			DrawnObject dob = new DrawnObject(item, left, top, scale, scale);
			obs.add(dob);
		}
		surface.objects = obs;
	}

	@Override
	public GameMatter click(int x, int y) {
		for (DrawnObject ob : surface.objects){
			GameMatter foundObject = ob.click(x, y);
			if (foundObject != null){
				return foundObject;
			}
		}
		return null;
	}

}
