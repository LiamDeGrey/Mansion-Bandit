package mansionBandit.network;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import mansionBandit.ApplicationWindow.GameFrame;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.main.Slave;
import mansionBandit.gameWorld.matter.Bandit;

/**
 * A client receives information about the current game world and uses it
 * to update its local copy. The client notifies the Server about any changes it
 * makes (player interaction, movement etc).
 * @author Shreyas
 *
 */
public final class Client {
	private int uid;
	private final Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String username;
	private ArrayList<String> usernameList;
	private boolean keepGoing = true;

	private Slave player;

	private GameFrame gameFrame;

	public Client (Socket socket, String username, Slave player, GameFrame gameframe) {
		this.socket = socket;
		this.username = username;
		this.player = player;
		usernameList = new ArrayList<String>();
		this.gameFrame = gameframe;
	}

	/**
	 * Sets up the Client by creating a ServerListener thread and sending the Client's username.
	 *
	 */
	public void start() {
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

			//Reading initial info from Server, also setting up a listener thread
			new ServerListener().start();

			//Write our username to server
			try {
				output.writeObject(username);
			}
			catch (IOException e) {
				System.out.println("Exception writing uid to server : " + e);
				disconnect();
			}

		} catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
			disconnect();
		}
	}

	/**
	 * Sends out an ItemUpdateMessage out on the stream to the server.
	 *
	 */
	public void clientSendItems() {
		try {
			int[] coords = player.getBandit().getRoomCoords(player.getBandit().getArea());
			output.writeObject(new ItemUpdateMessage(player.getBandit().getArea().getItems(), player.getBandit(), coords[0], coords[1]));
			output.reset();
		} catch (IOException e) {
			System.out.println("Exception sending item update " + e);
		}
	}

	/**
	 * Sends a StringMessage out on the stream to the server.
	 * @param message The string to be sent.
	 */
	public void sendChatMessage(String message) {
		try {
			message = username + ": " + message + "\n";
			output.writeObject(new StringMessage(message));
		} catch (IOException e) {
			System.out.println("Exception sending chat message");
		}
	}

	/**
	 * Closes all I/O streams and closes the socket.
	 *
	 */
	public void disconnect() {
		ClientDisconnectMessage cdm = new ClientDisconnectMessage(username);
        try {
			output.writeObject(cdm);
		} catch (IOException e) {
			System.out.println("Exception with sending disconnect message " + e);
		}

		try {
			if(input != null) input.close();
		}
		catch(Exception e) {}
		try {
			if(output != null) output.close();
		}
		catch(Exception e) {}
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {}
	}

	/**
	 * This class is used to wait for messages from the server in order to update the
	 * local copy of the game.
	 * @author Shreyas
	 */
	class ServerListener extends Thread {
		public void run() {
			while(keepGoing) {
				try {
					//Read server updates here
					Object o = input.readObject();
					if (o instanceof MansionArea[][]) {
						player.setGrid((MansionArea[][]) o);
					}
					if (o instanceof Integer) {
						player.giveId((Integer) o);
					}
					if (o instanceof ArrayList) {
						usernameList = (ArrayList<String>) o;
						gameFrame.updateClientPlayerList(usernameList);
					}
					if (o instanceof StringMessage) {
						if (((StringMessage) o).getString().equals("START")) {
							gameFrame.startClientMultiplayerGame();
						}
						if (((StringMessage) o).getString().equals("END")) {
							//show dialog and put to main menu
							gameFrame.hostDisconnected();
						}
						if (((StringMessage) o).getString().equals("ENDLOBBY")) {
							//show dialog and put to main menu
							gameFrame.hostLobbyDisconnected();
						}
						else {
							gameFrame.updateChatPanel(((StringMessage) o).getString());
						}
					}
					if (o instanceof ItemUpdateMessage) {
						ItemUpdateMessage ium = (ItemUpdateMessage) o;
						Bandit movingBandit = ium.getMovingBandit();
						int i = ium.getI();
						int j = ium.getJ();

						if (player.getBandit().grid[i][j].getItems().contains(player.getBandit())){
							if (!ium.getItems().contains(player.getBandit())){
								//Player has been removed from the current room
								//Set their location to start area
								player.getBandit().setArea(-1, -1);
								player.getBandit().initialiseInventory();
							}
						}

						//Remove any references of Bandit that may be in adjacent rooms
						if (!(movingBandit.equals(player.getBandit()))) {
							if (i-1 >= 0) {
								player.getBandit().grid[i-1][j].removeItem(movingBandit);
							}

							if (i+1 < player.getBandit().grid.length) {
								player.getBandit().grid[i+1][j].removeItem(movingBandit);
							}

							if (j-1 >= 0) {
								player.getBandit().grid[i][j-1].removeItem(movingBandit);
							}

							if (j+1 < player.getBandit().grid.length) {
								player.getBandit().grid[i][j+1].removeItem(movingBandit);
							}
						}

						if (!(i == -2 || j == -2)) {
							player.getBandit().grid[i][j].setItems(ium.getItems());
							gameFrame.getGamePanel().update();
						}
					}
				}
				catch(IOException e) {
					System.out.println("Connection has been ended by server: " + e);
					keepGoing = false;
				} catch (ClassNotFoundException e2) {
					System.out.println("Exception with class: " + e2);
				}
			}
		}

	}

	/**
	 * @return The Player this Client is associated with.
	 */
	public Slave getPlayer(){
		return player;
	}
}
