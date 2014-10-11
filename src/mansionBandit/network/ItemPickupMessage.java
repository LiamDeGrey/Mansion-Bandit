package mansionBandit.network;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * This class represents a network message for whenever a player picks up
 * or moves an item.
 * @author Shreyas
 *
 */
public class ItemPickupMessage implements Message {
	private Player player;
	private GameMatter item;
	private MansionArea area;

	ItemPickupMessage(Player player, GameMatter item, MansionArea area) {
		this.player = player;
		this.item = item;
		this.area = area;
	}

	public Player getPlayer() {
		return player;
	}

	public GameMatter getItem() {
		return item;
	}

	public MansionArea getArea() {
		return area;
	}

}
