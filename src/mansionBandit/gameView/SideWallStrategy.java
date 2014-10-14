package mansionBandit.gameView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class SideWallStrategy implements SurfaceStrategy {
	private boolean left;
	private Surface surface;
	private RoomView sideRoom = null;
	private Image surfaceTexture;
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
			g.drawImage(ob.getImage(), ob.getBoundX(), ob.getBoundY(), ob.getWidth(), ob.getHeight(), null);
		}
	}

	@Override
	public GameMatter click(int x, int y) {
		//TODO implement clicking on side walls??
		return null;
	}

	@Override
	public void setupSurface(Surface surface, Face direction) {
		this.surface = surface;
		//get the warped image
		surfaceTexture = warpImage("/texture/" + surface.roomView.room.getWallTexture() + ".png");

		//set bounds for the surface
		surfaceWidth = surface.roomView.width / 4;
		surfaceHeight = surface.roomView.height;
		surfaceY = surface.roomView.boundY;
		if (left){
			surfaceX = surface.roomView.boundX;
		} else {
			surfaceX = surface.roomView.boundX + ((surface.roomView.width * 3) / 4);
		}

		if (surface.roomView.room instanceof Hallway){
			//if we are in a hallway we should look to see
			//what room is on the other side of this surface
			MansionArea nextRoom = null;
			switch (direction){
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
				//next room is another hallway we should draw.
				//as it is a very specific perspective we must use a special constructor in RoomView
				sideRoom = new RoomView(nextRoom, surface.roomView.playerDirection, surface.roomView.boundX, surface.roomView.boundY, surface.roomView.width, surface.roomView.height, left);

			} else {
				//its a room, so use hallway wall texture
				surfaceTexture = warpImage("/texture/hallwayW.png");
			}
		}

		//create object list for surface
		createGameObjects(surface.roomView.room, direction);
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

		for (GameMatter item : room.getItems()){
			if (item instanceof Bandit){
				//bandits dont get drawn on walls
				continue;
			}
			
			if (item.getFace() != direction){
				//item does not belong to this surface
				continue;
			}

			int x = item.getDimensions().getX();
			int y = item.getDimensions().getY();

			//determine scale
			int scale = (int) ((((double) item.getDimensions().getScale()) / 100) * surfaceHeight);

			/* determine where the vertical center of the image should be
			 * it will be closer to the center, the further away it is,
			 * same as for TopBottomStrategy, although the problem is
			 * flipped onto its side
			 *    ______________________
			 *    \  o      :<---->o   /
			 *     \  o     :<--->o   /
			 *      \  o    :<-->o   /
			 *       \__o___:<->o___/
			 */
			double distanceScale = 0.5 + (0.5 * (((double) x) / 100));
			if (this.left){
				//invert scale if this object is on the left
				distanceScale = 0.5 + (1 - distanceScale);
			}
			//apply scale
			scale = (int) (scale * distanceScale);

			//determine where the vertical center of the image should be
			int objectCenterY = (int) (surfaceY + (y * ((double) surfaceHeight / 100)));
			//have to alter vertical position to be closer to the center when further back
			int surfaceCenterY = surfaceY + (surfaceHeight / 2);
			int diff = Math.abs(surfaceCenterY - objectCenterY);
			//apply scaling to the diff
			diff = (int) (diff * distanceScale);

			//apply the new y position, and account for having to draw from top left corner
			if (objectCenterY < surfaceCenterY){
				objectCenterY = surfaceCenterY - diff - scale;
			} else if (surfaceCenterY < objectCenterY){
				objectCenterY = surfaceCenterY + diff - scale;
			}

			int left = (int) (surfaceX + (x * ((double) surfaceWidth / 100))) - (scale / 4);

			//create the wrapped object and add to list (with warped image)
			DrawnObject dob = new DrawnObject(item, warpImage("/object/" + item.getImage() + ".png"), left, objectCenterY, scale / 2, scale);
			dob = computeCorners(item);
			obs.add(dob);
		}
		surface.objects = obs;
	}

	private DrawnObject computeCorners(GameMatter item){
		int LLx ,LLy, LRx, LRy, ULx, ULy, URx, URy;
		int scale = item.getDimensions().getScale();

		/*
		 * objects are defined as a position and a size relative to a 100x100 wall
		 * the x and y position specify the the bottom center of the object
		 *
		 * 0,0 ___________
		 *    |   ___ ____|____
		 *    |  |\ /|    |    | scale
		 *    |  |/_\|____|____|
		 *    |    ^(x,y) |
		 *    |___________|
		 *                 100,100
		 */

		//get unscaled corners
		//LL
		LLx = item.getDimensions().getX() - (scale / 2);
		LLy = item.getDimensions().getY();
		//LR
		LRx = LLx + scale;
		LRy = LLy;
		//UL
		ULx = LLx;
		ULy = LLy - scale;
		//UR
		URx = LRx;
		URy = ULy;
		//scale the corners
		LLy = scaleY(LLx, LLy);
		LRy = scaleY(LRx, LRy);
		ULy = scaleY(ULx, ULy);
		URy = scaleY(URx, URy);

//		System.out.println("LLx: " + LLx
//				+ " LLy: " + LLy
//				+ " LRx: " + LRx
//				+ " LRy: " + LRy
//				+ " ULx: " + ULx
//				+ " ULy: " + ULy
//				+ " URx: " + URx
//				+ " URy: " + URy);

		Image image = warpImage("/object/" + item.getImage() + ".png", ULy, URy, LRy, LLy);
		//Image image = warpImage("/object/" + item.getImage() + ".png");

		//find points to draw image with
		int drawnY, height;
		if (ULy <= URy){
			//top left corner is higher
			drawnY = ULy;
			height = scale;
		} else {
			drawnY = URy;
			height = scale + (URy - ULy);
		}

		//scale all points to fit surface
		scale = (int) ((((double) scale) / 100) * surfaceHeight);
		height = (int) ((((double) height) / 100) * surfaceHeight);
		int left = (int) (surfaceX + (item.getDimensions().getX() * ((double) surfaceWidth / 100))) - (scale / 4);
		int top = surfaceY + (drawnY * surfaceHeight / 100);

		return new DrawnObject(item, image, left, top, scale / 2, height);
	}

	private int scaleY(int x, int y){
		/* determine where the vertical position of the given point should be
		 * it will be closer to the center the further away it is,
		 * same as for TopBottomStrategy, although the diagram should be
		 * flipped onto its side
		 *    ______________________
		 *    \  o      :<---->o   /
		 *     \  o     :<--->o   /
		 *      \  o    :<-->o   /
		 *       \__o___:<->o___/
		 */

		double distanceScale = 0.3 + (0.7 * (((double) x) / 100));

		if (left){
			//invert scale if this object is on the left
			distanceScale = 0.3 + (1 - distanceScale);
		}

		int surfaceCenterY = 50;
		int diff = y - surfaceCenterY;
		//apply scaling to the diff
		diff = (int) (diff * distanceScale);

		//apply the new y position
		return surfaceCenterY + diff;
	}

	/**
	 * warps the image according to the given points
	 *
	 * @param imagePath
	 * @param ULy
	 * @param URy
	 * @param LRy
	 * @param LLy
	 * @return
	 */
	private Image warpImage(String imagePath, double ULy, double URy, double LRy, double LLy){
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//wrap in transformer object
		javaxt.io.Image warped = new javaxt.io.Image(image);
		int width = warped.getWidth();
		int imageHeight = warped.getHeight();

		double origTop = Math.min(ULy, URy);

		//scale points to image
//		float UL = (float) (((ULy - origTop) / origHeight) * height);
//		float UR = (float) (((URy - origTop) / origHeight) * height);
//		float LR = (float) (((LRy - origTop) / origHeight) * height);
//		float LL = (float) (((LLy - origTop) / origHeight) * height);

		//get corners as distance from the top
		float UL = (float) (ULy - origTop);
		float UR = (float) (URy - origTop);
		float LR = (float) (LRy - origTop);
		float LL = (float) (LLy - origTop);

		double origHeight = Math.max(LL, LR);

		//scale to image size
		UL = (float) ((UL / origHeight) * imageHeight);
		UR = (float) ((UR / origHeight) * imageHeight);
		LR = (float) ((LR / origHeight) * imageHeight);
		LL = (float) ((LL / origHeight) * imageHeight);


//		System.out.print("float values: top left = (" + 0 + "," + UL + ")"
//				+ " top right = (" + width + "," + UR + ")"
//				+ " bottom right = (" + width + "," + LR + ")"
//				+ " bottom left = (" + 0 + "," + LL + ")");

		warped.setCorners(0, UL, //UL
				width, UR, //UR
				width, LR, //LR
				0, LL);//LL

		return warped.getImage();
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
		//load image
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//wrap in transformer object
		javaxt.io.Image warped = new javaxt.io.Image(image);
		int width = warped.getWidth();
		int height = warped.getHeight();

		//apply the transform
		//TODO drag far side towards vertical center for better perspective
		if (this.left){
			warped.setCorners(0, 0, //UL
					width, height / 4, //UR
					width, 3 * (height / 4), //LR
					0, height);//LL
		} else {
			//right wall
			warped.setCorners(0, height / 4, //UL
					width, 0, //UR
					width, height, //LR
					0, 3 * (height / 4));//LL
		}
		return warped.getImage();
	}
}
