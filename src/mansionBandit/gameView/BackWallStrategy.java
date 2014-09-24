package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * strategy for drawing items on back wall
 * @author Andy
 *
 */
public class BackWallStrategy implements SurfaceStrategy {
	private Surface surface;
	private BufferedImage surfaceTexture;
	private int boundX, boundY, width, height;
	private RoomView nextRoom = null;
	private static String fog = "/walls/fog.png";
	@Override
	public void paintSurface(Graphics g) {
		//draw back rooms first
		if (nextRoom != null){
			//nextRoom.paintRoom(g);
		} else {
			//no room to draw so draw fog
			try {
				g.drawImage(ImageIO.read(this.getClass().getResource(fog)), boundX, boundY, width, height, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		//draw this rooms back wall
		
		g.drawImage(surfaceTexture, boundX, boundY, width, height, null);

		//draw objects on the wall
		for (DrawnObject ob : surface.objects){
			BufferedImage obImage = null;
			try {
				obImage = ImageIO.read(this.getClass().getResource("/object/" + ob.getGameObject().getFace() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//g.drawImage(obImage, ob.getBoundX(), ob.getBoundY(), ob.getWidth(), ob.getHeight(), null);
		}
	}

	@Override
	public Object click(int x, int y) {
		//TODO search floor for items
		return null;
	}

	@Override
	public void setupSurface(Surface surface, DEMOWALL wall) {
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
		
		//create object list for surface
		createGameObjects(wall);
			
	}
	
	/**
	 * wraps objects to be drawn into DrawnObjects
	 * with appropriate size distortion
	 * 
	 * @param wall
	 */
	private void createGameObjects(DEMOWALL wall){
		//TODO remove hardcoding
		List<DrawnObject> obs = new ArrayList<DrawnObject>();
		//loop through objects on wall, and resize them
		for (DEMOOBJECT ob : wall.getObjects()){
			int w = 100;
			int h = w;
			int left = boundX + (ob.getX() * (width / 100)) - (w / 2);
			int top = boundY + (ob.getY() * (height / 100)) - h;
			DrawnObject dob = new DrawnObject(ob, left, top, w, h);
			obs.add(dob);
		}
		surface.objects = obs;
	}
	
	/**
	 * orders objects according to those that should be drawn first
	 * 
	 * @param wall
	 */
	private void arrangeObjects(DEMOWALL wall){
		
	}

}
