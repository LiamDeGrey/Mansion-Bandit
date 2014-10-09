package mansionBandit.gameView;

import java.awt.Graphics;

import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * SurfaceStrategy handles drawing each 'surface' in the game
 * 
 * @author Andy
 *
 */
public interface SurfaceStrategy {
	
	/**
	 * paint the surface to the graphics object
	 * @param g the graphics object
	 */
	public void paintSurface(Graphics g);
	
	/**
	 * searches this surface to see if object has been clicked
	 * 
	 * @param x x position to check
	 * @param y y position to check
	 * @return the object if found or null otherwise
	 */
	public GameMatter click(int x, int y);
	
	/**
	 * sets up the strategy with all the objects, images, and coordinates it needs to 
	 * display the surface correctly
	 * 
	 * @param surface the surface to draw
	 * @param direction the direction of the surface
	 */
	public void setupSurface(Surface surface, Face direction);
}
