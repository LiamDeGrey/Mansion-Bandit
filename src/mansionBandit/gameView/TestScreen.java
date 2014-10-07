package mansionBandit.gameView;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.main.Host;
import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Face;

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

        p.getBandit().setArea(demoRoom);

        p.getBandit().setFace(Face.NORTHERN);

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

	//TODO remove
	private Decoration makeDeco(Face face){
		int size = 20;
		int x = (int) ((100 - size) * Math.random()) + (size / 2);
		int y = (int) ((100 - size) * Math.random()) + size;
		return new Decoration("testFace", face, new Dimensions(x, y, size));
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		p.turnLeft();
		gamePanel.update();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
