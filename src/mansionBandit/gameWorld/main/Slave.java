package mansionBandit.gameWorld.main;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.network.Client;


/**
 * Each Slave object takes it's grid from the host, and also
 * creates a new Bandit for this player
 * @author Liam De Grey
 *
 */
public class Slave extends Player{
	private Client client;

	//TODO: Create a reference to Client. Slave needs Client to broadcast
	//its messages out to the Server and eventually all clients.
	public Slave(String name){
		super(name);
	}

	public void setClient(Client c) {
		this.client = c;
	}

	protected void updateGrid(){
		System.out.println("====== CLIENT SENT MESSAGE ========");
		client.clientSendGrid();
	}
}
