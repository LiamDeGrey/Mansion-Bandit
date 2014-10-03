package mansionBandit.gameView;

import mansionBandit.gameWorld.matter.GameMatter;

/**
 * wrapper class to hold a game object to be drawn
 * the wrapper holds the distorted size information, allowing
 * the object to be drawn smaller or larger, and be clickable
 * 
 * @author Andy
 *
 */
public class DrawnObject implements Comparable<DrawnObject>{
	//game object object referred to
	private GameMatter gameObject;
	//size data
	private int boundX, boundY, width, height;
	
	public GameMatter getGameObject() {
		return gameObject;
	}
	
	public String getImage(){
		return gameObject.getName();
	}

	public int getBoundX() {
		return boundX;
	}

	public int getBoundY() {
		return boundY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getScale(){
		return gameObject.getDimensions().getScale();
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

	public DrawnObject(GameMatter ob, int x, int y, int w, int h){
		boundX = x;
		boundY = y;
		width = w;
		height = h;
		gameObject = ob;
	}

	@Override
	public int compareTo(DrawnObject o) {
		return (boundY + height) - (o.boundY + o.height);
	}

}
