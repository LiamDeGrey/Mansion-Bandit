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

/**
 * A client receives information about the current game world and uses it
 * to update its local copy. The client notifies the Server about any changes it
 * makes (player interaction, movement etc).
 * @author Shreyas
 *
 */
public final class Client {
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
	 * This method sets up the Client by creating a ServerListener thread and sending
	 * the Client's username.
	 *
	 */
	public void start() {
		try {
			System.out.println("Client creating new streams");
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
	 * This method writes the updated grid out to the stream for the Server to eventually
	 * broadcast.
	 */
	public synchronized void clientSendGrid() {
		try {
			output.writeObject(player.getGrid());
		} catch (IOException e) {
			System.out.println("Exception sending grid update " + e);
		}
	}

	public synchronized void clientSendRoom() {
		try {
			System.out.println("Creating room update message to write");
			output.writeObject(new RoomUpdateMessage(player.getBandit().getArea()));
		} catch (IOException e) {
			System.out.println("Exception sending room update " + e);
		}
	}
	
	public void clientSendItems() {
		try {
			System.out.println("Creating item update message to write");
			int[] coords = player.getBandit().getRoomCoords(player.getBandit().getArea());
			System.out.println("CLIENT SENDING ITEMS: " + player.getBandit().getArea().getItems());
			output.writeObject(new ItemUpdateMessage(player.getBandit().getArea().getItems(), coords[0], coords[1]));
			//output.flush();
			//output.reset();
		} catch (IOException e) {
			System.out.println("Exception sending item update " + e);
		}
	}

	/**
	 * The disconnect method closes all I/O streams and closes the socket.
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
						System.out.println("Received grid object");
						player.setGrid((MansionArea[][]) o);
					}
					if (o instanceof ArrayList) {
						System.out.println("Received username list");
						usernameList = (ArrayList<String>) o;
						System.out.println("list " + usernameList);
						gameFrame.updateClientPlayerList(usernameList);
					}
					if (o instanceof StringMessage) {
						System.out.println("Received string message");
						if (((StringMessage) o).getString().equals("START")) {
							System.out.println("Starting game");
							gameFrame.startClientMultiplayerGame();
						}
					}
					//if (o instanceof UpdateGridMessage) {
					//	System.out.println("Received grid message");
					//	player.setGrid(((UpdateGridMessage) o).getGrid());
					//}
					if (o instanceof RoomUpdateMessage) {
						System.out.println("Received room update message");
						int[] coords = player.getBandit().getRoomCoords(((RoomUpdateMessage) o).getRoom());
						if (!(coords[0] == -2 || coords[1] == -2)) {
							//System.out.println("CLIENT RECEIVED COORDS: i: " + coords[0] + " j: " + coords[1]);
							player.getBandit().setAreaInGrid(((RoomUpdateMessage) o).getRoom(), coords[0], coords[1]); //update locally
						}
					}
					if (o instanceof ItemUpdateMessage) {
						System.out.println("Received item update message");
						ItemUpdateMessage ium = (ItemUpdateMessage) o;
						int i = ium.getI();
						int j = ium.getJ();
						if (!(i == -2 || j == -2)) {
							//System.out.println("CLIENT RX - RECEIVED COORDS: i: " + i + " j: " + j);
							//System.out.println("CLIENT RX - RECEIVED ITEMS:       " + ium.getItems());
							//System.out.println("CLIENT RX - ROOM USED TO CONTAIN: " + player.getBandit().grid[i][j].getItems());
							player.getBandit().grid[i][j].setItems(ium.getItems());
							//System.out.println("CLIENT RX - ROOM NOW CONTAINS:    " + player.getBandit().grid[i][j].getItems());
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
