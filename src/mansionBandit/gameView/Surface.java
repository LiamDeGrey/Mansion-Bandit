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
	
	/**
	 * create surface object and configure strategy
	 * @param room
	 * @param strategy
	 */
	public Surface(RoomView room, DEMOWALL wall, SurfaceStrategy strategy){
		this.strategy = strategy;
		roomView = room;
		strategy.setupSurface(this, wall);
		//TODO add objects to surface
	}
	
	public void paint(Graphics g){
		strategy.paintSurface(g);
	}
	
	public Object findObject(int x, int y){
		//TODO look through objects on surface to find object on top, under coords
		return strategy.click(x, y);
	}
}
