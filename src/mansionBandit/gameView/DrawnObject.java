package mansionBandit.gameView;

import java.awt.Image;

import mansionBandit.gameWorld.matter.GameMatter;

/**
 * wrapper class to hold a game object to be drawn.
 * the wrapper holds the distorted size information, allowing
 * the object to be drawn  to specific sizes, and be found via
 * its coordinates
 * 
 * @author Andy
 *
 */
public class DrawnObject implements Comparable<DrawnObject>{
	private GameMatter gameObject;
	private Image image;
	//size data
	private int boundX, boundY, width, height;
	
	/**
	 * get the game object this DrawnObject contains
	 * @return
	 */
	public GameMatter getGameObject() {
		return gameObject;
	}
	
	/**
	 * get the image of this object (may be transformed
	 * already by a strategy)
	 * @return
	 */
	public Image getImage(){
		return image;
	}

	/**
	 * get the left most side of the object
	 * @return the x value
	 */
	public int getBoundX() {
		return boundX;
	}

	/**
	 * get the top most side of the object
	 * @return the y value
	 */
	public int getBoundY() {
		return boundY;
	}

	/**
	 * get the drawn width of the object
	 * @return the width value
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * get the drawn height of the the object
	 * @return the height value
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * returns the object if the bounding box lies behind the points given
	 * 
	 * @param x the x coordinate to check
	 * @param y the y coordinate to check
	 * @return the object if found, or null if point is not on object
	 */
	public GameMatter click(int x, int y){
		//TODO make better?
		if (x < boundX || x > boundX + width || y < boundY || y > boundY + height){
			return null;
		}
		return gameObject;
	}

	/**
	 * creates a DrawnObject
	 * 
	 * @param ob the object to be drawn
	 * @param image the image to be used for drawing the object
	 * @param x the top left x coordinate
	 * @param y the top left y coordinate
	 * @param w the horizontal size of the image
	 * @param h the vertical size of the image
	 */
	public DrawnObject(GameMatter ob, Image image, int x, int y, int w, int h){
		this.image = image;
		boundX = x;
		boundY = y;
		width = w;
		height = h;
		gameObject = ob;
	}

	@Override
	public int compareTo(DrawnObject o) {
		//TODO only valid for items on the floor!
		//need to account for items being anchored at the top center!
		return (boundY + height) - (o.boundY + o.height);
	}

}
