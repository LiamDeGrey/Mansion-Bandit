package mansionBandit.gameWorld.matter;

import mansionBandit.factory.RoomFactory;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.StartSpace;


/**
 * A Bandit is any person that has entered the game to play
 * as a thief
 * @author Liam De Grey
 *
 */
public class Bandit extends Character{
	private Grabable[] inventory = new Grabable[7];
	private StartSpace start;
	private MansionArea[][] grid;
	private int x = 2, y = 1;
	private MansionArea area;
	private int[] adjacentGrid = new int[2];

	public void setArea(int x, int y){
		this.x = x;
		this.y = y;
	}


	public Bandit(String name, MansionArea[][] grid) {
		super(name, null, null, null, null);
		this.grid = grid;
		setStartSpace();
		initialiseInventory();
	}

	public Bandit(String name){
		super(name, null, null, null, null);
		initialiseInventory();
	}

	/**
	 * Sets the spot this bandit will start at and the dimensions
	 */
	public void setStartSpace(){
		start = new StartSpace();
		MansionArea leftMid = grid[grid.length/2][0];
		MansionArea topMid = grid[0][grid[0].length/2];
		MansionArea rightMid = grid[grid.length/2][grid[0].length-1];
		MansionArea botMid = grid[grid.length-1][grid[0].length/2];


		if(leftMid.getWest()==null){
			start.setLinks(null, leftMid, null, null);
			leftMid.setWest(start);
			this.setFace(Face.EASTERN);
			adjacentGrid[0]=grid.length/2;
			adjacentGrid[1]=0;
		}else if(topMid.getNorth()==null){
			start.setLinks(null, null, topMid, null);
			topMid.setNorth(start);
			this.setFace(Face.SOUTHERN);
			adjacentGrid[0]=0;
			adjacentGrid[1]=grid.length/2;
		}else if(rightMid.getEast()==null){
			start.setLinks(null, null, null, rightMid);
			rightMid.setEast(start);
			this.setFace(Face.WESTERN);
			adjacentGrid[0]=grid.length/2;
			adjacentGrid[1]=grid[0].length-1;
		}else if(botMid.getSouth()==null){
			start.setLinks(botMid, null, null, null);
			botMid.setSouth(start);
			this.setFace(Face.NORTHERN);
			adjacentGrid[0]=grid.length-1;
			adjacentGrid[1]=grid[0].length/2;
		}else {
			System.out.println("Cannot add more than 4 players!!");
			return;
		}

		Dimensions dimens = new Dimensions(10, 10, 50);
		this.setDimensions(dimens);
		RoomFactory rf = new RoomFactory();
		rf.populateRoom(start);
	}

	/**
	 * This method gets the adjacent grid space to the startSpace(van)
	 * and returns the coords in an array
	 * @return array containing 2 ints, i and j
	 */
	public int[] getAdjacentGrid() {
		return adjacentGrid;
	}

	public Van getVan() {
		for(GameMatter matter : start.getItems()) {
			if(matter instanceof Van)
				return (Van) matter;
		}
		return null;
	}

	public void setGrid(MansionArea[][] grid){
		this.grid = grid;
	}

	public void setGridStart(MansionArea[][] grid){
		this.grid = grid;
		setStartSpace();
	}

	public MansionArea getArea(){
		return grid[x][y];
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
		if(face==Face.NORTHERN) {
			y += 1;
			area = getArea().getNorth();
		}
		else if(face==Face.EASTERN) {
			x += 1;
			area =getArea().getEast();
		}
		else if(face==Face.SOUTHERN) {
			y -= 1;
			area =getArea().getSouth();
		}
		else if(face==Face.WESTERN) {
			x -= 1;
			area =getArea().getWest();
		}

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
				inventory[slot] = itm;
				return true;
		}

		return false;
	}


	/**
	 * returns item at specified slot in players inventory
	 * @param slot
	 * @return
	 */
	public Grabable getItem(int slot){
		if(inventory.length > slot){
			if(slot>=0&&slot<=6){
				return inventory[slot];
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
		for(int i=0; i<inventory.length; i++){
			if(inventory[i]==null){
				inventory[i] = item;
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes an item to players inventory
	 * @param the item to remove
	 * @return whether the item was removed succesfully
	 */
	public boolean removeItem(int slot){
		if(inventory[slot]!=null){
			inventory[slot] =null;
			return true;
		}
		return false;
	}

	public boolean removeItem(Grabable itm) {
		for(int i=0; i<inventory.length; i++) {
			if(itm.equals(inventory[i])) {
				inventory[i] = null;
				return true;
			}
		}
		return false;
	}

	//TODO Drop item


	/**
	 * Kill the bandit drop their items and send back to van
	 */
	@Override
	public void kill(){
		for(int i=0; i<inventory.length; i++){
			if(inventory[i]!=null){
				area.addItem(inventory[i]);
			}
		}
		System.out.println("DEAD. Get out of here filthy Bandit!!");
		start.addItem(this);
		area = start;
	}

	@Override
	public String getDescription(){
		return "It's another bandit!";
	}

	@Override
	public String getImage(){
		return "bandit";
	}

	/**
	 * sets the 6 inventory slots to null at the start
	 */
	public void initialiseInventory(){
		for(int i=0; i<inventory.length; i++){
			inventory[i] = null;
		}
	}

}
