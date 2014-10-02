package mansionBandit.gameWorld.main;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.RoomsLayout;
import mansionBandit.gameWorld.matter.Bandit;

/**
 * There is only one host object in each game, this object creates the
 * grid and stores a bandit for the player
 * @author Liam De Grey
 *
 */
public class Host extends Player{
	private MansionArea[][] grid;
	private Bandit bandit;


	public Host(String name, int rooms){
		this.bandit = new Bandit(name);
		this.grid = new RoomsLayout(rooms).getGrid();
	}

}
