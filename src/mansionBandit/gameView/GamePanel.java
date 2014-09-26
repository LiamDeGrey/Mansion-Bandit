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
	
	//TODO get rid of demo
	RoomView demo;
	
	public GamePanel(){
		demo = new RoomView(new DEMOROOM(), 0, 0, width, height, 0);
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		demo.update();
		demo.paintRoom(g);
	}
}
