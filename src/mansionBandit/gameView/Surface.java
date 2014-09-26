package mansionBandit.gameView;

import java.awt.Graphics;
import java.util.List;

/**
 * a wall (or surface in a room)
 * can represent either a ceiling, floor, or wall
 * different strategies can be passed in to the surface to determine how it is drawn
 * (eg left wall or back wall)
 * 
 * @author Andy
 *
 */
public class Surface {
	private SurfaceStrategy strategy;
	protected RoomView roomView;
	protected List<DrawnObject> objects;
	private DEMOWALL wall;
	
	/**
	 * create surface object and configure strategy
	 * @param room
	 * @param strategy
	 */
	public Surface(RoomView room, DEMOWALL wall, SurfaceStrategy strategy){
		this.strategy = strategy;
		roomView = room;
		this.wall = wall;
		strategy.setupSurface(this, wall);
		//TODO add objects to surface
	}
	
	/**
	 * sets a new strategy for this surface
	 * 
	 * @param newStrat the new surface
	 */
	public void setStrategy(SurfaceStrategy newStrat){
		newStrat.setupSurface(this, wall);
		strategy = newStrat;
	}
	
	public void update(){
		//TODO is a full version of setup necessary?
		strategy.setupSurface(this, wall);
	}
	
	public void paint(Graphics g){
		strategy.paintSurface(g);
	}
	
	public Object findObject(int x, int y){
		//TODO look through objects on surface to find object on top, under coords
		return strategy.click(x, y);
	}
}
