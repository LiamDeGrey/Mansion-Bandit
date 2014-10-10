package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

public class TopBottomStrategy implements SurfaceStrategy {
	private boolean ceiling;
	private Surface surface;
	private Image surfaceTexture, shadow;
	private int surfaceX, surfaceY, surfaceWidth, surfaceHeight;

	/**
	 * create strategy for top or bottom
	 * @param top true if surface is a ceiling
	 */
	public TopBottomStrategy(boolean top) {
		this.ceiling = top;
	}

	@Override
	public void paintSurface(Graphics g) {
		g.drawImage(surfaceTexture, surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);

		if (!ceiling){
			//draw shadows on floor first to prevent overlapping shadows on objects
			for (DrawnObject ob : surface.objects){
				g.drawImage(shadow, ob.getBoundX() -10, ob.getBoundY() + ob.getHeight() - 10, ob.getWidth() + 20, 20, null);
			}
		}

		//draw objects on the wall
		for (DrawnObject ob : surface.objects){
			//draw object
			g.drawImage(ob.getImage(), ob.getBoundX(), ob.getBoundY(), ob.getWidth(), ob.getHeight(), null);
		}
	}

	@Override
	public GameMatter click(int x, int y) {
		if (ceiling){
			return null;
		}
		List<DrawnObject> objects = new ArrayList<DrawnObject>();
		objects.addAll(surface.objects);
		Collections.reverse(objects);
		for (DrawnObject ob : objects){
			GameMatter foundObject = ob.click(x, y);
			if (foundObject != null){
				return foundObject;
			}
		}
		return null;
	}

	@Override
	public void setupSurface(Surface surface, Face direction) {
		
		//set bounds for the surface
		surfaceX = surface.roomView.boundX;
		surfaceWidth = surface.roomView.width;
		
		if (surface.roomView.sidePassage){
			/* 
			 * override for sidepassages.
			 * when using the skew feature of javaxt, we must only use coordinates
			 * within the bounds of the original image file, so here we have to
			 * scale the width up so that we can move one edge past the other
			 * and maintain the correct ratio: 
			 *  ___________________________
			 *  |      /                  /
			 *  |     /                  /|
			 *  |    /                  / |
			 *  |   /  transformed     /  |
			 *  |  /     image        /   |
			 *  | /                  /    |
			 *  |/__________________/_____|
			 *      original image bounds
			 * 
			 */
			surfaceWidth = (int) (surface.roomView.width * 1.25);
			if (!surface.roomView.sidePassageLeft){
				surfaceX = (int) (surface.roomView.boundX - (surface.roomView.width * 0.25));
			}
		}
		
		surfaceHeight = surface.roomView.height/4;
		if (ceiling){
			surfaceY = surface.roomView.boundY;
		} else {
			surfaceY = surface.roomView.boundY + ((surface.roomView.height * 3) / 4);
		}
		
		//load and warp the image
		this.surface = surface;
		if (ceiling){
			surfaceTexture = warpImage("/texture/" + surface.roomView.room.getCeilingTexture() + ".png");
		} else {
			surfaceTexture = warpImage("/texture/" + surface.roomView.room.getFloorTexture() + ".png");
		}
		
		//create object list for surface
		createGameObjects(surface.roomView.room, direction);

		//load shadow for objects if on floor
		if (!ceiling){
			try {
				shadow = ImageIO.read(this.getClass().getResource("/object/shadow.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
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

		//loop through objects on floor, and resize them
		for (GameMatter item : room.getItems()){
			if (item.getFace() != direction){
				continue;
			}
			
			//determine x and y based on direction facing in room
			int x = getX(item.getDimensions());
			int y = getY(item.getDimensions());

			//get base height of object
			int scale = (int) ((((double) item.getDimensions().getScale()) / 100) * (surfaceHeight * 4));

			/* determine width and height based on distance away from viewer perspective
			 * this causes items that are further away to appear smaller
			 * (variable distanceScale is the level of scaling to apply as a double between 0.5 and 1) */
			double distanceScale = 0.5 + (0.5 * (((double) y) / 100));
			if (ceiling){
				//invert scale if this object is on the ceiling
				distanceScale = 0.5 + (1 - distanceScale);
			}
			//apply scale
			scale = (int) (scale * distanceScale);

			/* determine where the horizontal center of the image should be
			 * it will be closer to the center, the further away it is
			 *    ______________________
			 *    \  o      :<---->o   /
			 *     \  o     :<--->o   /
			 *      \  o    :<-->o   /
			 *       \__o___:<->o___/
			 */
			int objectCenterX = (int) (surfaceX + (x * ((double) surfaceWidth / 100)));
			int surfaceCenterX = surfaceX + (surfaceWidth / 2);
			int diff = Math.abs(surfaceCenterX - objectCenterX);
			//apply scaling to the difference
			diff = (int) (diff * distanceScale);

			//apply the new x position, and account for having to draw from top left corner
			if (objectCenterX < surfaceCenterX){
				objectCenterX = surfaceCenterX - diff - (scale / 2);
			} else if (surfaceCenterX < objectCenterX){
				objectCenterX = surfaceCenterX + diff - (scale / 2);
			}

			int top = (int) (surfaceY + (y * ((double) surfaceHeight / 100)));
			if (!ceiling){
				//objects y positions are anchored at the top of the object if being drawn on the ceiling, so no need to modify y here
				top -= scale;
			}

			//load image
			BufferedImage image = null;
			try {
				image = ImageIO.read(this.getClass().getResource("/object/" + item.getImage() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DrawnObject dob = new DrawnObject(item, image, objectCenterX, top, scale, scale);
			obs.add(dob);
		}

		//sort objects to ensure drawing order is correct (back first)
		Collections.sort(obs);
		if (ceiling){
			//TODO wont work on ceiling objects! need custom comparator as ceiling objects are anchored on top!
			Collections.reverse(obs);
		}
		surface.objects = obs;
	}

	/**
	 * objects dimensions are given as if 0,0 is the North West corner,
	 * this method returns the x value depending on which direction
	 * the viewer is actually facing (basically rotates coordinates)
	 * 
	 * @param dim dimensions of the object
	 * @return the correct x value
	 */
	private int getX(Dimensions dim){
		Face direction = surface.roomView.playerDirection;
		if (direction == Face.EASTERN){
			if (ceiling){
				return 100 - dim.getY();
			}
			return dim.getY();
		}
		if (direction == Face.SOUTHERN){
			return 100 - dim.getX();
		}
		if (direction == Face.WESTERN){
			if (ceiling){
				return dim.getY();
			}
			return 100 - dim.getY();
		}
		//must be facing north
		return dim.getX();
	}

	/**
	 * objects dimensions are given as if 0,0 is the North West corner,
	 * this method returns the y value depending on which direction
	 * the viewer is actually facing. (basically rotates coordinates)
	 * 
	 * @param dim dimensions of the object
	 * @return the correct y value
	 */
	private int getY(Dimensions dim){
		Face direction = surface.roomView.playerDirection;
		if (direction == Face.EASTERN){
			if (ceiling){
				return dim.getX();
			}
			return 100 - dim.getX();
		}
		if (direction == Face.SOUTHERN){
			return 100 - dim.getY();
		}
		if (direction == Face.WESTERN){
			if (ceiling){
				return 100 - dim.getX();
			}
			return dim.getX();
		}
		//must be facing north
		return dim.getY();
	}
	
	/**
	 * applies perspective transformations on passed images depending on whether this is a right
	 * or left wall and returns the result
	 * uses the javaxt library
	 * Note: does not change height or width, thats currently done by the parameters passed to the DrawnObject 
	 * 
	 * @param imagePath string path to image
	 * @return the transformed Image object
	 */
	private Image warpImage(String imagePath){
		//TODO seems to be an issue with images not tiling correctly rotation???
		BufferedImage image = null;
		//load image
		try {
			image = ImageIO.read(this.getClass().getResource(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//wrap image in the transformer object
		javaxt.io.Image warped = new javaxt.io.Image(image);
		int width = warped.getWidth();
		int height = warped.getHeight();

		//TODO condense?
		//apply the transform
		if (surface.roomView.sidePassage){
			//here we use skew to slide the far away edge towards the center (side passages only)
			int closeWidth = (int) (width * 0.8);
			int farWidth = (int) (width * 0.4);
			if (surface.roomView.sidePassageLeft){
				//left side
				if (ceiling){
					warped.setCorners(0, 0, //UL
							closeWidth, 0, //UR
							width, height, //LR
							width - farWidth, height);//LL
				} else {
					//floor
					warped.setCorners(width - farWidth, 0, //UL
							width, 0, //UR
							closeWidth, height, //LR
							0, height);//LL
				}
			} else {
				//right side
				if (ceiling){
					warped.setCorners(width - closeWidth, 0, //UL
							closeWidth, 0, //UR
							farWidth, height, //LR
							0, height);//LL
				} else {
					//floor
					warped.setCorners(0, 0, //UL
							farWidth, 0, //UR
							width, height, //LR
							width - closeWidth, height);//LL
				}
			}
		} else {
			//normal transform, just have to shrink the far edge
			if (ceiling){
				warped.setCorners(0, 0, //UL
						width, 0, //UR
						(3 * (width / 4)), height, //LR
						(width / 4), height);//LL
			} else {
				//floor
				warped.setCorners((3 * (width / 4)), 0, //UL
						(width / 4), 0, //UR
						0, height, //LR
						width, height);//LL
			}
		}
		return warped.getImage();
	}
}
