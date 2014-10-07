package mansionBandit.network;

import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.GameMatter;

public class ItemPickupMessage implements Message {
	private Player player;
	private GameMatter item;

	ItemPickupMessage(Player player, GameMatter item) {
		this.player = player;
		this.item = item;
	}

	public Player getPlayer() {
		return player;
	}

	public GameMatter getItem() {
		return item;
	}

}
