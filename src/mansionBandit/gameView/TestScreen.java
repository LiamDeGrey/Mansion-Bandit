package mansionBandit.gameView;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class TestScreen extends JFrame implements KeyListener{
	GamePanel gamePanel;
	
	public static void main(String[] args){
		new TestScreen();
	}
	
	public TestScreen(){
		super();
        setLayout(new BorderLayout());
        setSize(800, 600);
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);
        addKeyListener(this);
        setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		gamePanel.demo.roomDEMO.setDirection(gamePanel.demo.roomDEMO.getLeft(gamePanel.demo.roomDEMO.getDirection()));
		gamePanel.repaint();
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
