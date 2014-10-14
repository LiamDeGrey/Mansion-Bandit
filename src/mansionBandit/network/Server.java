package mansionBandit.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;





//import Server.ClientThread;
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
		usernameList.add("Empty slot");
		usernameList.add("Empty slot");
		usernameList.add("Empty slot");
		clientList = new ArrayList<ClientThread>();
		this.player = player;
		this.gameFrame = gameframe;
	}

	/**
	 * The start method sets up the ServerSocket and enters a loop where it awaits connections. Upon a
	 * successful connection, the Client is added to a list.
	 *
	 */
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

			try { //Attempt to close sockets/clients
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
	 * This method will broadcast the Server's movements out to the Clients.
	 *
	 */
	public synchronized void serverSendGrid() {
		broadcastGrid(player.getGrid());
	}

	public synchronized void serverSendRoom() {
		broadcast(new RoomUpdateMessage(player.getBandit().getArea()));
	}

	/**
	 * Used for iterating the list of clients and sending them each a message.
	 * @param msg The message to be sent to all clients.
	 *
	 */
	public synchronized void broadcast(Message msg) {
		for(int i = clientList.size(); --i >= 0;) {
			ClientThread ct = clientList.get(i);

			ct.sendMessage(msg);
		}
	}

	public synchronized void broadcastGrid(MansionArea[][] grid) {
		for(int i = clientList.size(); --i >= 0;) {
			ClientThread ct = clientList.get(i);

			ct.sendGridObject(grid);
		}
	}

	/**
	 * Removes a specific ClientThread from the list.
	 * @param ct The ClientThread to be removed.
	 */
	public synchronized void remove(ClientThread ct) {
		clientList.remove(ct);
	}

	/**
	 * This class represents a Thread that will run for each of the clients connected to the server.
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
				for (int i = 0; i < usernameList.size(); i++) {
					if(usernameList.get(i) == "Empty slot") {
						usernameList.set(i, username);
						break;
					}
				}
				gameFrame.repaint();
				gameFrame.updatePlayerList(usernameList);

				//Broadcasting grid to clients that connect
				MansionArea[][] grid = player.getBandit().getGrid();
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

		/**
		 * This method sends a single message to an individual Client along the stream. Checks for
		 * whether the Client is still connected via the socket.
		 * @param msg The single message to be sent.
		 */
		public void sendMessage(Message msg) {
			//Check if Client is still connected
			if(!socket.isConnected()) {
				System.out.println(username + " is not connected, closing socket.");
				close();
			}

			//Send the message out on its stream
			try {
				output.writeObject(msg);
			}
			//Inform that an error occurred with sending the message, do not close anything
			catch(IOException e) {
				System.out.println("Error sending message to " + username);
				System.out.println(e.toString());
			}
		}

		public synchronized void sendGridObject(MansionArea[][] grid) {
			//Check if Client is still connected
			if(!socket.isConnected()) {
				System.out.println(username + " is not connected, closing socket.");
				close();
			}

			//Send the message out on its stream
			try {
				output.writeObject(grid);
			}
			//Inform that an error occurred with sending the message, do not close anything
			catch(IOException e) {
				System.out.println("Error sending message to " + username);
				System.out.println(e.toString());
			}
		}

		public void run() {
			boolean end = false;
			while(!end) {
				try {
					//Read input and act accordingly
					Object obj = input.readObject();
					if (obj instanceof ClientDisconnectMessage) {
						System.out.println("got clientdisconnect message");
						ClientThread toDisconnect = getClient(((ClientDisconnectMessage) obj).getUsername());
						usernameList.set(usernameList.indexOf(username), "Empty slot");
						System.out.println(usernameList);
						remove(toDisconnect);
						System.out.println(clientList.size());
						if (clientList.size() != 0) {
							for (ClientThread ct : clientList) {
								ct.output.writeObject(usernameList);
							}
						}
						System.out.println("updating server client list view");
						gameFrame.updatePlayerList(usernameList);
					}
					//if (obj instanceof UpdateGridMessage) {
					//	System.out.println("got update grid message");
					//	player.setGrid(((UpdateGridMessage) obj).getGrid());
					//	broadcast((Message) obj);
					//}
					if (obj instanceof MansionArea[][]) {
						System.out.println("got grid object message");
						player.setGrid((MansionArea[][]) obj);
						broadcastGrid((MansionArea[][]) obj);
					}
					if (obj instanceof RoomUpdateMessage) {
						System.out.println("got room update message");
						int[] coords = player.getBandit().getRoomCoords(((RoomUpdateMessage) obj).getRoom());
						if (!(coords[0] == -2 || coords[1] == -2)) {
							System.out.println("SERVER RECEIVED COORDS: i: " + coords[0] + " j: " + coords[1]);
							player.getBandit().setAreaInGrid(((RoomUpdateMessage) obj).getRoom(), coords[0], coords[1]); //update locally
							broadcast(new RoomUpdateMessage((MansionArea) obj));
						}
					}
				}
				catch (Exception e) {
					System.out.println(username + ": Exception reading Object Streams: " + e);
					break;
				}
			}
			//ClientThread has ended
			close();
		}

		/**
		 * Attempts to close all the sockets/streams for a single ClientThread.
		 */
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

	}

	public ClientThread getClient(int i){
		return clientList.get(i);
	}

	public ClientThread getClient(String username) {
		ClientThread clientThread = null;
		for (ClientThread ct : clientList) {
			if (ct.username == username) {
				clientThread = ct;
			}
		}
		return clientThread;
	}

	public String getServerUserName() {
		return username;
	}


}
