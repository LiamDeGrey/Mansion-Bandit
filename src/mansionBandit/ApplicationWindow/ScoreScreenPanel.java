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


	JLabel player1Label = new JLabel();
	JLabel player2Label = new JLabel();
	JLabel player3Label = new JLabel();
	JLabel player4Label = new JLabel();

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
