package mansionBandit.gameView;

import java.awt.Graphics;
import java.util.List;

import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

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
	private Face face;
	protected RoomView roomView;
	protected List<DrawnObject> objects;
//	private DEMOWALL wall;
	
//	/**
//	 * create surface object and configure strategy
//	 * @param room
//	 * @param strategy
//	 */
//	public Surface(RoomView room, DEMOWALL wall, SurfaceStrategy strategy){
//		this.strategy = strategy;
//		roomView = room;
//		this.wall = wall;
//		strategy.setupSurface(this, wall);
//		//TODO add objects to surface
//	}
	
	/**
	 * create surface object and configure strategy
	 * @param room
	 * @param strategy
	 */
	public Surface(RoomView room, Face face, SurfaceStrategy strategy){
		this.strategy = strategy;
		roomView = room;
		this.face = face;
		strategy.setupSurface(this, face);
		//TODO add objects to surface
	}
	
	/**
	 * sets a new strategy for this surface
	 * 
	 * @param newStrat the new surface
	 */
	public void setStrategy(SurfaceStrategy newStrat){
		newStrat.setupSurface(this, face);
		strategy = newStrat;
	}
	
	public void update(){
		//TODO is a full version of setup necessary?
		strategy.setupSurface(this, face);
	}
	
	public void paint(Graphics g){
		strategy.paintSurface(g);
	}
	
	public GameMatter findObject(int x, int y){
		//TODO look through objects on surface to find object on top, under coords
		return strategy.click(x, y);
	}
}
