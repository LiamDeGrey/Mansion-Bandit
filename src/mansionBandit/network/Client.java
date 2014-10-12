package mansionBandit.network;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	 * @author Shreyas
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

	// TODO: METHODS FOR SENDING UPDATES
	public void clientSendGrid() {
		MansionArea[][] toSend = player.getGrid();
		try {
			output.writeObject(toSend);
		} catch (IOException e) {
			System.out.println("Exception sending grid update " + e);
		}
	}

	/**
	 * The disconnect method closes all I/O streams and closes the socket.
	 * @author Shreyas
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
        //TODO: NEED TO INFORM THE GUI
	}

	/**
	 * This class is used to wait for messages from the server in order to update the
	 * local copy of the game.
	 * @author Shreyas
	 *
	 */
	class ServerListener extends Thread {

		public void run() {
			while(keepGoing) {
				try {
					//Read server updates here
					Object o = input.readObject();
					if (o instanceof MansionArea[][]) {
						System.out.println("Received grid");
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
						if (o == "START") {
							gameFrame.startClientMultiplayerGame();
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
