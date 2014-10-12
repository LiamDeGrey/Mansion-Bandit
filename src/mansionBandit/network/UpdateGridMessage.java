package mansionBandit.network;

import mansionBandit.gameWorld.areas.MansionArea;

public class UpdateGridMessage implements Message {
	private MansionArea[][] grid;

	UpdateGridMessage(MansionArea[][] grid) {
		this.grid = grid;
	}

	public MansionArea[][] getGrid() {
		return grid;
	}
}
