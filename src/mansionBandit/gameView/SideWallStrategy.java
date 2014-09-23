package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SideWallStrategy implements SurfaceStrategy {
	private boolean left;
	private Surface surface;
	private BufferedImage surfaceTexture;
	private int boundX, boundY, width, height;
	
	public SideWallStrategy(boolean left) {
		this.left = left;
	}

	@Override
	public void paintSurface(Graphics g) {
		g.drawImage(surfaceTexture, boundX, boundY, width, height, null);
	}

	@Override
	public Object click(int x, int y) {
		//TODO implement clicking on side walls??
		return null;
	}

	@Override
	public void setParentSurface(Surface surface) {
		this.surface = surface;
		try {
			//set image for the view
			if (left){
				surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + surface.roomView.room.getWall() + "L.png"));
			} else {
				surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + surface.roomView.room.getWall() + "R.png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set bounds for the surface
		width = surface.roomView.width / 4;
		height = surface.roomView.height;
		boundY = surface.roomView.boundY;
		if (left){
			boundX = surface.roomView.boundX;
		} else {
			boundX = surface.roomView.boundX + ((surface.roomView.width * 3) / 4);
		}
	}
}
