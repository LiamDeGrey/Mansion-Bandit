package mansionBandit.gameView;

import java.awt.Graphics;

/**
 * RoomView contains all objects and information required to draw a view of a single room on the screen
 * 
 * @author Andy
 *
 */
public class RoomView {
	private Surface ceiling, floor, left, right, back;
	private int boundX, boundY, width, height;
	
	/**
	 * constructor constructs the Surfaces from the given room, and
	 * stores the rooms bounds
	 */
	public RoomView(int boundX, int boundY, int width, int height){
		this.boundX = boundX;
		this.boundY = boundY;
		this.width = width;
		this.height = height;
		//TODO use Liams room object
	}
	
	//TODO draw several rooms into the distance
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
