package mansionBandit.ApplicationWindow;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InGameMenuPanel extends JPanel{

	GameFrame gameFrame;
	
	public InGameMenuPanel(GameFrame gameframe){
		gameFrame = gameframe;
		GridLayout gridLayout = new GridLayout(0,1,10,10);
		// this panel is for the part of the UI that presents information about
		// the player such as inventory
		this.setLayout(gridLayout);

		// check that the window is not already active
		// sets up a new frame

		// creates the resume button
		JButton menuResumeButton = new JButton("Resume");
		menuResumeButton.addActionListener(gameFrame);
		menuResumeButton.setActionCommand("menuResumeBtn");
		this.add(menuResumeButton);

		// makes sure focus is kept on the main window
		menuResumeButton.setFocusable(false);


		// creates the help button
		JButton menuHelpButton = new JButton("How to play");
		menuHelpButton.addActionListener(gameFrame);
		menuHelpButton.setActionCommand("menuHelpBtn");
		this.add(menuHelpButton);

		// makes sure focus is kept on the main window
		menuHelpButton.setFocusable(false);


		// creates the exit to menu button
		JButton menuExitToMenuButton = new JButton("Main Menu");
		menuExitToMenuButton.addActionListener(gameFrame);
		menuExitToMenuButton.setActionCommand("menuExitToMenuBtn");
		this.add(menuExitToMenuButton);

		// makes sure focus is kept on the main window
		menuExitToMenuButton.setFocusable(false);


		// creates the exit button
		JButton menuExitButton = new JButton("Exit");
		menuExitButton.addActionListener(gameFrame);
		menuExitButton.setActionCommand("menuExitGameBtn");
		this.add(menuExitButton);

		// makes sure focus is kept on the main window
		menuExitButton.setFocusable(false);


		this.setVisible(false);
		

		this.setOpaque(true);

	}
	}
	

