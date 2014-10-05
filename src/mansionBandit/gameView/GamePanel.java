package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sun.net.www.content.text.plain;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.Couch;
import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.GameMatter;

public class GamePanel extends JPanel{
	private int height = 600;
	private int width = 800;
	private Player player;
	private Face direction;

	public RoomView room;

	/**
	 * returns the object the user clicked on
	 * x and y should be given as relative to this panel, not the frame it is contained in
	 * 
	 * @param x position to check
	 * @param y position to check
	 * @return the GameMatter object, whatever that may be, or null if non found
	 */
	public GameMatter getObject(int x, int y){
		return room.findObjectByMouse(x, y);
	}

	public GamePanel(){
		//TODO fully integrate
		//currently this is a test integration of objects
		Room demoRoom = new Room("wall1", "ceiling1", "carpet1");
		//demo object to be placed on all sides
//		demoRoom.addItem(makeDeco(Face.FLOOR));
//		demoRoom.addItem(makeDeco(Face.EASTERN));
//		demoRoom.addItem(makeDeco(Face.NORTHERN));
//		demoRoom.addItem(makeDeco(Face.SOUTHERN));
//		demoRoom.addItem(makeDeco(Face.CEILING));
//		demoRoom.addItem(makeDeco(Face.WESTERN));
		room = new RoomView(demoRoom, 0, 0, width, height, 0);
	}

	public GamePanel(Player p){
		player = p;
		direction = player.getBandit().getFace();
		
		room = new RoomView(player.getBandit().getArea(), 0, 0, width, height, 0);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		room.update();
		room.paintRoom(g);
	}
}
