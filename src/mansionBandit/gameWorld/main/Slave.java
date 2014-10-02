package mansionBandit.gameWorld.main;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Grabable;


/**
 * Each Slave object takes it's grid from the host, and also
 * creates a new Bandit for this player
 * @author Liam De Grey
 *
 */
public class Slave extends Player{
	private MansionArea[][] grid;
	private Bandit bandit;


	public Slave(String name, MansionArea[][] grid){
		this.bandit = new Bandit(name);
		this.grid = grid;
	}
}
