package mansionBandit.ApplicationWindow;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import mansionBandit.gameView.GamePanel;
import mansionBandit.gameWorld.areas.Grid;
import mansionBandit.gameWorld.areas.Map;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.main.Host;
import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.main.Slave;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Grabable;
import mansionBandit.gameWorld.matter.Key;
import mansionBandit.network.Client;
import mansionBandit.network.Server;

/**
 * The frame that the user interacts with
 * @author Theo
 */
public class GameFrame extends JFrame implements ActionListener,
		WindowListener, KeyListener {

	// window dimensions
	private final int windowDimensionX = 800;
	private final int windowDimensionY = 717;

	private Grabable draggingItem; //the item being dragged by the player

	private JLabel descriptionLabel;	//contains descriptions of items

	private boolean ingameMenuActive = false; 	// whether or not the ingame menu is up

	InGameMenuPanel ingameMenuPanel;//the in-game menu

	private GamePanel gamePanel;//the panel used for displaying game graphics

	private Cursor itemImageCursor;// the cursor image used for when the player drags items

	private MainMenuPanel mainMenu;//the main menu

	private boolean gameStarted = false;//whether gameplay has started. The game only checks for mouse and keyboard user input if the game has started.

	private Player player;//the player this window is used by/applies to

	//GUI FIELDS//
	//position of in game menu
	private final int ingameMenuX = 325;
	private final int ingameMenuY = 200;
	private final int ingameMenuW = 150;
	private final int ingameMenuH = 150;
	private GUICanvas guiCanvas;//the GUI drawing canvas
	private final Point inventoryBarPos = new Point(50,300);//the position of the inventory bar
	private final int inventorySlotSize = 80;//size of a single inventory slot
	private JLayeredPane layeredPane;//the pane that all components are added to so that they can stack properly
	private JLabel timeLabel;//the label used to display how much time is left

	//Server
	private Server server;

	//Client
	private Client client;

	private Controller controller;//the controller that manages player interaction

	public GameFrame(ApplicationMain main) {
		super();

		this.setLayout(null);

		getContentPane().setBackground(Color.BLACK);

		//creates the main menu
		mainMenu = new MainMenuPanel(this);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		setLocationRelativeTo(null);

		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true);

		// listens for mouse input
		addMouseListener(controller);

		// listens for keyboard input
		addKeyListener(this);
		addKeyListener(controller);

		//sets up main menu interface
		enterMainMenu();



	}

	public Dimension getPreferredSize() {
		return new Dimension(windowDimensionX, windowDimensionY);
	}

	/**
	 * Sets up GUI elements
	 */
	private void setupScreen() {

		ingameMenuPanel = new InGameMenuPanel(this);

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


		Map map = new Map(player);
		//sets position of map

		map.setLocation(690,10);
        map.setSize(map.getPreferredSize());

        //map.setBounds(649,10,1500,1500);
		//map.setVisible(true);
		//map.repaint();
		//adds the map to the game screen
		layeredPane.add(map, new Integer(1),0);

		//adds description LABEL
		descriptionLabel = new JLabel("<html><p><center></center></p></html>");
		descriptionLabel.setBounds(662,inventoryBarPos.y+299,137,87);
		descriptionLabel.setBackground(Color.DARK_GRAY);
		descriptionLabel.setOpaque(true);
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabel.setVerticalAlignment(SwingConstants.CENTER);
		descriptionLabel.setForeground(Color.white);
		layeredPane.add(descriptionLabel, new Integer(1),0);


		//adds TIME LABEL
		timeLabel = new JLabel("<html><p><center></center></p></html>");
		timeLabel.setBounds(0,inventoryBarPos.y+299,98,87);
		timeLabel.setBackground(Color.DARK_GRAY);
		timeLabel.setOpaque(true);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setVerticalAlignment(SwingConstants.CENTER);
		timeLabel.setForeground(Color.white);
		layeredPane.add(timeLabel, new Integer(1),0);


		//redisplay the screen
		this.revalidate();
		this.repaint();
		this.pack();
	}

	// MENUS AND FRAME INTERACTION//


	/**
	 * ends the current game by resetting values, disconnecting and removing GUI components
	 */
	private void endGame(){

		this.remove(layeredPane);
		closeIngameMenu();

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

	//make the player
	player = new Host("", 20);

	player.getBandit().setArea(makeRoom());
	player.getBandit().setFace(Face.NORTHERN);

	//sets up user interface elements
	setupScreen();

	controller= new Controller(player, gamePanel,this);
	addMouseListener(controller);
	addKeyListener(controller);
	//indicate that gameplay has started
	gameStarted = true;

	}

	public void startMultiplayerGame(){

		this.remove(mainMenu);

		setupScreen();

		controller= new Controller(player, gamePanel,this);
		addMouseListener(controller);
		addKeyListener(controller);

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
			JOptionPane.showMessageDialog(
							this,
							"Steal as much as you can before the time runs out. Take stolen items to the van to cash them in. Drag items around to interact with them. Right click them to get a description.",
							"Help ", JOptionPane.INFORMATION_MESSAGE);

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

		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Escape")) {
			// if the in game menu is up, close it
			if (ingameMenuActive) {
				closeIngameMenu();
			} else {
				// otherwise bring up the ingame menu
				showIngameMenu();
			}
		}
		}

		//resets descriptiontext
		descriptionLabel.setText("");

		gamePanel.update();
	}

	// These methods are unused but required for the KeyListener interface
	@Override
	public void keyPressed(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	/**
	 * gives the inventory slot at the given position.
	 * @param the mouse position
	 * @return the number of the inventory slot found at the mouse position. -1 if no slot found.
	 */
	public int getInventorySlot(int x,int y){
		int totalSlots =  player.getInventorySize();

		  //needed so that it draws in the right position. Placeholder.
		int offsetY = 285;
		int offsetX = 52;


		for(int i = 0; i<totalSlots;i++){

			//if the mouse is in the x bounds of one of the inventory slots
			//if(x > inventoryBarPos.x + (i*inventorySlotSize) + offsetX && x< inventoryBarPos.x + ((i+1)*inventorySlotSize)+ offsetX){
			if(x > inventoryBarPos.x + (i*inventorySlotSize) + offsetX && x< inventoryBarPos.x + ((i+1)*inventorySlotSize)+ offsetX){
				System.out.println("inventory slot found X " + i);

				//if the mouse is in the y bounds of the inventory slots
				if(y> inventoryBarPos.y + offsetY && y<inventoryBarPos.y +inventorySlotSize + offsetY){

				System.out.println("inventory slot found Y " + i);

				//return this inventory slot
				return i;
				}
			}
		}

		//if nothing is found return -1 indicating no slot here
		return -1;
	}

	/**
	 * sets the time of the time displayed in the time label.
	 * @param time
	 */
	public void updateTimeLeft(int time){
		timeLabel.setText("" +time);
	}

	public void setDescriptionText(String text){
		descriptionLabel.setText(text);
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

		client = new Client(s, username,(Slave)player,this);
		((Slave) player).setClient(client);

		client.start();

	}

	//TODO: this method requires a game world parameter
	//TODO: need to create a ServerRunning thread which will call this method. (last line must be in a thread)
	public void runServer(int port, int nclients, String userName) {
		System.out.println("Creating server on port " + port + " with " + nclients + " limit");

		//creates a game object that the server hosts
		player = new Host(userName, 25);

		//creates a new server
		this.server = new Server(port, nclients, userName,(Host)player,this);
		((Host) player).setServer(server);
		new ServerRunning().start();
	}


	class ServerRunning extends Thread {
		public void run() {
			server.start();
		}
	}

	/**
	 * indicates that a player has connected to the game
	 */
	public void updatePlayerList(ArrayList<String> usernameList){
		mainMenu.updatePlayerList(usernameList);
	}

	/**
	 * indicates that a player has connected to the game
	 */
	public void updateClientPlayerList(ArrayList<String> usernameList){
		System.out.println("IN GAME FRAME " +usernameList);
		mainMenu.updateClientPlayerList(usernameList);
	}


	/**
	 * sets the cursor to a custom image given by item name
	 * @param e the mouse that will have its cursor set
	 * @param itemName the string for the image
	 */
	public void setCursorImage(MouseEvent e, String itemName){

		//set up the image
		BufferedImage img;
		System.out.println("Set cursor to" +itemName);

		try {

			img = ImageIO.read(this.getClass().getResource("/object/" + itemName));

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

	public Server getServer(){
		if(server != null){
		return server;
		}
		return null;
	}

	public Client getClient(){
		return client;
	}

	public boolean gameStarted(){
		return gameStarted;
	}

	public GUICanvas getGUICanvas(){
		return guiCanvas;
	}


	//TODO get rid of later
	public Room makeRoom(){
		//currently this is a test integration of objects
        Room demoRoom = new Room("wall1", "ceiling1", "floor1");
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
