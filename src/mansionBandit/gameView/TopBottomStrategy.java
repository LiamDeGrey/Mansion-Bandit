package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		if (top){
			return null;
		}
		//TODO search floor for items
		return null;
	}

	@Override
	public void setupSurface(Surface surface, DEMOWALL wall) {
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
		//loop through objects on floor, and resize them
		for (DEMOOBJECT ob : wall.getObjects()){
			//determine x and y based on direction in room
			int x = getX(ob);
			int y = getY(ob);
			
			//===determine width and height based on distance from back wall===
			
			//this is the level of scaling to apply 
			double scale = ((double) y) / 100;
			if (this.top){
				scale = 1 - scale;
			}
			
			int h = (int) (100 * (scale)) + 100;
			int w = h;
			
			//determine center x
			int left = boundX + (x * (width / 100));
			//have to alter x position to be closer to center when further back
			int center = boundX + (width / 2);
			int diff = Math.abs(center - left);
			//apply scaling
			diff = (diff / 2);
			diff = (int) (diff + (diff * scale));
			if (left < center){
				left = center - diff - (w / 2);
			} else if (center < left){
				left = center + diff - (w / 2);
			}
			
			//TODO change variable names so that we're not relying on scope?
			int top = boundY + (y * (height / 100));
			if (!this.top){
				top -= h;
			}
			System.out.print("top = " + this.top + ", origX= " + ob.getX() + ", newX = " + getX(ob) + ", left = " + left +
					", origY= " + ob.getY() + ", newY = " + getY(ob) + ", top = " + top +
					", width = " + w + ", height = " + h + "\n");
			DrawnObject dob = new DrawnObject(ob, left, top, w, h);
			obs.add(dob);
		}
		surface.objects = obs;
	}
	
	private int getX(DEMOOBJECT ob){
		if (surface.roomView.direction == DEMOROOM.E){
			return ob.getY();
		}
		if (surface.roomView.direction == DEMOROOM.S){
			return 100 - ob.getX();
		}
		if (surface.roomView.direction == DEMOROOM.W){
			return 100 - ob.getY();
		}
		//must be facing north
		return ob.getX();
	}
	
	private int getY(DEMOOBJECT ob){
		if (surface.roomView.direction == DEMOROOM.E){
			return 100 - ob.getX();
		}
		if (surface.roomView.direction == DEMOROOM.S){
			return 100 - ob.getY();
		}
		if (surface.roomView.direction == DEMOROOM.W){
			return ob.getX();
		}
		//must be facing north
		return ob.getY();
	}
	
	/**
	 * orders objects according to those that should be drawn first
	 * 
	 * @param wall
	 */
	private void arrangeObjects(DEMOWALL wall){
		
	}

}
