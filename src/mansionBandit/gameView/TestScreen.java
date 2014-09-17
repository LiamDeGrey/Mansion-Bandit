package mansionBandit.gameView;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class TestScreen{
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        GamePanel gamePanel = new GamePanel();
        
        frame.add(gamePanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
	}
}
