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
	private Image surfaceTexture;
	private int surfaceX, surfaceY, surfaceWidth, surfaceHeight;

	public TopBottomStrategy(boolean top) {
		this.ceiling = top;
	}

	@Override
	public void paintSurface(Graphics g) {
		g.drawImage(surfaceTexture, surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);

		//draw objects on the wall
		for (DrawnObject ob : surface.objects){
			
			//TODO move into its own loop to prevent shadows overlapping other objects
			if (!ceiling){
				//draw shadow if object on floor
				BufferedImage shadow = null;
				try {
					shadow = ImageIO.read(this.getClass().getResource("/object/shadow.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.drawImage(shadow, ob.getBoundX() -10, ob.getBoundY() + ob.getHeight() - 10, ob.getWidth() + 20, 20, null);
			}
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
	public void setupSurface(Surface surface, Face face) {
		
		//set bounds for the surface
		surfaceX = surface.roomView.boundX;
		surfaceWidth = surface.roomView.width;
		
		//override for sidepassage
		if (surface.roomView.sidePassage){
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
		
		this.surface = surface;
		if (ceiling){
			surfaceTexture = warpImage("/ceilings/" + surface.roomView.room.getCeilingTexture() + ".png");
		} else {
			surfaceTexture = warpImage("/floors/" + surface.roomView.room.getFloorTexture() + ".png");
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
			int x = getX(item.getDimensions());
			int y = getY(item.getDimensions());

			//get base height of object
			int size = (int) ((((double) item.getDimensions().getScale()) / 100) * (surfaceHeight * 4));

			/* determine width and height based on distance away from viewer perspective
			 * this causes items that are further away to appear smaller
			 * (variable scale is the level of scaling to apply as a double between 0.5 and 1) */
			double scale = 0.5 + (0.5 * (((double) y) / 100));
			if (this.ceiling){
				//invert scale if this object is on the ceiling
				scale = 0.5 + (1 - scale);
			}
			//apply scale
			size = (int) (size * scale);

			//determine where the horizontal center of the image should be
			int objectCenterX = (int) (surfaceX + (x * ((double) surfaceWidth / 100)));
			//have to alter horizontal position to be closer to the center when further back
			int surfaceCenterX = surfaceX + (surfaceWidth / 2);
			int diff = Math.abs(surfaceCenterX - objectCenterX);
			//apply scaling to the diff
			diff = (int) (diff * scale);

			//apply the new x position, and account for having to draw from top left corner
			if (objectCenterX < surfaceCenterX){
				objectCenterX = surfaceCenterX - diff - (size / 2);
			} else if (surfaceCenterX < objectCenterX){
				objectCenterX = surfaceCenterX + diff - (size / 2);
			}

			//TODO change variable names so that we're not relying on scope?
			int top = (int) (surfaceY + (y * ((double) surfaceHeight / 100)));
			if (!this.ceiling){
				//objects y positions are anchored at the top of the object if being drawn on the ceiling, so no need to modify y here
				top -= size;
			}

			BufferedImage image = null;
			try {
				image = ImageIO.read(this.getClass().getResource("/object/" + item.getName() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DrawnObject dob = new DrawnObject(item, image, objectCenterX, top, size, size);
			obs.add(dob);
		}
		Collections.sort(obs);
		if (ceiling){
			//TODO wont work on ceiling objects! need custom comparator!
			Collections.reverse(obs);
		}
		surface.objects = obs;
	}

	private int getX(Dimensions dim){
		Face face = surface.roomView.playerDirection;
		if (face == Face.EASTERN){
			if (ceiling){
				return 100 - dim.getY();
			}
			return dim.getY();
		}
		if (face == Face.SOUTHERN){
			return 100 - dim.getX();
		}
		if (face == Face.WESTERN){
			if (ceiling){
				return dim.getY();
			}
			return 100 - dim.getY();
		}
		//must be facing north
		return dim.getX();
	}

	private int getY(Dimensions dim){
		Face face = surface.roomView.playerDirection;
		if (face == Face.EASTERN){
			if (ceiling){
				return dim.getX();
			}
			return 100 - dim.getX();
		}
		if (face == Face.SOUTHERN){
			return 100 - dim.getY();
		}
		if (face == Face.WESTERN){
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
	 * @return an transformed Image object
	 */
	private Image warpImage(String imagePath){
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaxt.io.Image warped = new javaxt.io.Image(image);
		int width = warped.getWidth();
		int height = warped.getHeight();

		//TODO make neater
		//skew is used for sliding the far away edge towards the center (side passages only)
		if (surface.roomView.sidePassage){
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
					warped.setCorners(0, 0, //UL
							farWidth, 0, //UR
							width, height, //LR
							width - closeWidth, height);//LL
				}
			}
		} else {

			if (ceiling){
				warped.setCorners(0, 0, //UL
						width, 0, //UR
						(3 * (width / 4)), height, //LR
						(width / 4), height);//LL
			} else {
				warped.setCorners((3 * (width / 4)), 0, //UL
						(width / 4), 0, //UR
						0, height, //LR
						width, height);//LL
			}
		}
		return warped.getImage();
	}
}
