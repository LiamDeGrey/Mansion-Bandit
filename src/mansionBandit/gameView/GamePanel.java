package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.FurnitureStatic;
import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.GameMatter;
import mansionBandit.gameWorld.matter.Guard;

/**
 * This panel is responsible for displaying the game world
 * It takes a Player as an argument, and renders the room that player is in.
 * Also handles mouse clicks, so that if the user clicks an object in the game,
 * the GamePanel will return that object
 * @author Andy
 *
 */
public class GamePanel extends JPanel{
	private int height = 600;
	private int width = 800;
	private Player player;

	public RoomView roomView;

	/**
	 * returns the object the user clicked on
	 * x and y should be given as relative to this panel, not the frame it is contained in
	 * NOTE: only works in the room the player is in
	 * and currently only working for the floor and back wall
	 *
	 * @param x position to check
	 * @param y position to check
	 * @return the GameMatter object, whatever that may be, or null if non found
	 */
	public GameMatter getObject(int x, int y){
		return roomView.findObject(x, y);
	}

	/**
	 * creates a gamePanel using a Player object to determine the direction faced, and location
	 * @param p
	 */
	public GamePanel(Player p){
		player = p;
		//create the initial RoomView
		roomView = new RoomView(p.getBandit().getName(), player.getBandit().getArea(), player.getBandit().getFace(), 0, 0, width, height, 0);
	}

	/**
	 * calls upon the GamePanel to redraw the scene (should be called when something
	 * in the players view has changed (object added/removed from room, player turned or moved)
	 * NOTE: currently is unoptimised. This will redraw the entire scene from scratch
	 */
	public void update(){
		MansionArea previousRoom = roomView.room;
		roomView = new RoomView(player.getBandit().getName(), player.getBandit().getArea(), player.getBandit().getFace(), 0, 0, width, height, 0);
		if (!previousRoom.equals(player.getBandit().getArea())){
			//in a new room, check for a Guard
			for (GameMatter item : roomView.room.getItems()){
				if (item instanceof Guard){
					//found a guard in the room, set timer
					Thread check = new Thread(){
						@Override
						public void run(){
							try {
								this.sleep(2700);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							update();

						}
					};
					check.start();
				}
			}
		}

		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setClip(0, 0, width, height);
		super.paintComponent(g);
		roomView.paintRoom(g);
	}
}
