package mansionBandit.network;

import java.util.List;

import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * An UpdateGridMessage represents the main form of serializable data transfer
 * over the I/O streams for Mansion Bandit game status.
 * @author Shreyas
 *
 */
public class ItemUpdateMessage implements Message {
	private List<GameMatter> items;
	private Bandit movingBandit;
	private int i;
	private int j;

	ItemUpdateMessage(List<GameMatter> list, Bandit movingPlayer, int i, int j) {
		this.items = list;
		this.movingBandit = movingPlayer;
		this.i = i;
		this.j = j;
	}

	public List<GameMatter> getItems() {
		return items;
	}

	public Bandit getMovingBandit() {
		return movingBandit;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
}
