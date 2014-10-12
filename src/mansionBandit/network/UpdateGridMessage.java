package mansionBandit.network;

import mansionBandit.gameWorld.areas.MansionArea;

/**
 * An UpdateGridMessage represents the main form of serializable data transfer
 * over the I/O streams for Mansion Bandit game status.
 * @author Shreyas
 *
 */
public class UpdateGridMessage implements Message {
	private MansionArea[][] grid;

	UpdateGridMessage(MansionArea[][] grid) {
		this.grid = grid;
	}

	public MansionArea[][] getGrid() {
		return grid;
	}
}
