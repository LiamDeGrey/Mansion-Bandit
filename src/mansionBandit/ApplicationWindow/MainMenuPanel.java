package mansionBandit.ApplicationWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainMenuPanel extends JPanel implements ActionListener{
	 
	//initialize panels for the menus
	JPanel startMenuPanel = new JPanel();
	JPanel hostLobbyMenuPanel = new JPanel();
	JPanel slaveLobbyMenuPanel = new JPanel();
	JPanel multiplayerMenuPanel = new JPanel();
	JPanel hostGamePanel = new JPanel();
	JPanel connectToGamePanel = new JPanel();
	
	public MainMenuPanel(GameFrame gameFrame){
		 //creates the buttons for the panel
		
		//Sets up all of buttons in the seperate JPanels/Sub menus
		//this just creates them so they
		setupStartMenu();
		setupSlaveLobbyMenu();
		setupHostLobbyMenu();
		setupMultiplayerMenu();
		setupHostGameMenu();
		setupConnectToGameMenu();
		
		
		
		//Sets the menu to the main menu initally
		setMenu(startMenuPanel);
		
		
		
	 }
	
	
	
	/**
	 * sets up the buttons for the first screen of the main menu
	 */
	private void setupStartMenu(){
		//set up buttons
		
		// creates the exit button
		JButton playButton = new JButton("Play");
		playButton.addActionListener(this);
		playButton.setActionCommand("startGame");
		startMenuPanel.add(playButton);
		
		
		// creates the multiplayer button
		JButton multiplayerButton = new JButton("Multiplayer");
		multiplayerButton.addActionListener(this);
		multiplayerButton.setActionCommand("enterMultiplayerMenu");
		startMenuPanel.add(multiplayerButton);
				
		
		// creates the tutorial button in the menu
		JButton helpButton = new JButton("Tutorial");
		helpButton.addActionListener(this);
		helpButton.setActionCommand("startTutorial");
		startMenuPanel.add(helpButton);
		
		// creates the exit button
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		exitButton.setActionCommand("exitButton");
		//adds the button to the menu panel
		startMenuPanel.add(exitButton);
		
		
	}
	
	/**
	 * sets up the buttons for the first screen of the main menu
	 */
	private void setupMultiplayerMenu(){
		
		// creates the back button which will take the player back to the start of the main menu
		JButton connectButton = new JButton("Connect to game");
		connectButton.addActionListener(this);
		connectButton.setActionCommand("connectToGameButton");
		
		startMenuPanel.add(connectButton);
		
		// creates the back button which will take the player back to the start of the main menu
		JButton hostGameButton = new JButton("Host Game");
		hostGameButton.addActionListener(this);
		hostGameButton.setActionCommand("hostGameButton");
		
		startMenuPanel.add(hostGameButton);
		
		// creates the disconnect button which will take the player back to the start of the main menu
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setActionCommand("backButton");
		
		startMenuPanel.add(backButton);
		
	}
	
	/**
	 * sets up the buttons for the first screen of the main menu
	 */
	private void setupConnectToGameMenu(){
		
		
		// creates the back button which will take the player back to the start of the main menu
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setActionCommand("backButton");
		
		startMenuPanel.add(backButton);
		
		
		// creates the back button which will take the player back to the start of the main menu
		JButton connectButton = new JButton("Connect");
		backButton.addActionListener(this);
		backButton.setActionCommand("connectButton");
		
		//sets up text box for names of connected players
		JTextField addressTextBox = new JTextField();
		
		//sets up a textbox for the player to input their name
		JTextField nameTextBox = new JTextField();
	}
	
	/**
	 * sets up the buttons for the first screen of the main menu
	 */
	private void setupHostGameMenu(){
		
		
		// creates the back button which will take the player back to the start of the main menu
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setActionCommand("backButton");
		
		startMenuPanel.add(backButton);
		
		
		// creates the back button which will take the player back to the start of the main menu
		JButton startButton = new JButton("Start");
		startButton.addActionListener(this);
		startButton.setActionCommand("setMenuHostLobby");
				
		startMenuPanel.add(startButton);
		
		
		//sets up text box for names of connected players
		JTextField addressTextBox = new JTextField();
		
		
		//sets up a textbox for the player to input their name
		JTextField nameTextBox = new JTextField();
	}
	
	/**
	 * sets up the interface for lobby menu for a slave (non-host) player
	 * this screen displays the users currently connected to the host
	 */
	private void setupSlaveLobbyMenu(){
		
		
		// creates the back button which will take the player back to the start of the main menu
		JButton disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(this);
		disconnectButton.setActionCommand("disconnectButton");
		
		startMenuPanel.add(disconnectButton);
		
				
		//Sets up the labels that will display the names of the players
		JLabel player1NameLabel = new JLabel();
		startMenuPanel.add(player1NameLabel);
		JLabel player2NameLabel = new JLabel();
		startMenuPanel.add(player2NameLabel);
		JLabel player3NameLabel = new JLabel();
		startMenuPanel.add(player3NameLabel);
		JLabel player4NameLabel = new JLabel();
		startMenuPanel.add(player4NameLabel);
		
		
	}
	
	/**
	 * sets up the interface for lobby menu for a host player, allowing them to start the game
	 * this screen displays the users currently connected to the host
	 */
	private void setupHostLobbyMenu(){
		
		
		// creates the back button which will take the player back to the start of the main menu
		JButton disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(this);
		disconnectButton.setActionCommand("disconnectButton");
		
		startMenuPanel.add(disconnectButton);
		
				
				
		//Sets up the labels that will display the names of the players
		JLabel player1NameLabel = new JLabel();
		startMenuPanel.add(player1NameLabel);
		JLabel player2NameLabel = new JLabel();
		startMenuPanel.add(player2NameLabel);
		JLabel player3NameLabel = new JLabel();
		startMenuPanel.add(player3NameLabel);
		JLabel player4NameLabel = new JLabel();
		startMenuPanel.add(player4NameLabel);
		
		
	}
	
	
	

	
	/**
	 * Changes/sets the menu to the one specified. Only one sub-menu will be displayed at a time.
	 * @param the menu that is to be displayed to the user
	 */
	private void setMenu(JPanel submenu){
		
		//removes all current menus/panels that are displayed
		this.removeAll();
		
		//sets the current menu to submenu
		this.add(submenu);
		
		submenu.setVisible(true);
	}
	
	
	/**
	 * quits out of the main menu and starts the game
	 */
	private void startGame(){ 
		this.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("startGame")){
			startGame();
		}
		else if(e.getActionCommand().equals("setMenuMultiplayer")){
			//set the menu to the multiplayer menu
			setMenu(multiplayerMenuPanel);
		}
		else if(e.getActionCommand().equals("setMenuHostLobby")){
			//set the menu to the multiplayer lobby menu as the host
			setMenu(hostLobbyMenuPanel);
		}
		else if(e.getActionCommand().equals("setMenuSlaveLobby")){
			//set the menu to the multiplayer lobby menu as a slave
			setMenu(slaveLobbyMenuPanel);
		}
		else if(e.getActionCommand().equals("setMenuHostGame")){
			//set the menu to the multiplayer host a game menu
			setMenu(hostGamePanel);
		}
		else if(e.getActionCommand().equals("setMenuConnectToGame")){
			//set the menu to the multiplayer connect to game menu
			setMenu(connectToGamePanel);
		}
		else if(e.getActionCommand().equals("exitButton")){
			//exit the game, uses the frame exit game method
			
		}
		else if(e.getActionCommand().equals("backButton")){
			//go back to the main menu
			setMenu(startMenuPanel);
		}
		
		
			
	}
	
	
}
