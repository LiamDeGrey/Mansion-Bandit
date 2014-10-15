package mansionBandit.ApplicationWindow;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * displays the score of all players
 * @author carrtheo
 *
 */
public class ScoreScreenPanel extends JPanel {

	JLabel titleLabel = new JLabel("Score");
	JLabel player1Label = new JLabel("Player 1:");
	JLabel player2Label = new JLabel("Player 2:");
	JLabel player3Label = new JLabel("Player 3:");
	JLabel player4Label = new JLabel("Player 4:");

	GameFrame gameframe;

	public ScoreScreenPanel(GameFrame gameFrame){
		super();

		gameframe = gameFrame;

		this.setBackground(Color.black);

		this.setLayout(null);



		this.add(player1Label);
		this.add(player2Label);
		this.add(player3Label);
		this.add(player4Label);

		player1Label.setVisible(true);
		player2Label.setVisible(true);
		player3Label.setVisible(true);
		player4Label.setVisible(true);

		player1Label.setBounds(0,0,200,200);
		//player2Label.setBounds();
	//	player3Label.setBounds();
		//player4Label.setBounds();


	}


	/**
	 * updates the text displayed in the score screen
	 * @param playerInfo
	 */
	public void updateScoreInfo(ArrayList<String> playerInfo){

		player1Label.setText(playerInfo.get(0));
		player2Label.setText(playerInfo.get(1));
		player3Label.setText(playerInfo.get(2));
		player4Label.setText(playerInfo.get(3));

	}


}
