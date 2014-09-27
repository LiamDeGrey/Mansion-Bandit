package mansionBandit.gameWorld.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import mansionBandit.gameWorld.areas.RoomsLayout;
import mansionBandit.network.Client;
import mansionBandit.network.Server;

/**
 * This class starts off the game
 * creates the panels and frames
 * and initialises the rooms and items
 * @author Liam De Grey
 *
 */
public class Main {
	
	public Main(){
		//System.out.println("Make the JFrame");
		new RoomsLayout(20);
	}
	
	/**
	 * This method allows a client to connect to a specified server using the address
	 * and port number.
	 * @param address Address of the server.
	 * @param port Port specified by server for communication.
	 * @param username Identifier for the client attempting to connect
	 * @throws IOException
	 */
	private static void runClient(String address, int port, String username) throws IOException {
		Socket s = new Socket(address, port);
		System.out.println("Client connecting to: " + address + " on port: " + port);
		new Client(s, username).start();
	}
	
	//TODO: this method requires a game world parameter
	private static void runServer(int port, int nclients) {
		System.out.println("Creating server on port " + port + " with " + nclients + " limit");
		new Server(port, nclients).start();
	}
	
	public static void main(String[] args){
		new Main();
	}
}
