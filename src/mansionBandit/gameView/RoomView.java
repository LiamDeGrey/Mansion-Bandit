package mansionBandit.gameView;

import java.awt.Graphics;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * RoomView contains all objects and information required to draw a view of a single room on the screen
 * By default the room will be rendered as if it is directly in front of the viewer,
 * A second constructor exists that creates a special version the renders a room as a side passage
 *
 * @author Andy
 *
 */
public class RoomView {
	protected MansionArea room;
	private Surface ceiling, floor, left, right, back, behind;
	protected int boundX, boundY, width, height, depth;
	protected boolean sidePassage, sidePassageLeft = false;
	protected String bandit;
	
	//number of rooms to draw into the distance
	public static int viewDepthMAX = 5;

	protected Face playerDirection;

	/**
	 * constructor constructs the Surfaces from the given room, and
	 * stores the rooms bounds
	 * 
	 * @param room the room we are rendering
	 * @param direction the direction we are facing
	 * @param boundX the left edge of the rendered view
	 * @param boundY the top edge of the rendered view
	 * @param width the width of the rendered view
	 * @param height the height of the rendered view
	 * @param depth the depth of the room (eg the n'th room behind the first one being drawn)
	 */
	public RoomView(String bandit, MansionArea room, Face direction, int boundX, int boundY, int width, int height, int depth){

		this.bandit = bandit;
		this.boundX = boundX;
		this.boundY = boundY;
		this.width = width;
		this.height = height;
		this.depth = depth;

		this.playerDirection = direction;

		this.room = room;
		
		//create the surfaces, passing in appropriate strategies
		ceiling = new Surface(this, Face.CEILING, new TopBottomStrategy(true));
		floor = new Surface(this, Face.FLOOR, new TopBottomStrategy(false));
		back = new Surface(this, getTurn(Face.NORTHERN), new BackWallStrategy());
		left = new Surface(this, getTurn(direction.WESTERN), new SideWallStrategy(true));
		right = new Surface(this, getTurn(direction.EASTERN), new SideWallStrategy(false));
	}
	
	/**
	 * special constructor is only used in situations where it is drawing a room immediately to the side of the one the player is in
	 */
	
	/**
	 * special constructor is the same as above except it creates a modified RoomView designed to handle hallways that branch off of 
	 * a straight hallway (a side passage)
	 * 
	 * @param room
	 * @param face
	 * @param boundX
	 * @param boundY
	 * @param width
	 * @param height
	 * @param sidePassageLeft
	 */
	public RoomView(MansionArea room, Face face, int boundX, int boundY, int width, int height, boolean sidePassageLeft){
		
		//set flags
		sidePassage = true;
		this.sidePassageLeft = sidePassageLeft;
		
		this.boundY = boundY;
		this.width = width;
		this.height = height;
		
		if (sidePassageLeft){
			this.boundX = boundX - width;
		} else {
			this.boundX = boundX + width;
		}
		
		this.depth = viewDepthMAX;

		this.playerDirection = face;

		this.room = room;
		
		//no need for side walls here
		ceiling = new Surface(this, Face.CEILING, new TopBottomStrategy(true));
		floor = new Surface(this, Face.FLOOR, new TopBottomStrategy(false));
		back = new Surface(this, getTurn(Face.NORTHERN), new BackWallStrategy());
	}
	
	/**
	 * helper method to transform directions based on which way the player is facing
	 * 
	 * @param direction is the direction of the wall we want, if we assume the backwall is North (eg West would be the left wall)
	 * @return the Face that we should use to get the actual wall we want from the room
	 */
	private Face getTurn(Face direction){
		int leftAmount = 0;
		switch (playerDirection){
		case NORTHERN:
			//no need compute anything else here
			return direction;
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
			return Face.getLeft(direction);
		case 2:
			return Face.getLeft(Face.getLeft(direction));
		case 3:
			return Face.getLeft(Face.getLeft(Face.getLeft(direction)));
		}
		return direction;
	}

	/**
	 * paint the room to the screen by painting each surface individually
	 * according to a 'simple painters algorithm'
	 * @param g graphics object to use
	 */
	public void paintRoom(Graphics g){
		/* 
		 * draw back wall first
		 * as it may recurse through and draw more rooms behind it
		 */
		back.paint(g);
		
		//draw the sides. if this room is a side passage these may be null, hence the check
		if (!sidePassage){
			left.paint(g);
			right.paint(g);
		}
		
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

		//only basic implementation needed for walls and ceiling now
		//that they should only hold one item each at the beginning
		GameMatter found = floor.findObject(x,y);
		//search floor Surface for object
		if (found == null){
			found = ceiling.findObject(x,y);
			if (found == null){
				//if no object found we will try the back wall
				found = back.findObject(x,y);
				if (found == null){
					//if no object found we will try the side walls
					found = left.findObject(x,y);
					if (found == null){
						//if no object found we will try the back wall
						found = right.findObject(x,y);
					}
				}
			}
		}
		return found;
	}
}
