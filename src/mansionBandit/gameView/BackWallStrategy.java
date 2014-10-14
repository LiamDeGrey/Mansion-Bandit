package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import mansionBandit.gameWorld.areas.Hallway;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * strategy for drawing items on back wall
 * @author Andy
 *
 */
public class BackWallStrategy implements SurfaceStrategy {
	private Surface surface;
	private Image surfaceTexture;
	private int surfaceX, surfaceY, surfaceWidth, surfaceHeight;
	private RoomView nextRoom = null;
	private static String fog = "/texture/fog.png";
	
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
		
		g.drawImage(surfaceTexture, surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);

		//draw objects on the wall
		for (DrawnObject ob : surface.objects){
			g.drawImage(ob.getImage(), ob.getBoundX(), ob.getBoundY(), ob.getWidth(), ob.getHeight(), null);
		}
	}

	/**
	 * generates the bounds of the surface
	 * has to check if the roomView is for a side passage
	 */
	private void getBounds(){
		surfaceWidth = surface.roomView.width / 2;

		if (surface.roomView.sidePassage){
			//is a sidepassage
			if (surface.roomView.sidePassageLeft){
				//is on left
				surfaceX = surface.roomView.boundX + surfaceWidth + (surfaceWidth / 2);
			} else {
				//is on right
				surfaceX = surface.roomView.boundX - (surfaceWidth / 2);
			}
		} else {
			//not a side passage
			surfaceX = surface.roomView.boundX + (surfaceWidth / 2);
		}

		surfaceHeight = surface.roomView.height / 2;
		surfaceY = surface.roomView.boundY + (surfaceHeight /2);
	}

	@Override
	public void setupSurface(Surface surface, Face direction) {
		this.surface = surface;
		try {
			//set image for the view
			surfaceTexture = ImageIO.read(this.getClass().getResource("/texture/" + surface.roomView.room.getWallTexture() + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getBounds();
		
		int depth = surface.roomView.depth;

		//here we check to see if we need to draw more rooms in the distance (eg are we in a hallway)
		if (surface.roomView.room instanceof Hallway){
			//ok now find out what the next room we have to draw is
			MansionArea next = null;
			switch (direction){
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
				break;
			}

			if (next != null && next instanceof Hallway && depth <= surface.roomView.viewDepthMAX){
				//next room is a hallway and within range of the main view,
				//so setup that room to be drawn behind this one
				
				if (next != null && surface.roomView.sidePassage){
					/* except that we are a side passage
					 * (if the room is drawn behind the back wall here
					 * it will look out of place as the perspective 
					 * for a back wall is not configured to be drawn at this
					 * angle
					 */
					//TODO fix perspective in back wall for this??
					nextRoom = null;
				} else {
					nextRoom = new RoomView(next, direction, surfaceX, surfaceY, surfaceWidth, surfaceHeight, depth + 1);
				}
				
			} else {
				//next room is a room, so use hallway Wall texture for use in the hallway
				try {
					surfaceTexture = ImageIO.read(this.getClass().getResource("/texture/hallwayW.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//create object list for surface
		createGameObjects(surface.roomView.room, direction);
	}
	
	/**
	 * wraps objects to be drawn into DrawnObjects
	 * with appropriate size distortion
	 * 
	 * @param wall
	 */
	
	/**
	 * gets the objects in the room, filters them for this wall
	 * only, and wraps them into a drawn object, complete with
	 * resized bounds
	 * 
	 * @param room the room this surface belongs to
	 * @param direction the direction this surface is on
	 */
	private void createGameObjects(MansionArea room, Face direction){
		List<DrawnObject> obs = new ArrayList<DrawnObject>();
		
		//loop through objects on wall, and resize them
		for (GameMatter item : room.getItems()){
			if (item instanceof Bandit){
				//bandits dont get drawn on walls
				continue;
			}
			
			if (item.getFace() != direction){
				//item not on this wall, move on
				continue;
			}
			
			BufferedImage image = null;
			try {
				image = ImageIO.read(this.getClass().getResource("/object/" + item.getImage() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//create the DrawnObject with the computed bounds
			DrawnObject dob = new DrawnObject(item, image, surfaceX, surfaceY, surfaceWidth, surfaceHeight);
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
