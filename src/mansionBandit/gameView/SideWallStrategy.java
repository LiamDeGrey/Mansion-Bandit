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

public class SideWallStrategy implements SurfaceStrategy {
	private boolean left;
	private Surface surface;
	private RoomView sideRoom = null;
	private BufferedImage surfaceTexture;
	private int surfaceX, surfaceY, surfaceWidth, surfaceHeight;
	
	public SideWallStrategy(boolean left) {
		this.left = left;
	}

	@Override
	public void paintSurface(Graphics g) {
		
		if (sideRoom != null){
			sideRoom.paintRoom(g);
		}

		g.drawImage(surfaceTexture, surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);

		//draw objects on the wall
		for (DrawnObject ob : surface.objects){
			BufferedImage obImage = null;
			try {
				if (left){
					obImage = ImageIO.read(this.getClass().getResource("/object/" + ob.getImage() + "L.png"));
					javaxt.io.Image skewedImage = new javaxt.io.Image(obImage);
				}else {
					obImage = ImageIO.read(this.getClass().getResource("/object/" + ob.getImage() + "R.png"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(obImage, ob.getBoundX(), ob.getBoundY(), ob.getWidth(), ob.getHeight(), null);
		}
	}

	@Override
	public GameMatter click(int x, int y) {
		//TODO implement clicking on side walls??
		return null;
	}

	@Override
	public void setupSurface(Surface surface, Face face) {
		this.surface = surface;
		try {
			//set image for the view
			if (left){
				surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + surface.roomView.room.getWallTexture() + "L.png"));
			} else {
				surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + surface.roomView.room.getWallTexture() + "R.png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set bounds for the surface
		surfaceWidth = surface.roomView.width / 4;
		surfaceHeight = surface.roomView.height;
		surfaceY = surface.roomView.boundY;
		if (left){
			surfaceX = surface.roomView.boundX;
		} else {
			surfaceX = surface.roomView.boundX + ((surface.roomView.width * 3) / 4);
		}

		//here we check to see if we need to draw more rooms in the distance (eg are we in a hallway)
		if (surface.roomView.room instanceof Hallway){

			MansionArea nextRoom = null;
			switch (face){
			case NORTHERN:
				nextRoom = surface.roomView.room.getNorth();
				break;
			case WESTERN:
				nextRoom = surface.roomView.room.getWest();
				break;
			case SOUTHERN:
				nextRoom = surface.roomView.room.getSouth();
				break;
			case EASTERN:
				nextRoom = surface.roomView.room.getEast();
			}

			if (nextRoom != null && nextRoom instanceof Hallway){
				
				sideRoom = new RoomView(nextRoom, surface.roomView.playerDirection, surface.roomView.boundX, surface.roomView.boundY, surface.roomView.width, surface.roomView.height, left);

			} else if (nextRoom != null && nextRoom instanceof Room){
				try {
					if (left){
						surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + nextRoom.getWallTexture() + "L.png"));
					} else {
						surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + nextRoom.getWallTexture() + "R.png"));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//create object list for surface
		createGameObjects(surface.roomView.room, face);
	}

		/**
		 * wraps objects to be drawn into DrawnObjects
	 * with appropriate size and position distortion
	 * 
	 * @param wall
	 */
	private void createGameObjects(MansionArea room, Face face){
		List<DrawnObject> obs = new ArrayList<DrawnObject>();
		
		//loop through objects on floor, and resize them
		for (GameMatter item : room.getItems()){
			if (item.getFace() != face){
				continue;
			}
			//determine x and y based on direction facing in room
			int x = item.getDimensions().getX();
			int y = item.getDimensions().getY();
			
			//get base height of object
			int size = (int) ((((double) item.getDimensions().getScale()) / 100) * surfaceHeight);
			
			/* determine width and height based on distance away from viewer perspective
			 * this causes items that are further away to appear smaller
			 * (variable scale is the level of scaling to apply as a double between 0.5 and 1) */
			double scale = 0.5 + (0.5 * (((double) x) / 100));
			if (this.left){
				//invert scale if this object is on the left
				scale = 0.5 + (1 - scale);
			}
			//apply scale
			size = (int) (size * scale);
						
			//determine where the vertical center of the image should be
			int objectCenterY = (int) (surfaceY + (y * ((double) surfaceHeight / 100)));
			//have to alter vertical position to be closer to the center when further back
			int surfaceCenterY = surfaceY + (surfaceHeight / 2);
			int diff = Math.abs(surfaceCenterY - objectCenterY);
			//apply scaling to the diff
			diff = (int) (diff * scale);
			
			//apply the new y position, and account for having to draw from top left corner
			if (objectCenterY < surfaceCenterY){
				objectCenterY = surfaceCenterY - diff - size;
			} else if (surfaceCenterY < objectCenterY){
				objectCenterY = surfaceCenterY + diff - size;
			}
			
			//TODO change variable names so that we're not relying on scope?
			int left = (int) (surfaceX + (x * ((double) surfaceWidth / 100))) - (size / 4);
			
			//create the wrapped object and add to list
			DrawnObject dob = new DrawnObject(item, left, objectCenterY, size / 2, size);
			obs.add(dob);
		}
		surface.objects = obs;
	}
}
