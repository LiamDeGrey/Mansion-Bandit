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


	/**
	 * returns item at specified slot in players inventory
	 * @param slot
	 * @return
	 */
	public Grabable getItem(int slot){
		return inventory.get(slot);
	}

	/**
	 * Adds an item to players inventory
	 * @param the item to add
	 * @return whether the item was added succesfully
	 */
	public boolean addItem(Grabable item){
		if(inventory.size()<6){
			return inventory.add(item);
		}
		return false;
	}

	/**
	 * Adds an item to players inventory
	 * @param the item to add
	 * @return whether the item was added succesfully
	 */
	public boolean removeItem(Grabable item){
		 return inventory.remove(item);

	}

	public void turnLeft(){

	}

	public void turnRight(){

	}

	public void moveForward(){

}

	public int getInventorySize(){
		return 7;
	}


}

