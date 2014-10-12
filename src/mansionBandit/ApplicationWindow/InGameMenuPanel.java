package mansionBandit.ApplicationWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InGameMenuPanel extends JPanel{

	GameFrame gameFrame;

	/**
	 * an ingame menu that is displayed when the user presses esc while the game is running
	 * @param gameframe that this menu is added to
	 * @author theo
	 */
	public InGameMenuPanel(GameFrame gameframe){
		gameFrame = gameframe;
		GridLayout gridLayout = new GridLayout(0,1,10,0);
		// this panel is for the part of the UI that presents information about
		// the player such as inventory
		this.setLayout(gridLayout);

		// check that the window is not already active
		// sets up a new frame

		// creates the resume button
		JButton menuResumeButton = new JButton("Resume");
		menuResumeButton.addActionListener(gameFrame);
		menuResumeButton.setActionCommand("menuResumeBtn");
		menuResumeButton.setBackground(Color.black);
		menuResumeButton.setForeground(Color.white);
		menuResumeButton.setFont(new Font("Courier New", 15, 15));
		this.add(menuResumeButton);

		// makes sure focus is kept on the main window
		menuResumeButton.setFocusable(false);


		// creates the help button
		JButton menuHelpButton = new JButton("How to play");
		menuHelpButton.addActionListener(gameFrame);
		menuHelpButton.setActionCommand("menuHelpBtn");
		menuHelpButton.setBackground(Color.black);
		menuHelpButton.setForeground(Color.white);
		menuHelpButton.setFont(new Font("Courier New", 15, 15));
		this.add(menuHelpButton);

		// makes sure focus is kept on the main window
		menuHelpButton.setFocusable(false);


		// creates the exit to menu button
		JButton menuExitToMenuButton = new JButton("Main Menu");
		menuExitToMenuButton.addActionListener(gameFrame);
		menuExitToMenuButton.setActionCommand("menuExitToMenuBtn");
		menuExitToMenuButton.setBackground(Color.black);
		menuExitToMenuButton.setForeground(Color.white);
		menuExitToMenuButton.setFont(new Font("Courier New", 15, 15));
		this.add(menuExitToMenuButton);

		// makes sure focus is kept on the main window
		menuExitToMenuButton.setFocusable(false);


		// creates the exit button
		JButton menuExitButton = new JButton("Exit");
		menuExitButton.addActionListener(gameFrame);
		menuExitButton.setActionCommand("menuExitGameBtn");
		menuExitButton.setBackground(Color.black);
		menuExitButton.setForeground(Color.white);
		menuExitButton.setFont(new Font("Courier New", 15, 15));
		this.add(menuExitButton);

		// makes sure focus is kept on the main window
		menuExitButton.setFocusable(false);


		this.setVisible(false);


		this.setOpaque(true);

	}
	}


