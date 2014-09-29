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
	private int surfaceX, surfaceY, surfaceWidth, surfaceHeight;
	private RoomView nextRoom = null;
	private static String fog = "/walls/fog.png";
	@Override
	public void paintSurface(Graphics g) {
		//draw back rooms first
		if (nextRoom != null){
			nextRoom.paintRoom(g);
		} else {
			//no room to draw so draw fog
			try {
				g.drawImage(ImageIO.read(this.getClass().getResource(fog)), surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//draw this rooms back wall
		g.drawImage(surfaceTexture, surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);
		
		//draw objects on the wall
		for (DrawnObject ob : surface.objects){
			BufferedImage obImage = null;
			try {
				obImage = ImageIO.read(this.getClass().getResource("/object/" + ob.getGameObject().getFace() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(obImage, ob.getBoundX(), ob.getBoundY(), ob.getWidth(), ob.getHeight(), null);
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
			surfaceTexture = ImageIO.read(this.getClass().getResource("/walls/" + surface.roomView.roomDEMO.getWall() + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set bounds for the surface
		surfaceWidth = surface.roomView.width / 2;
		surfaceX = surface.roomView.boundX + (surfaceWidth / 2);
		surfaceHeight = surface.roomView.height / 2;
		surfaceY = surface.roomView.boundY + (surfaceHeight /2);
		
		int depth = surface.roomView.depth;
		if (depth < surface.roomView.viewDepthMAX){
			//TODO get next rooms from actual room
			nextRoom = new RoomView(new DEMOROOM(), surfaceX, surfaceY, surfaceWidth, surfaceHeight, depth + 1);
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
		List<DrawnObject> obs = new ArrayList<DrawnObject>();
		//loop through objects on wall, and resize them
		for (DEMOOBJECT ob : wall.getObjects()){
			int size = (int) ((((double) ob.getSize()) / 100) * surfaceHeight);
			int left = (int) (surfaceX + (ob.getX() * ((double) surfaceWidth / 100)) - (size / 2));
			int top = (int) (surfaceY + (ob.getY() * ((double) surfaceHeight / 100)) - size);
			System.out.print("origX= " + ob.getX() + ", left = " + left +
					", origY= " + ob.getY() + ", top = " + top +
					", width = " + size + ", height = " + size + 
					", SurfaceTop= " + surfaceY + ", surfheight= " + surfaceHeight + "\n");
			DrawnObject dob = new DrawnObject(ob, left, top, size, size);
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
