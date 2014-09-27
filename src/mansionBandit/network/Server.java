package mansionBandit.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Server receives updates from clients via a socket. This updates the Server's version
 * of the game, and these updates are sent to all clients.
 * @author Shreyas
 *
 */
public final class Server {
	//FIELDS
	//game
	private static int uniqueID;
	private int port, playerLimit;
	private boolean end;
	
	public Server(int port, int playerLimit) {
		this.port = port;
		this.playerLimit = playerLimit;
	}
	
	public void start() {
		end = false;
		
		try {
			ServerSocket ss = new ServerSocket(port);
			
			while (!end) {
				System.out.println("Mansion Bandit Server listening on port: " + port);
				System.out.println("Waiting for players to join...");
				
				Socket s = ss.accept();
				//TODO: create a list of connections here potentially
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
	class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream input;
		ObjectOutputStream output;
		int uid;
		String username;
		//types of messages

		ClientThread(Socket socket) {
			uid = ++uniqueID;
			this.socket = socket;
			
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input  = new ObjectInputStream(socket.getInputStream());
				username = (String) input.readObject();
				System.out.println(username + " has connected.");
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

		//TODO: METHOD TO WRITE UPDATES
	}
}
