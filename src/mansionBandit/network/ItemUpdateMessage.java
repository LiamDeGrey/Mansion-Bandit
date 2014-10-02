package mansionBandit.network;

import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.GameMatter;

public class ItemUpdateMessage implements Message {
	private int uid;
	private Bandit bandit;
	private GameMatter item;

	ItemUpdateMessage(int uid, Bandit bandit, GameMatter item) {
		this.uid = uid;
		this.bandit = bandit;
		this.item = item;
	}

	public int getUid() {
		return uid;
	}

	public Bandit getBandit() {
		return bandit;
	}

	public GameMatter getItem() {
		return item;
	}

}
