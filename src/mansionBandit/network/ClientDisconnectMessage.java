package mansionBandit.network;

import mansionBandit.gameWorld.main.Player;

public class ClientDisconnectMessage implements Message {
	private String username;

	ClientDisconnectMessage(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

}
