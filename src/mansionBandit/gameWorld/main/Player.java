package mansionBandit.gameWorld.main;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.RoomsLayout;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Grabable;

/**
 * Player object is different for each client in the game
 * Each client has its own player and this class also takes
 * the pre defined rooms layout
 * @author Liam De Grey
 *
 */
public abstract class Player {
	private MansionArea[][] grid;
	private Bandit bandit;

	public Player(String name, int rooms){
		this.bandit = new Bandit(name);
		this.grid = new RoomsLayout(rooms).getGrid();
	}

	public Player(String name, MansionArea[][] grid){
		this.bandit = new Bandit(name);
		this.grid = grid;
	}

	public Bandit getBandit(){
		return bandit;
	}

	public MansionArea[][] getGrid(){
		return grid;
	}

	public void turnLeft(){
		int current = Face.getFaceNum(bandit.getFace());
		if(current==0) current = 3;
		else current--;
		bandit.setFace(Face.getFace(current));
	}

	public void turnRight(){
		int current = Face.getFaceNum(bandit.getFace());
		if(current==3) current = 0;
		else current++;
		bandit.setFace(Face.getFace(current));
	}

	public void moveForward(){

	}

	public boolean addItem(Grabable itm, int slot){
		return bandit.addItem(itm, slot);
	}

	public boolean addItem(Grabable itm){
		return bandit.addItem(itm);
	}

	public boolean removeItem(Grabable itm){
		return bandit.removeItem(itm);
	}

	public Grabable getItem(int slot){
		return bandit.getItem(slot);
	}

	public int getInventorySize(){
		return 6;
	}


}

