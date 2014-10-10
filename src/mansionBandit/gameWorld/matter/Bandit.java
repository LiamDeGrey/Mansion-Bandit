package mansionBandit.gameWorld.matter;

import java.util.ArrayList;
import java.util.List;

import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.StartSpace;


/**
 * A Bandit is any person that has entered the game to play
 * as a thief
 * @author Liam De Grey
 *
 */
public class Bandit extends Character{
	private List<Grabable> inventory = new ArrayList<Grabable>();
	private StartSpace van;
	private MansionArea[][] grid;
	private MansionArea area;


	public Bandit(String name, MansionArea[][] grid) {
		super(name, null, null, null, null);
		this.grid = grid;
		setStartSpace();

		//initializes array so that you can add to any point in it from the beginning
		for(int i=0;i<7;i++){
			inventory.add(null);
		}
	}

	public Bandit(String name){
		super(name, null, null, null, null);

		//initializes array so that you can add to any point in it from the beginning
		for(int i=0;i<7;i++){
			inventory.add(null);
		}
	}

	/**
	 * Sets the spot this bandit will start at and the dimensions
	 */
	public void setStartSpace(){
		van = new StartSpace();
		MansionArea leftMid = grid[grid.length/2][0];
		MansionArea topMid = grid[0][grid[0].length/2];
		MansionArea rightMid = grid[grid.length/2][grid[0].length-1];
		MansionArea botMid = grid[0][grid[0].length/2];


		if(leftMid.getWest()==null){
			van.setLinks(null, leftMid, null, null);
			leftMid.setWest(van);
			this.setFace(Face.EASTERN);
			Dimensions dimens = new Dimensions(10, 10, 50);
			this.setDimensions(dimens);
		}else if(topMid.getNorth()==null){
			van.setLinks(null, null, topMid, null);
			topMid.setNorth(van);
			this.setFace(Face.SOUTHERN);
			Dimensions dimens = new Dimensions(10, 10, 50);
			this.setDimensions(dimens);
		}else if(rightMid.getEast()==null){
			van.setLinks(null, null, null, rightMid);
			rightMid.setEast(van);
			this.setFace(Face.WESTERN);
			Dimensions dimens = new Dimensions(10, 10, 50);
			this.setDimensions(dimens);
		}else if(botMid.getSouth()==null){
			van.setLinks(botMid, null, null, null);
			botMid.setSouth(van);
			this.setFace(Face.NORTHERN);
			Dimensions dimens = new Dimensions(10, 10, 50);
			this.setDimensions(dimens);
		}else
			System.out.println("Cannot add more than 4 players!!");
	}

	public void setGrid(MansionArea[][] grid){
		this.grid = grid;
		setStartSpace();
	}

	public MansionArea getArea(){
		return area;
	}

	public void setArea(MansionArea area){
		this.area = area;
	}


	/**
	 * bandit moves forward
	 */
	public boolean moveForward() {
		Face face = getFace();
		MansionArea area = null;
		if(face==Face.NORTHERN) area = getArea().getNorth();
		else if(face==Face.EASTERN) area =getArea().getEast();
		else if(face==Face.SOUTHERN) area =getArea().getSouth();
		else if(face==Face.WESTERN) area =getArea().getWest();

		if(area!=null) {
			setArea(area);
			return true;
		}
		return false;
	}

	/**
	 * makes the bandit look to the left
	 */
	public void turnLeft(){
		int current = Face.getFaceNum(getFace());
		if(current==0) current = 3;
		else current--;
		setFace(Face.getFace(current));
	}

	/**
	 * makes the bandit look right
	 */
	public void turnRight(){
		int current = Face.getFaceNum(getFace());
		if(current==3) current = 0;
		else current++;
		setFace(Face.getFace(current));
	}

	/**
	 * Adds item to inventory
	 * @param itm
	 * @param slot
	 * @return
	 */
	public boolean addItem(Grabable itm, int slot){
		if(slot>=0&&slot<=6){
			if(inventory.get(slot)==null){
				inventory.set(slot, itm);
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
		if(inventory.size() > slot){
			if(slot>=0&&slot<=5){
				return inventory.get(slot);
				}
			}
		return null;
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
	 * Removes an item to players inventory
	 * @param the item to remove
	 * @return whether the item was removed succesfully
	 */
	public boolean removeItem(Grabable item){
		if(inventory.contains(item)){
			return inventory.remove(item);
		}
		return false;
	}

	@Override
	public String getDescription(){
		return "It's another bandit!";
	}

	@Override
	public String getImage(){
		return "bandit";
	}

}
