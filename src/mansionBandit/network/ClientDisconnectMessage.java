package mansionBandit.network;

import mansionBandit.gameWorld.main.Player;

/**
 * A ClientDisconnectMessage is to be sent to the Server containing the username of
 * the Client that is disconnecting from the Server.
 * @author Shreyas
 *
 */
public class ClientDisconnectMessage implements Message {
	private String username;

	ClientDisconnectMessage(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

}
