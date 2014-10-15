package mansionBandit.gameView;

import java.awt.Graphics;
import java.util.List;

import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * a surface in a room (wall, floor, ceiling)
 * different strategies can be passed in to the surface to determine how it is drawn
 * (eg left wall or back wall)
 * 
 * NOTE: Strategy pattern has become somewhat redundant due to strategies no longer being swapped around,
 * which was the initial reason for using the design pattern
 * 
 * @author Andy
 *
 */
public class Surface {
	private SurfaceStrategy strategy;
	private Face face;
	protected RoomView roomView;
	protected List<DrawnObject> objects;
	
	/**
	 * create surface object and configure strategy
	 * 
	 * @param room
	 * @param strategy
	 */
	public Surface(RoomView room, Face face, SurfaceStrategy strategy){
		this.strategy = strategy;
		roomView = room;
		this.face = face;
		strategy.setupSurface(this, face);
	}
	
	public void paint(Graphics g){
		strategy.paintSurface(g);
	}
	
	/**
	 * searches this surface to see if object has been clicked
	 * 
	 * @param x x position to check
	 * @param y y position to check
	 * @return the object if found or null otherwise
	 */
	public GameMatter findObject(int x, int y){
		return strategy.findObject(x, y);
	}
}
