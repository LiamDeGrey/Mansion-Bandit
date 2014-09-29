package mansionBandit.gameWorld.main;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.matter.Bandit;

/**
 * Player object is different for each client in the game
 * Each client has its own player and this class also takes
 * the pre defined rooms layout
 * @author Liam De Grey
 *
 */
public class Player {
	private MansionArea[][] grid;
	private Bandit player;

	public Player(Bandit player, MansionArea[][] grid){
		this.player = player;
		this.grid = grid;
	}

	public Bandit getPlayer(){
		return player;
	}

	public MansionArea[][] getGrid(){
		return grid;
	}

}
