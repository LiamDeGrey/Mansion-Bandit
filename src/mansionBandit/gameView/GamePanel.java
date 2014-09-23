package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	BufferedImage floor, wallL, wall, wallR, ceiling;
	int height = 600;
	int width = 800;
	RoomView demo;
	
	public GamePanel(){
		try {
			floor = ImageIO.read(this.getClass().getResource("/floors/carpet1.png"));
			wallL = ImageIO.read(this.getClass().getResource("/walls/wall1L.png"));
			wall = ImageIO.read(this.getClass().getResource("/walls/wall1.png"));
			wallR = ImageIO.read(this.getClass().getResource("/walls/wall1R.png"));
			ceiling = ImageIO.read(this.getClass().getResource("/ceilings/ceiling1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		demo = new RoomView(new DEMOROOM(), 0, 0, width, height);
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*draw back wall
		g.drawImage(wall, width/4, height/4, width/2, height/2, null);
		//draw ceiling
		g.drawImage(ceiling, 0, 0, width, height/4, null);
		//draw left wall
		g.drawImage(wallL, 0, 0, width/4, height, null);
		//draw right wall
		g.drawImage(wallR, 3*(width/4), 0, width/4, height, null);
		//draw floor
		g.drawImage(floor, 0, 3*(height/4), width, height/4, null);
		*/
		demo.paintRoom(g);
	}
}
