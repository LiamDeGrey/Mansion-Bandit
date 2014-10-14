package mansionBandit.network;

import mansionBandit.gameWorld.areas.MansionArea;

public class RoomUpdateMessage implements Message {
	private MansionArea room;

	RoomUpdateMessage(MansionArea room) {
		this.room = room;
	}

	public MansionArea getRoom() {
		return room;
	}
}
