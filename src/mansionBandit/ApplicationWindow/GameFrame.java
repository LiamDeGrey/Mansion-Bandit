package mansionBandit.ApplicationWindow;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mansionBandit.gameView.GamePanel;
import mansionBandit.gameView.TestScreen;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Grid;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.main.Host;
import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.main.Slave;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Grabable;
import mansionBandit.gameWorld.matter.Key;
import mansionBandit.network.Client;
import mansionBandit.network.Server;

/**
 * @author Theo
 *TODO Change all placeholder classes to actual classes. Change all PlayerPlayerHolders to players, change canvas to GamPanel, change item to gamematter item, etc
 */
public class GameFrame extends JFrame implements ActionListener, MouseListener,
		WindowListener, KeyListener {

	//PLACEHOLDER represents main logic class
	//private ApplicationMain game;

	//The main drawing class
	//GameCanvas canvas = new GameCanvas();

	// window dimensions
	private final int windowDimensionX = 1024;
	private final int windowDimensionY = 768;
	
	
	
	private final int ingameMenuX = 325;
	private final int ingameMenuY = 200;
	private final int ingameMenuW = 150;
	private final int ingameMenuH = 150;

	// whether the player is currently dragging an item with the mouse
	private Grabable draggingItem;

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


	//the offset of the mouse Y position, caused by the frame
	private final int mouseOffSetY; 

	//GUI FIELDS//
	//the GUI drawing canvas
	private GUICanvas guiCanvas;

	//the position of the inventory bar
	private final Point inventoryBarPos = new Point(50,250);

	private final int inventorySlotSize = 100;

	//the pane that all components are added to so that they can stack properly
	private JLayeredPane layeredPane;


	public GameFrame(ApplicationMain main) {
		super();

		this.setLayout(null);

		getContentPane().setBackground(Color.BLACK);
		
		//creates the main menu
		mainMenu = new MainMenuPanel(this);


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
	
		mouseOffSetY = getInsets().top;
	}

	public Dimension getPreferredSize() {
		return new Dimension(windowDimensionX, windowDimensionY);
	}

	/**
	 * Sets up GUI elements
	 */
	private void setupInterface() {

		GridLayout gridLayout = new GridLayout(0,1,10,10);
		// this panel is for the part of the UI that presents information about
		// the player such as inventory


		// check that the window is not already active
		// sets up a new frame
		ingameMenuPanel = new JPanel(gridLayout);

		
		// creates the resume button
		JButton menuResumeButton = new JButton("Resume");
		menuResumeButton.addActionListener(this);
		menuResumeButton.setActionCommand("menuResumeBtn");
		ingameMenuPanel.add(menuResumeButton);

		// makes sure focus is kept on the main window
		menuResumeButton.setFocusable(false);
		
		
		// creates the help button
		JButton menuHelpButton = new JButton("How to play");
		menuHelpButton.addActionListener(this);
		menuHelpButton.setActionCommand("menuHelpBtn");
		ingameMenuPanel.add(menuHelpButton);

		// makes sure focus is kept on the main window
		menuHelpButton.setFocusable(false);
		
		
		// creates the exit to menu button
		JButton menuExitToMenuButton = new JButton("Main Menu");
		menuExitToMenuButton.addActionListener(this);
		menuExitToMenuButton.setActionCommand("menuExitToMenuBtn");
		ingameMenuPanel.add(menuExitToMenuButton);

		// makes sure focus is kept on the main window
		menuExitToMenuButton.setFocusable(false);
		
		
		// creates the exit button
		JButton menuExitButton = new JButton("Exit");
		menuExitButton.addActionListener(this);
		menuExitButton.setActionCommand("menuExitGameBtn");
		ingameMenuPanel.add(menuExitButton);

		// makes sure focus is kept on the main window
		menuExitButton.setFocusable(false);


		ingameMenuPanel.setVisible(false);
		//setLayout(new BorderLayout());

		ingameMenuPanel.setOpaque(true);


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

		this.remove(layeredPane);
		closeIngameMenu();
		//gamePanel.setVisible(false);
		gameStarted = false;
	}

	/**
	 * Goes to the main menu by adding it to this frame
	 */
	private void enterMainMenu(){
		  
		
	
		
		//adds the main menu;
		this.add(mainMenu);

		mainMenu.setVisible(true);
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

		//layeredPane.add
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
	 * Exits the main menu and begins gameplay. Adds all panels that are relevant to gameplay to the frame.
	 */
	public void startGame(){

	//remove the main menu
	this.remove(mainMenu);



	//creates player that this applicationWindow applies to
	//TODO make create player
	//player = gameWorld.createPlayer(this);

	//TODO Make proper create player method

	Grid r = new Grid(1);
	player = new Host("", 20);


	//TODO PLACEHOLDER get rid of
	player.getBandit().setArea(makeRoom());
	player.getBandit().setFace(Face.NORTHERN);

	layeredPane = new JLayeredPane();


	this.add(layeredPane);


	//set the size and location of the layeredPane
	layeredPane.setBounds(0,0,windowDimensionX,windowDimensionY);

	//add in game menu
	ingameMenuPanel.setBounds(ingameMenuX,ingameMenuY,ingameMenuW,ingameMenuH);
	ingameMenuPanel.setOpaque(false);
	
	//gives it a black background
	ingameMenuPanel.setBackground(Color.black);
	ingameMenuPanel.setOpaque(true);
	
	//adds it at 3rd layer of the pane
	layeredPane.add(ingameMenuPanel,new Integer(2),0);

	
	//add the rendering panel
	gamePanel = new GamePanel(player);
	
	gamePanel.setBounds(0,0,windowDimensionX,windowDimensionY);
	
	gamePanel.setOpaque(false);
	//adds it at bottom layer of the pane
	layeredPane.add(gamePanel,new Integer(0),0);


	//add the GUI rendering panel
	guiCanvas = new GUICanvas(this);
	guiCanvas.setBounds(inventoryBarPos.x,inventoryBarPos.y,windowDimensionX,windowDimensionY);
	guiCanvas.setOpaque(false);
	//adds it at 2nd layer of the pane
	layeredPane.add(guiCanvas,new Integer(1),0);



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

		int mouseX = e.getPoint().x;
		int mouseY = e.getPoint().y - mouseOffSetY;
		
		//only checks for user input if gameplay has started
		
		if(gameStarted){

			//debug statement
			System.out.println("Mouse release at " + (mouseX) + " " + mouseY);

		// left button released
		if (e.getButton() == MouseEvent.BUTTON1) {

			// Check if the player is dragging something

			if (draggingItem != null) {

				// see if there is an object or enemy at this position and
				// whether it can be used on it
				if (gamePanel.getObject(mouseX,mouseY) != null) {

					// check if the item being dragged can be used on the
					// targeted item
					//TODO ADD ability to use items on other items
					if (draggingItem.useItemOn(draggingItem,gamePanel.getObject(mouseX,mouseY))) {

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

						//draggingItem.addToInventory(player);

						player.addItem(draggingItem);
						// remove the dragging item
						draggingItem = null;

						// set the cursor back to default
						e.getComponent()
								.setCursor(
										Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}

				// check if mouse is at an inventory slot and that slot is empty
				else if(getInventorySlot(mouseX,mouseY)>=0 && player.getItem(getInventorySlot(mouseX,mouseY)) == null){

					// if its at this spot then stop dragging and add it to the players inventory at specified position

					player.addItem(draggingItem, getInventorySlot(mouseX,mouseY));

					System.out.println("added to inventory " + draggingItem.getImage());

					draggingItem = null;

					// set the cursor back to default
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				 else {
					// otherwise just drop the object at this position in the room

					 //TODO add drop item method
					//player.dropItem(e.getPoint());

					 draggingItem = null;

					// set the cursor back to default
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}

			}


			// if the player isn't dragging an item
			else {

				// check if there is an object/creature/item at mouse position

				if (gamePanel.getObject(mouseX,mouseY) != null) {

					//TODO ADD DESCRIPTION TEXT FOR ITEMS
					// change the description text to the items description
					//descriptionText = gamePanel.getObject(e.getPoint().x, e.getPoint().y).getDescription();
				}
			}


		// right button released
		}
		if (e.getButton() == MouseEvent.BUTTON3) {

		}


		//repaint the canvas so that changes show up
		guiCanvas.repaint();

	}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	// These mouse events are required for the MouseListener interface but are not used
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent e) {
		
		int mouseX = e.getPoint().x;
		int mouseY = e.getPoint().y - mouseOffSetY;
		
		//TODO make all of these method calls apply to actual classes

				//only checks for user input if the game has started

				if(gameStarted){

					//debug code
					System.out.println("Mouse clicked at " + e.getPoint().toString());

				// firstly check that the player isn't already holding something
				if (draggingItem == null) {
					// check if there is an item that can be dragged at the current
					// mouse position
					if (gamePanel.getObject(mouseX,mouseY) instanceof Grabable) {

						// begin dragging the item at mouse position
						draggingItem = (Grabable) gamePanel.getObject(mouseX,mouseY);

						//hide the item from the room so that it no longer appears on the screen and CANT BE INTERRACTED WITH
						//TODO: remove item from room
						//draggingItem.remove();

						// SET CURSOR TO ITEM HERE //
						setCursorImage(e, draggingItem.getName() +".png");
					}
				
					//else check if they selected an item in an inventory slot and that slot has an item in it
					else if (player.getItem(getInventorySlot(mouseX,mouseY)) != null) {
						
						Grabable inventoryItem = player.getItem(getInventorySlot(mouseX,mouseY));

						// remove the item at the selected position from the players
						// inventory
						player.removeItem(inventoryItem);

						// set the removed item as the dragged item
						draggingItem = inventoryItem;

						// SET CURSOR TO ITEM HERE //
						setCursorImage(e, draggingItem.getName() +".png");
					}
				}

				}
				guiCanvas.repaint();

	}

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
			player.moveForward();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {

			// turn the player right
			player.turnRight();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {

			// turn the player left
			player.turnLeft();
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
		gamePanel.update();
	}


	/**
	 * gives the inventory slot at the given position.
	 * @param the mouse position
	 * @return the number of the inventory slot found at the mouse position. -1 if no slot found.
	 */
	private int getInventorySlot(int x,int y){
		int totalSlots =  player.getInventorySize();

		  //needed so that it draws in the right position. Placeholder.
		int inventoryBarYOffset = 250;

		for(int i = 0; i<totalSlots;i++){

			//if the mouse is in the x bounds of one of the inventory slots
			if(x > inventoryBarPos.x + (i*inventorySlotSize) && x< inventoryBarPos.x + ((i+1)*inventorySlotSize)){

				System.out.println("inventory slot found X " + i);

				//if the mouse is in the y bounds of the inventory slots
				if(y> inventoryBarPos.y + inventoryBarYOffset && y<inventoryBarPos.y +inventorySlotSize + inventoryBarYOffset){
			
				System.out.println("inventory slot found Y " + i);

				//return this inventory slot
				return i;
				}
			}
		}

		//if nothing is found return -1 indicating no slot here
		return -1;
	}

	// These methods are unused but required for the KeyListener interface
	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}



	/**
	 * This method allows a client to connect to a specified server using the address
	 * and port number.
	 * @param address Address of the server.
	 * @param port Port specified by server for communication.
	 * @param username Identifier for the client attempting to connect
	 * @throws IOException
	 */
	public void runClient(String address, int port, String username) throws IOException {
		Socket s = new Socket(address, port);
		System.out.println("Client connecting to: " + address + " on port: " + port);

		player = new Slave(username);

		Client client = new Client(s, username,(Slave)player);

		client.start();

	}

	//TODO: this method requires a game world parameter
	//TODO: need to create a ServerRunning thread which will call this method. (last line must be in a thread)
	public void runServer(int port, int nclients, String userName) {
		System.out.println("Creating server on port " + port + " with " + nclients + " limit");

		//creates a game object that the server hosts
		player = new Host(userName, 25);

		//grid = player.getGrid();

		//creates a new server
		new Server(port, nclients, userName,(Host)player).start();
	}




	/**
	 * sets the cursor to a custom image given by item name
	 * @param e the mouse that will have its cursor set
	 * @param itemName the string for the image
	 */
	private void setCursorImage(MouseEvent e, String itemName){

		//set up the path to the image
		String imgPath = "GameMatter/"+itemName;

		//set up the image
		BufferedImage img;
		System.out.println("Set cursor to" +itemName);
		try {


			img = ImageIO.read(this.getClass().getResource("/object/" + itemName));
			//obImage = ImageIO.read(this.getClass().getResource("/object/" + ob.getImage() + ".png"));


			//create a cursor with item as image
			itemImageCursor = Toolkit.getDefaultToolkit().createCustomCursor(img,new Point(1,1), "itemCursor");

			//set the cursor to be this custom cursor
			e.getComponent().setCursor(itemImageCursor);

		} catch (IOException a) {

			a.printStackTrace();
		}


	}


	/**
	 * @return the item being dragged by the player
	 */
	public Grabable getDraggingItem() {
		return draggingItem;
	}

	public Player getPlayer(){
		return player;
	}

	/**
	 * @return the position of the inventory bar
	 */
	public Point getInventoryBarPos(){
		return inventoryBarPos;
	}

	/**
	 * @return the size of slots in the inventory
	 */
	public int getInventorySlotSize(){
		return inventorySlotSize;
	}


	//TODO get rid of later
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
	private Grabable makeDeco(Face face){
		int size = 20;
		int x = (int) ((100 - size) * Math.random()) + (size / 2);
		int y = (int) ((100 - size) * Math.random()) + size;
		return new Key("testFace", face, new Dimensions(x, y, size));
		//return new Grabable("testFace", face, new Dimensions(x, y, size));
	}

}
