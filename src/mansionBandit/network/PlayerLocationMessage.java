package mansionBandit.network;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.main.Player;

/**
 * This class represents a network message for whenever a player changes location.
 * @author ramasushre
 *
 */
public class PlayerLocationMessage implements Message {
	private Player player;
	private MansionArea area;

	PlayerLocationMessage(Player player, MansionArea area) {
		this.player = player;
		this.area = area;
	}

	public Player getPlayer() {
		return player;
	}

	public MansionArea getArea() {
		return area;
	}
}
