package mansionBandit.gameView;

import java.awt.Graphics;

public class Surface {
	private SurfaceStrategy strategy;
	protected RoomView roomView;
	
	/**
	 * create surface object and configure strategy
	 * @param room
	 * @param strategy
	 */
	public Surface(RoomView room, SurfaceStrategy strategy){
		this.strategy = strategy;
		roomView = room;
		strategy.setParentSurface(this);
		//TODO add objects to surface
	}
	
	public void paint(Graphics g){
		strategy.paintSurface(g);
	}
	
	public Object findObject(int x, int y){
		//TODO look through objects on surface to find object on top, under coords
		return null;
	}
}
