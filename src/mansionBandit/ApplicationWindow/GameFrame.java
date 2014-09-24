package mansionBandit.ApplicationWindow;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mansionBandit.gameView.GamePanel;
import mansionBandit.gameWorld.main.Main;
import mansionBandit.gameWorld.main.Player;

public class GameFrame extends JFrame implements ActionListener, MouseListener,
		WindowListener, KeyListener {

	//PLACEHOLDER represents main logic class
	private ApplicationMain game;
	
	//The main drawing class
	GameCanvas canvas = new GameCanvas();
	
	// window dimensions
	private int windowDimensionX = 1024;
	private int windowDimensionY = 768;

	// whether the player is currently dragging an item with the mouse
	private Item draggingItem;

	// description text of the thing that the player is examining
	private String descriptionText;

	// whether or not the ingame menu is up
	private boolean ingameMenuActive = false;
	
	// the panel used for displaying the in game menu
	private JPanel ingameMenuPanel;
	
	//the panel used for displaying game graphics
	private GamePanel gamePanel;

	// the cursor image used for when the player drags items
	Cursor itemImageCursor;
	
	//The main menu panel that is used as the main menu and contains the buttons and navigation
	private MainMenuPanel mainMenu;
	
	//if gameplay has started. The game only checks for mouse and keyboard user input if the game has started
	private boolean gameStarted = false;
	
	//the player this window is used by/applies to
	private Player player;
	
	//the main class of the game
	private Main gameWorld;
	
	public GameFrame(ApplicationMain main) {
		super();

		game = main;
		
		//creates the main menu
		mainMenu = new MainMenuPanel(this);
		
		// canvas = new GameCanvas(this); // create canvas
		setLayout(new BorderLayout()); // use border layour
		// add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// set frame not to close
														// when "x" button
														// clicked.
		addWindowListener(this);

		// create a canvas/draw object here and add it

		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true);

		// listens for mouse input
		addMouseListener(this);

		// listens for keyboard input
		addKeyListener(this);
		
		
		//sets up main menu interface 
		enterMainMenu();
		
		
		//sets up user interface elements
		setupInterface();

	}

	public Dimension getPreferredSize() {
		return new Dimension(windowDimensionX, windowDimensionY);
	}

	/**
	 * Sets up GUI elements
	 */
	private void setupInterface() {

		// this panel is for the part of the UI that presents information about
		// the player such as inventory
		JPanel playerStatusPanel = new JPanel();

		// check that the window is not already active
		// sets up a new frame
		ingameMenuPanel = new JPanel();

		// creates the exit button
		JButton menuExitButton = new JButton("Exit");
		menuExitButton.addActionListener(this);
		menuExitButton.setActionCommand("menuExitGameBtn");
		ingameMenuPanel.add(menuExitButton);

		// makes sure focus is kept on the main window
		menuExitButton.setFocusable(false);

		
		// creates the exit button
		JButton menuExitToMenuButton = new JButton("Main Menu");
		menuExitToMenuButton.addActionListener(this);
		menuExitToMenuButton.setActionCommand("menuExitToMenuBtn");
		ingameMenuPanel.add(menuExitToMenuButton);

		// makes sure focus is kept on the main window
		menuExitToMenuButton.setFocusable(false);
		
		// creates the help button
		JButton menuHelpButton = new JButton("How to play");
		menuHelpButton.addActionListener(this);
		menuHelpButton.setActionCommand("menuHelpBtn");
		ingameMenuPanel.add(menuHelpButton);

		// makes sure focus is kept on the main window
		menuHelpButton.setFocusable(false);

		// creates the help button
		JButton menuResumeButton = new JButton("Resume");
		menuResumeButton.addActionListener(this);
		menuResumeButton.setActionCommand("menuResumeBtn");
		ingameMenuPanel.add(menuResumeButton);

		// makes sure focus is kept on the main window
		menuResumeButton.setFocusable(false);

		ingameMenuPanel.setVisible(false);
		setLayout(new BorderLayout());

		// adds the panel to the center of the screen
		this.add(ingameMenuPanel, BorderLayout.NORTH);
		pack();
	}

	// MENUS AND FRAME INTERACTION//
	
	
	/**
	 * ends the current game by resetting values, disconnecting and removing GUI components
	 */
	private void endGame(){
		//this.removeAll();
		
		//resets all game values and discards the game object
		
		//also hides canvas, GUI components 
		
		closeIngameMenu();
		gamePanel.setVisible(false);
		gameStarted = false;
	}
	
	/**
	 * Goes to the main menu by adding it to this frame
	 */
	private void enterMainMenu(){
		
		//adds the main menu
		this.add(mainMenu, BorderLayout.NORTH);
		
		mainMenu.setVisible(true);
		//this.pack();
		mainMenu.revalidate();
		mainMenu.repaint();
		this.revalidate();
		this.repaint();
	}
	
	
	/**
	 * brings up the in game menu if it is not already active, creating a small
	 * window which allows the user to select the help, settings and exit
	 * options
	 */
	private void showIngameMenu() {
		
		ingameMenuPanel.setVisible(true);
		ingameMenuActive = true;

	}

	/**
	 * closes the in game menu if the in game menu is active
	 */
	private void closeIngameMenu() {
		// if the menu window is up
		if (ingameMenuActive) {

			// closes the window
			// ingameMenuPanel.dispose();
			ingameMenuPanel.setVisible(false);

			// indicates that it is no longer up
			ingameMenuActive = false;

		}
	}
	
	
	/**
	 * Exits the main menu and begins gameplay
	 */
	public void startGame(){
	
	//remove the main menu 
	this.remove(mainMenu);
	
	//create the game that the ApplicationWindow will interact with
	gameWorld = new Main();
	
	//creates player that this applicationWindow applies to
	//TODO make create player
	//player = gameWorld.createPlayer(this);
	
	//add the rendering panel
	gamePanel = new GamePanel();
	this.add(gamePanel);
	
	//redisplay the screen
	this.revalidate();
	this.repaint();
	this.pack();
	
	//indicate that gameplay has started
	gameStarted = true;
	}
	

	/**
	 * Exits the game completely. Asks user for confirmation before exiting.
	 */
	public void exitGame() {

		// Ask the user to confirm they wanted to do this
		int r = JOptionPane.showConfirmDialog(this, new JLabel(
				"Are you sure you want to exit the game?"), "Confirm Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (r == JOptionPane.YES_OPTION) {
			System.exit(0);
		}

	}

	@Override
	public void actionPerformed(ActionEvent act) {

		// if the user presses exit game in the in-game menu
		if (act.getActionCommand().equals("menuExitGameBtn")) {
			exitGame();
		}

		// if the user presses help in the in-game menu
		if (act.getActionCommand().equals("menuHelpBtn")) {

		}

		// if the user presses resume in the in-game menu
		if (act.getActionCommand().equals("menuResumeBtn")) {
			closeIngameMenu();
		}
		
		// if the user presses exit to menu it ends the current game and returns to the main menu
		if (act.getActionCommand().equals("menuExitToMenuBtn")) {
			
			endGame();
			enterMainMenu();
		}
	}

	// MOUSE INTERACTION//

	@Override
	public void mouseReleased(MouseEvent e) {
		
		//only checks for user input if gameplay has started
		
		if(gameStarted){
		
		// left button released
		if (e.getButton() == MouseEvent.BUTTON1) {

			// Check if the player is dragging something

			if (draggingItem != null) {

				// see if there is an object or enemy at this position and
				// whether it can be used on it
				if (canvas.getItemAt(e.getPoint()) != null) {

					// check if the item being dragged can be used on the
					// targeted item
					if (game.useItemOnItem(game.getObjectAt(e.getPoint()))) {

						// if it was used successfully, the item will have been
						// used and is deleted and the item is removed from
						// inventory
						draggingItem = null;

						// set the cursor back to default
						e.getComponent()
								.setCursor(
										Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					} else {

						// if the interaction was invalid the item is returned
						// to the players inventory
						game.addItemToInventory(draggingItem);

						// remove the dragging item
						draggingItem = null;

						// set the cursor back to default
						e.getComponent()
								.setCursor(
										Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}

				// check if mouse is at an empty slot in inventory
				else if (game.atEmptyInventorySlot(e.getPoint())) {

					// if its at this spot then stop dragging and add it to the
					// inventory
					game.addToInventory(draggingItem);
					draggingItem = null;

					// set the cursor back to default
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} else {

					// otherwise just drop the object at this position
					game.dropItem(e.getPoint());
					draggingItem = null;

					// set the cursor back to default
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}

			}
			// if the player isn't dragging an item
			else {

				// check if there is an object/creature/item at mouse position

				if (canvas.getItemAt(e.getPoint()) != null) {

					// change the description text to the items description
					descriptionText = game.getDescription(game.getInstanceAt(e
							.getPoint()));
				}
			}
		}

		// right button released
		if (e.getButton() == MouseEvent.BUTTON3) {

		}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		//only checks for user input if the game has started
		
		if(gameStarted){
		
		// firstly check that the player isn't already holding something
		if (draggingItem != null) {
			// check if there is an item that can be dragged at the current
			// mouse position
			if (canvas.getItemAt(e.getPoint()) instanceof DraggablePlaceHolder) {

				// begin dragging the item at mouse position
				draggingItem = canvas.getItemAt(e.getPoint());

				// SET CURSOR TO ITEM HERE //
				//create a cursor with item as image
				itemImageCursor = Toolkit.getDefaultToolkit()
						.createCustomCursor(draggingItem.getImage(),
								e.getPoint(), "itemCursor");
				
				//set the cursor to be this custom cursor
				e.getComponent().setCursor(itemImageCursor);
			}
			// check if they selected an item in an inventory slot instead
			else if (game.getInventoryItem(e.getPoint()) != null) {

				Item inventoryItem = game.getInventoryItem(e.getPoint());

				// remove the item at the selected position from the players
				// inventory
				game.removeInventoryItem(e.getPoint());

				// set the removed item as the dragged item
				draggingItem = inventoryItem;

				// SET CURSOR TO ITEM HERE //
				//create a cursor with item as image
				itemImageCursor = Toolkit.getDefaultToolkit()
						.createCustomCursor(draggingItem.getImage(),
								e.getPoint(), "itemCursor");
				
				//set the cursor to be this custom cursor
				e.getComponent().setCursor(itemImageCursor);
			}
		}
		}
	}

	// These mouse events are required for the MouseListener interface but are not used
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}

	// WINDOW INTERACTION//

	/**
	 * This method is called when the user clicks on the "X" button in the
	 * right-hand corner.
	 * 
	 * @param e
	 */
	@Override
	public void windowClosing(WindowEvent arg0) {
		// exits game
		exitGame();
	}

	//these are required for the WindowEvent listener interface but are not used
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

	// KEYBOARD INTERACTION//

	@Override
	public void keyReleased(KeyEvent e) {
		//only checks for user input if the game has started
		
		if(gameStarted){
		
		System.out.println("key released: "
				+ KeyEvent.getKeyText(e.getKeyCode()));

		// if the user presses W
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {

			// attempt to move the player forwards
			game.moveForward();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {

			// turn the player right
			game.turnRight();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {

			// turn the player left
			game.turnLeft();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Escape")) {
			// if the in game menu is up, close it
			if (ingameMenuActive) {
				closeIngameMenu();
			} else {
				// otherwise bring up the ingame menu
				showIngameMenu();
			}
		}
		}

	}

	// These methods are unused but required for the KeyListener interface
	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * @return the item being dragged by the player
	 */
	public Item getDraggingItem() {
		return draggingItem;
	}

}
