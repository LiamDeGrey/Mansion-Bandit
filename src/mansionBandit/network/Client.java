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
	private int uid; //unique id
	private String username;
	private ArrayList<String> usernameList;

	private Slave player;

	private GameFrame gameFrame;

	public Client (Socket socket, String username, Slave player) {
		this.socket = socket;
		this.username = username;
		this.player = player;
		usernameList = new ArrayList<String>();
	}

	public void start() {
		try {
			System.out.println("Client creating new streams");
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

			//read game info
			new ServerListener().start();

			//write our username to server
			try {
				output.writeObject(username); //Client writing test id here
			}
			catch (IOException e) {
				System.out.println("Exception writing uid to server : " + e);
			}

		} catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	// TODO: MOVEMENT DETECTION METHODS (SENDING UPDATES)
	// TODO: DISCONNECT METHOD FOR SAFETY

	/**
	 * This class is used to wait for messages from the server in order to update the
	 * local copy of the game.
	 * @author Shreyas
	 *
	 */
	class ServerListener extends Thread {

		public void run() {
			while(true) {
				try {
					//read server updates here
					Object o = input.readObject();
					if (o instanceof MansionArea[][]) {
						System.out.println("Received grid");
						player.setGrid((MansionArea[][]) o);
					}
					else if (o instanceof ArrayList) {
						System.out.println("Received username list");
						usernameList = (ArrayList<String>) o;
					}
				}
				catch(IOException e) {
					System.out.println("Connection has been ended by server: " + e);
				} catch (ClassNotFoundException e2) {
					System.out.println("Exception with class: " + e2);
				}
			}
		}

	}

	/**
	 * @return The gameWorld this client is using
	 */
	public Slave getPlayer(){
		return player;
	}
}
