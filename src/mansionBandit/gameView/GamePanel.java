package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Couch;
import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Dimensions;

public class GamePanel extends JPanel{
	int height = 600;
	int width = 800;

	//TODO get rid of demo
	public RoomView demo;

	public GamePanel(){
		//TODO fully integrate
		//currently this is a test integration of objects
		Room demoRoom = new Room();
		//demo object to be placed on all sides
		demoRoom.addItem(makeDeco(Face.FLOOR));
		demoRoom.addItem(makeDeco(Face.EASTERN));
		demoRoom.addItem(makeDeco(Face.NORTHERN));
		demoRoom.addItem(makeDeco(Face.SOUTHERN));
		demoRoom.addItem(makeDeco(Face.TOP));
		demoRoom.addItem(makeDeco(Face.WESTERN));
		demo = new RoomView(new DEMOROOM(), 0, 0, width, height, 0);
		//demo = new RoomView(demoRoom, 0, 0, width, height, 0);
	}

	//TODO remove
	private Decoration makeDeco(Face face){
		int size = 20;
		int x = (int) ((100 - size) * Math.random()) + (size / 2);
		int y = (int) ((100 - size) * Math.random()) + size;
		return new Decoration("decor", face, new Dimensions(x, y));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		demo.update();
		demo.paintRoom(g);
	}
}
