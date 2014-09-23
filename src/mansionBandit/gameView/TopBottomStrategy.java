package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TopBottomStrategy implements SurfaceStrategy {
	private boolean top;
	private Surface surface;
	private BufferedImage surfaceTexture;
	private int boundX, boundY, width, height;
	
	public TopBottomStrategy(boolean top) {
		this.top = top;
	}

	@Override
	public void paintSurface(Graphics g) {
		g.drawImage(surfaceTexture, boundX, boundY, width, height, null);
	}

	@Override
	public Object click(int x, int y) {
		if (top){
			return null;
		}
		//TODO search floor for items
		return null;
	}

	@Override
	public void setParentSurface(Surface surface) {
		this.surface = surface;
		try {
			//set image for the view
			if (top){
				surfaceTexture = ImageIO.read(this.getClass().getResource("/ceilings/" + surface.roomView.room.getCeiling() + ".png"));
			} else {
				surfaceTexture = ImageIO.read(this.getClass().getResource("/floors/" + surface.roomView.room.getFloor() + ".png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set bounds for the surface
		boundX = surface.roomView.boundX;
		width = surface.roomView.width;
		height = surface.roomView.height/4;
		if (top){
			boundY = surface.roomView.boundY;
		} else {
			boundY = surface.roomView.boundY + ((surface.roomView.height * 3) / 4);
		}
	}

}
