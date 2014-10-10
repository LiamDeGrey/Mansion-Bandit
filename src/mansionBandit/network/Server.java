package mansionBandit.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import mansionBandit.ApplicationWindow.GameFrame;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.main.Host;


/**
 * The Server receives updates from clients via a socket. This updates the Server's version
 * of the game, and these updates are sent to all clients.
 * @author Shreyas
 *
 */
public final class Server {
	private static int uniqueID;
	private String username;
	private ArrayList<String> usernameList;
	private int port, playerLimit;
	private ArrayList<ClientThread> clientList;
	private boolean end;
	protected Host player;
	private GameFrame gameFrame;

	public Server(int port, int playerLimit, String userName, Host player, GameFrame gameframe) {
		this.port = port;
		this.playerLimit = playerLimit;
		this.username = userName;
		usernameList = new ArrayList<String>();
		usernameList.add(username);
		clientList = new ArrayList<ClientThread>();
		this.player = player;
		this.gameFrame = gameframe;
	}

	public void start() {
		end = false;

		try {
			ServerSocket ss = new ServerSocket(port);

			while (!end) {
				System.out.println("Mansion Bandit Server listening on port: " + port);
				System.out.println("Waiting for players to join...");

				Socket s = ss.accept();
				ClientThread ct = new ClientThread(s);
				clientList.add(ct);
				ct.start();
			}

			try { //attempt to close sockets/clients
				ss.close();
				for (ClientThread c : clientList) {
					try {
						c.input.close();
						c.output.close();
						c.socket.close();
					} catch (IOException e) {
						System.out.println("Exception closing clients: " + e);
					}
				}
			} catch (Exception e) {
				System.out.println("Exception closing socket: " + e);
			}

		} catch (IOException e) {
			System.out.println("Exception with socket: " + e);
		}
	}

	/**
	 * This class represents a Thread that will run for each of the clients connected to the server.
	 * @author Shreyas
	 *
	 */
	public class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream input;
		ObjectOutputStream output;
		int uid;
		int testid;
		String username;
		//types of messages

		ClientThread(Socket socket) {
			uid = ++uniqueID;
			this.socket = socket;
			System.out.println("New client thread created");

			try {
				//Creating I/O streams for a client
				output = new ObjectOutputStream(socket.getOutputStream());
				input  = new ObjectInputStream(socket.getInputStream());

				//Read username that Client broadcasts to us
				username = (String) input.readObject();
				System.out.println(username + " has connected.");
				usernameList.add(username);
				gameFrame.repaint();
				gameFrame.playerHasConnected(usernameList);

				//Broadcasting grid to clients that connect
				MansionArea[][] grid = player.getGrid();
				output.writeObject(grid);

				//Broadcasting username list to clients that connect
				output.writeObject(usernameList);
			}
			catch (IOException e) {
				System.out.println(username + ": Exception creating IO Object Streams: " + e);
				return;
			} catch (ClassNotFoundException e) {
				System.out.println(username + ": Exception with class: " + e);
			}
		}

		public void run() {
			boolean end = false;
			while(!end) {
				try {
					//read input
				}
				catch (Exception e) {
					System.out.println(username + ": Exception reading Object Streams: " + e);
					break;
				}
			}
			//out of loop, therefore close everything
			close();
		}

		//TODO: STOP METHOD (OPTION FOR SERVER HOST)

		private void close() {
			try {
				if(output != null) output.close();
			}
			catch(Exception e) {}
			try {
				if(input != null) input.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}


		public String getClientUserName(){
			return username;
		}
		//TODO: METHOD TO WRITE UPDATES
	}

	public ClientThread getClient(int i){
		return clientList.get(i);
	}

	public String getServerUserName() {
		return username;
	}


}
