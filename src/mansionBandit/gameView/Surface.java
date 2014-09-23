package mansionBandit.gameView;

import java.awt.Graphics;

public class Surface {
	private int boundX, boundY, width, height;
	
	public Surface(int boundX, int boundY, int width, int height){
		//TODO move bounds into strategy??
		this.boundX = boundX;
		this.boundY = boundY;
		this.width = width;
		this.height = height;
	}
	
	public void paint(Graphics g){
		
	}
	
	public Object findObject(int x, int y){
		if (x < boundX || x > boundX + width || y < boundY || y > boundY + height){
			return null;
		}
		//TODO look through objects on surface to find object on top, under coords
		return null;
	}
}
