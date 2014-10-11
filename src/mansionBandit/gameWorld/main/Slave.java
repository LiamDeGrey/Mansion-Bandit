package mansionBandit.gameWorld.main;

import mansionBandit.gameWorld.areas.MansionArea;


/**
 * Each Slave object takes it's grid from the host, and also
 * creates a new Bandit for this player
 * @author Liam De Grey
 *
 */
public class Slave extends Player{

	//TODO: Create a reference to Client. Slave needs Client to broadcast
	//its messages out to the Server and eventually all clients.
	public Slave(String name){
		super(name);
	}
}
