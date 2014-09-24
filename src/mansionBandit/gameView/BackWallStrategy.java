package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BackWallStrategy implements SurfaceStrategy {
	private Surface surface;
	private BufferedImage surfaceTexture;
	private int boundX, boundY, width, height;
	private RoomView nextRoom = null;
	@Override
	public void paintSurface(Graphics g) {
		//draw back rooms first
		if (nextRoom != null){
			nextRoom.paintRoom(g);
		}
		//draw this rooms back wall
		
		//g.drawImage(surfaceTexture, boundX, boundY, width, height, null);

		//TODO draw objects on the wall
	}

	@Override
	public Object click(int x, int y) {
		//TODO search floor for items
		return null;
	}

	@Override
	public void setParentSurface(Surface surface) {
		this.surface = surface;
		try {
			//set image for the view
			surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + surface.roomView.room.getWall() + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set bounds for the surface
		width = surface.roomView.width / 2;
		boundX = surface.roomView.boundX + (width / 2);
		height = surface.roomView.height / 2;
		boundY = surface.roomView.boundY + (height /2);
		
		int depth = surface.roomView.depth;
		if (depth < surface.roomView.viewDepth){
			//TODO get next rooms from actual room
			nextRoom = new RoomView(new DEMOROOM(), boundX, boundY, width, height, depth + 1);
		}
			
	}

}
