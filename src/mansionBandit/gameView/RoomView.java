package mansionBandit.gameView;

import java.awt.Graphics;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * RoomView contains all objects and information required to draw a view of a single room on the screen
 *
 * @author Andy
 *
 */
public class RoomView {
	//public DEMOROOM roomDEMO;
	protected MansionArea room;
	private Surface ceiling, floor, left, right, back, behind;
	protected int boundX, boundY, width, height, depth;
	public static int viewDepthMAX = 2;

	protected Face playerDirection;

	/**
	 * constructor constructs the Surfaces from the given room, and
	 * stores the rooms bounds
	 */
	public RoomView(MansionArea room, Face face, int boundX, int boundY, int width, int height, int depth){

		this.boundX = boundX;
		this.boundY = boundY;
		this.width = width;
		this.height = height;
		this.depth = depth;

		//TODO get direction from player
		this.playerDirection = face;

		this.room = room;
		
		//TODO update to work in any direction
		ceiling = new Surface(this, Face.CEILING, new TopBottomStrategy(true));
		floor = new Surface(this, Face.FLOOR, new TopBottomStrategy(false));
		back = new Surface(this, getTurn(Face.NORTHERN), new BackWallStrategy());
		left = new Surface(this, getTurn(face.WESTERN), new SideWallStrategy(true));
		right = new Surface(this, getTurn(face.EASTERN), new SideWallStrategy(false));
		//behind is never drawn
		//behind = new Surface(this, face.SOUTHERN, new BackWallStrategy());
	}
	
	/**
	 * helper method to transform directions based on which way the player is facing
	 * @param face
	 * @return
	 */
	private Face getTurn(Face face){
		int leftAmount = 0;
		switch (playerDirection){
		case NORTHERN:
			return face;
		case WESTERN:
			leftAmount = 1;
			break;
		case SOUTHERN:
			leftAmount = 2;
			break;
		case EASTERN:
			leftAmount = 3;
		}
		
		switch (leftAmount){
		case 1:
			return Face.getLeft(face);
		case 2:
			return Face.getLeft(Face.getLeft(face));
		case 3:
			return Face.getLeft(Face.getLeft(Face.getLeft(face)));
		}
		return face;
	}

	public void update(){
//		//TODO make much much MUCH nicer
//		//TODO account for moving into a new room
//		if (roomDEMO.getDirection() == directionDEMO){
//			return;
//		}
//		//update walls and direction
//
//		if (roomDEMO.getLeft(directionDEMO) == roomDEMO.getDirection()){
//			//we have turned left
//			Surface temp = back;
//			back = left;
//			left = behind;
//			behind = right;
//			right = temp;
//		} else if (roomDEMO.getLeft(roomDEMO.getLeft(directionDEMO)) == roomDEMO.getDirection()){
//			//we have turned around 180 degrees
//			Surface temp = back;
//			back = behind;
//			behind = temp;
//			temp = left;
//			left = right;
//			right = temp;
//		} else{
//			//we must have turned right
//			Surface temp = back;
//			back = right;
//			right = behind;
//			behind = left;
//			left = temp;
//		}
//
//		directionDEMO = roomDEMO.getDirection();
//
//		back.setStrategy(new BackWallStrategy());
//		left.setStrategy(new SideWallStrategy(true));
//		right.setStrategy(new SideWallStrategy(false));
//		ceiling.update();
//		floor.update();
	}

	/**
	 * paint the room to the screen by painting each surface individually
	 * according to a 'simple painters algorithm'
	 * @param g
	 */
	public void paintRoom(Graphics g){
		//draw sides first
		left.paint(g);
		right.paint(g);
		//draw back wall
		back.paint(g);
		//draw ceiling
		ceiling.paint(g);
		//finally draw the floor
		floor.paint(g);
	}

	/**
	 * searches the room for an object that was clicked and returns it
	 * returns null if no object was found under the given coordinates
	 *
	 * @param x the x location of the mouse click
	 * @param y the y location of the mouse click
	 * @return the object that was clicked (or null if not object found)
	 */
	public GameMatter findObjectByMouse(int x, int y){

		//TODO make work on side walls??? (or Ceiling (eg chandalier?))

		//search floor Surface for object
		GameMatter found = floor.findObject(x,y);
		if (found == null){
			//if no object found we will try the back wall
			found = back.findObject(x,y);
		}
		return found;
	}
}
