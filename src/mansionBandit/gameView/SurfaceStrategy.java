package mansionBandit.gameView;

import java.awt.Graphics;

/**
 * SurfaceStrategy handles drawing each 'surface' in the game
 * 
 * @author Andy
 *
 */
public interface SurfaceStrategy {
	public void paintSurface(Graphics g);
	public Object click(int x, int y);
}
