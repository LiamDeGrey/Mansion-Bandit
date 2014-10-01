package mansionBandit.gameView;

import java.awt.Graphics;

import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Face;

/**
 * RoomView contains all objects and information required to draw a view of a single room on the screen
 *
 * @author Andy
 *
 */
public class RoomView {
	public DEMOROOM roomDEMO;
	protected Room room;
	private Surface ceiling, floor, left, right, back, behind;
	protected int boundX, boundY, width, height, depth;
	public static int viewDepthMAX = 2;

	protected int directionDEMO;
	protected Face face;

	/**
	 * constructor constructs the Surfaces from the given room, and
	 * stores the rooms bounds
	 */
	//TODO remove
	public RoomView(DEMOROOM room, int boundX, int boundY, int width, int height, int depth){

		this.boundX = boundX;
		this.boundY = boundY;
		this.width = width;
		this.height = height;
		this.depth = depth;
		directionDEMO = room.getDirection();

		//TODO use Liams room object
		this.roomDEMO = room;

		//TODO create surfaces
		ceiling = new Surface(this, room.getWall(room.C), new TopBottomStrategy(true));
		floor = new Surface(this, room.getWall(room.F), new TopBottomStrategy(false));
		back = new Surface(this, room.getWall(room.N), new BackWallStrategy());
		left = new Surface(this, room.getWall(room.W), new SideWallStrategy(true));
		right = new Surface(this, room.getWall(room.E), new SideWallStrategy(false));
		//behind is never drawn
		behind = new Surface(this, room.getWall(room.S), new BackWallStrategy());
	}

	/**
	 * constructor constructs the Surfaces from the given room, and
	 * stores the rooms bounds
	 */
	public RoomView(Room room, int boundX, int boundY, int width, int height, int depth){

		this.boundX = boundX;
		this.boundY = boundY;
		this.width = width;
		this.height = height;
		this.depth = depth;
		face = Face.NORTHERN;

		this.room = room;

		//TODO fix!
//		ceiling = new Surface(this, room.getWall(), new TopBottomStrategy(true));
//		floor = new Surface(this, room.getWall(room.F), new TopBottomStrategy(false));
//		back = new Surface(this, room.getWall(room.N), new BackWallStrategy());
//		left = new Surface(this, room.getWall(room.W), new SideWallStrategy(true));
//		right = new Surface(this, room.getWall(room.E), new SideWallStrategy(false));
		//behind is never drawn
//		behind = new Surface(this, room.getWall(room.S), new BackWallStrategy());
	}

	public void update(){
		//TODO make much much MUCH nicer
		//TODO account for moving into a new room
		if (roomDEMO.getDirection() == directionDEMO){
			return;
		}
		//update walls and direction

		if (roomDEMO.getLeft(directionDEMO) == roomDEMO.getDirection()){
			//we have turned left
			Surface temp = back;
			back = left;
			left = behind;
			behind = right;
			right = temp;
		} else if (roomDEMO.getLeft(roomDEMO.getLeft(directionDEMO)) == roomDEMO.getDirection()){
			//we have turned around 180 degrees
			Surface temp = back;
			back = behind;
			behind = temp;
			temp = left;
			left = right;
			right = temp;
		} else{
			//we must have turned right
			Surface temp = back;
			back = right;
			right = behind;
			behind = left;
			left = temp;
		}

		directionDEMO = roomDEMO.getDirection();

		back.setStrategy(new BackWallStrategy());
		left.setStrategy(new SideWallStrategy(true));
		right.setStrategy(new SideWallStrategy(false));
		ceiling.update();
		floor.update();
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
	public Object findObjectByMouse(int x, int y){

		//TODO make work on side walls??? (or Ceiling (eg chandalier?))

		//search floor Surface for object
		Object found = floor.findObject(x,y);
		if (found != null){
			//if no object found we will try the back wall
			found = back.findObject(x,y);
		}
		return found;
	}
}
