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
	public void paintSurface(Graphics g);
	public GameMatter click(int x, int y);
	public void setupSurface(Surface surface, Face face);
}
