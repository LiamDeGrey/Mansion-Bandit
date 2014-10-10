package mansionBandit.gameView;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import mansionBandit.factory.RoomFactory;
import mansionBandit.gameWorld.areas.*;
import mansionBandit.gameWorld.main.Host;
import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;
import mansionBandit.gameWorld.matter.Sellable;

/**
 * my own frame class that I can use as a sandbox to work on new features
 * 
 * @author Andy
 *
 */
public class TestScreen extends JFrame implements KeyListener{
	GamePanel gamePanel;
	Player p;

	public static void main(String[] args){
		new TestScreen();
	}

	public TestScreen(){
		super();
        setLayout(new BorderLayout());
        setSize(800, 600);

        //create mock player objects
        p = new Host("test player", 1);

        //currently this is a test integration of objects
        Room demoRoom = makeRoom();
        
        //           X
        //    [0][1][2][3][4]
        // [0] _  R  H  R  _
        //Y[1] R  H  H  H  R
        // [2] _  R  R  H  R
        //		[y][x]
        
        MansionArea[][] grid = new MansionArea[3][5];
        //rooms
        Room room1 = new Room();
        Room room2 = new Room();
        
        grid[1][0] = room2;
        grid[0][1] = room1;
        grid[0][2] = new Hallway();
        grid[0][3] = room2;
        grid[2][1] = room1;
        grid[2][2] = room2;
        grid[2][3] = new Hallway();
        grid[1][4] = room1;
        grid[2][4] = room2;
        
        //halways
        grid[1][1] = new Hallway();
        grid[1][2] = new Hallway();
        grid[1][3] = new Hallway();
        
        grid[1][1].setLinks(grid[0][1], grid[1][2], grid[2][1], grid[1][0]); //[1][1]
        grid[1][2].setLinks(grid[0][2], grid[1][3], grid[2][2], grid[1][1]); //[1][2]
        grid[1][3].setLinks(grid[0][3], grid[1][4], grid[2][3], grid[1][2]); //[1][3]
       

        grid[0][2].setLinks(null, grid[0][3], grid[1][2], grid[0][1]); //[0][2]
        grid[2][3].setLinks(grid[1][3], grid[2][4], null, grid[2][2]); //[2][3]
        p.getBandit().setArea(grid[1][1]);
//        p.getBandit().setArea(demoRoom);
        
        RoomFactory factory = new RoomFactory();
        //pop halls
        factory.populateRoom(grid[1][1]);
        factory.populateRoom(grid[1][2]);
        factory.populateRoom(grid[1][3]);
        factory.populateRoom(grid[0][2]);
        factory.populateRoom(grid[2][3]);
        
        //pop rooms
        factory.populateRoom(grid[0][1]);
        factory.populateRoom(grid[0][3]);
        
        factory.populateRoom(grid[1][0]);
        factory.populateRoom(grid[1][4]);
        
        factory.populateRoom(grid[2][1]);
        factory.populateRoom(grid[2][2]);
        factory.populateRoom(grid[2][4]);

        p.getBandit().setFace(Face.EASTERN);

        gamePanel = new GamePanel(p);

        add(gamePanel, BorderLayout.CENTER);
        addKeyListener(this);
        setVisible(true);
	}

	public Room makeRoom(){
		//currently this is a test integration of objects
        Room demoRoom = new Room("wall1", "ceiling1", "carpet1");
        //demo object to be placed on all sides
        demoRoom.addItem(makeDeco(Face.FLOOR));
        demoRoom.addItem(makeDeco(Face.EASTERN));
        demoRoom.addItem(makeDeco(Face.NORTHERN));
        demoRoom.addItem(makeDeco(Face.SOUTHERN));
        demoRoom.addItem(makeDeco(Face.CEILING));
        demoRoom.addItem(makeDeco(Face.WESTERN));

        return demoRoom;
	}

	private GameMatter makeDeco(Face face){
		int size = 20;
		int x = (int) ((100 - size) * Math.random()) + (size / 2);
		int y = (int) ((100 - size) * Math.random()) + size;

		return new Sellable("test!", "testing testing...", "testFace", face, new Dimensions(x, y, size), 5);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		//p.turnLeft();
		//gamePanel.update();
	}

//	@Override
//	public void keyReleased(KeyEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		//only checks for user input if the game has started

		// if the user presses W
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {

			// attempt to move the player forwards
			p.moveForward();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {

			// turn the player right
			p.turnRight();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {

			// turn the player left
			p.turnLeft();
		}
		
		gamePanel.update();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
