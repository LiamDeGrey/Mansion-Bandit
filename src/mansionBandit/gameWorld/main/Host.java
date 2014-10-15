package mansionBandit.gameWorld.main;

import mansionBandit.network.Server;

/**
 * There is only one host object in each game, this object creates the
 * grid and stores a bandit for the player
 * @author Liam De Grey
 *
 */
public class Host extends Player{
	private Server server;

	//TODO: Create a reference to a server. Host needs Server to broadcast out,
	//it doesn't need to send messages anywhere else.
	public Host (String name, int id, int rooms){
		super(name, id, rooms);
	}

	public void setServer(Server s) {
		this.server = s;
	}

	public void updateItems() {
		if (server != null) {
			server.serverSendItems();
		}
	}

}
