package mansionBandit.gameWorld.main;

import java.util.ArrayList;
import java.util.List;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Grabable;

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
	private List<Grabable> inventory = new ArrayList<Grabable>();

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

	/*
	 * Adds item to inventory , checks if valid
	 */
	public boolean addItem(Grabable itm, int slot){
		if(slot>=0&&slot<=6){
			if(inventory.get(slot)!=null){
				inventory.add(itm);
				return true;
			}
		}
		return false;
	}



}

