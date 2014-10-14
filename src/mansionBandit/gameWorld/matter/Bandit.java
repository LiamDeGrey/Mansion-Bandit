package mansionBandit.gameWorld.matter;

import javax.swing.JOptionPane;

import mansionBandit.factory.RoomFactory;
import mansionBandit.factory.RoomPainter;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.StartSpace;
import mansionBandit.gameWorld.main.Player;


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
	private int i, j;
	private Van van;

	private MansionArea area;
	private int[] adjacentGrid = new int[2];

	public Bandit(String name, MansionArea[][] grid, Player player) {
		super(name, null, null, null, null);
		this.grid = grid;
		setStartSpace();
		initialiseInventory();
	}

	public Bandit(String name, Player player){
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

		if(getName().equals("1")){
			start.setLinks(null, leftMid, null, null);
			//leftMid.setWest(start);
			this.setFace(Face.EASTERN);
			adjacentGrid[0]=grid.length/2;
			adjacentGrid[1]=0;
		}else if(getName().equals("2")){
			start.setLinks(null, null, topMid, null);
			//topMid.setNorth(start);
			this.setFace(Face.SOUTHERN);
			adjacentGrid[0]=0;
			adjacentGrid[1]=grid.length/2;
		}else if(getName().equals("3")){
			start.setLinks(null, null, null, rightMid);
			//rightMid.setEast(start);
			this.setFace(Face.WESTERN);
			adjacentGrid[0]=grid.length/2;
			adjacentGrid[1]=grid[0].length-1;
		}else if(getName().equals("4")){
			start.setLinks(botMid, null, null, null);
			//botMid.setSouth(start);
			this.setFace(Face.NORTHERN);
			adjacentGrid[0]=grid.length-1;
			adjacentGrid[1]=grid[0].length/2;
		}


		/*if(leftMid.getWest()==null){
			start.setLinks(null, leftMid, null, null);
			//leftMid.setWest(start);
			this.setFace(Face.EASTERN);
			adjacentGrid[0]=grid.length/2;
			adjacentGrid[1]=0;
		}else if(topMid.getNorth()==null){
			start.setLinks(null, null, topMid, null);
			//topMid.setNorth(start);
			this.setFace(Face.SOUTHERN);
			adjacentGrid[0]=0;
			adjacentGrid[1]=grid.length/2;
		}else if(rightMid.getEast()==null){
			start.setLinks(null, null, null, rightMid);
			//rightMid.setEast(start);
			this.setFace(Face.WESTERN);
			adjacentGrid[0]=grid.length/2;
			adjacentGrid[1]=grid[0].length-1;
		}else if(botMid.getSouth()==null){
			start.setLinks(botMid, null, null, null);
			//botMid.setSouth(start);
			this.setFace(Face.NORTHERN);
			adjacentGrid[0]=grid.length-1;
			adjacentGrid[1]=grid[0].length/2;
		}else {
			System.out.println("Cannot add more than 4 players!!");
			return;
		}*/

		setArea(-1,-1);
		start.addItem(this);
		Dimensions dimens = new Dimensions(10, 10, 50);
		this.setDimensions(dimens);
		RoomFactory rf = new RoomFactory();
		rf.populateRoom(start);

		van = new Van(getName()+"van", Face.opposite(getFace()), this);
		start.addItem(van);
		Door exit = new Door(getName()+"startDoor", getFace(), new Dimensions(50, 100, 70), false);
		Door entry = new Door(getName()+"toStart", Face.opposite(getFace()), new Dimensions(50, 100, 70), false);
		start.addItem(exit);
		grid[adjacentGrid[0]][adjacentGrid[1]].addItem(entry);
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

	public MansionArea[][] getGrid(){
		return grid;
	}

	public void setGrid(MansionArea[][] grid){
		this.grid = grid;
		setStartSpace();
	}

	public void setGridStart(MansionArea[][] grid){
		this.grid = grid;
		setStartSpace();
	}

	public void setArea(int i, int j) {
		this.i = i;
		this.j = j;
		if(i==-1||j==-1){
			area = start;
		}else{
			area = grid[i][j];
		}
	}

	public MansionArea getArea(){
		if(i==-1||j==-1)
			return start;
		return grid[i][j];
	}

	public Face getDirectionToStart(){
		if(start.getNorth()!=null){
			return Face.SOUTHERN;
		}else if(start.getWest()!=null){
			return Face.EASTERN;
		}else if(start.getSouth()!=null){
			return Face.NORTHERN;
		}else if(start.getEast()!=null){
			return Face.WESTERN;
		}
		return null;
	}


	/**
	 * bandit moves forward
	 */
	public boolean moveForward() {
		int newi = 0, newj= 0;
		Face face = getFace();
		MansionArea next = null;

		if(getRoomCoords(getArea())[0]==getAdjacentGrid()[0]
				&&getRoomCoords(getArea())[1]==getAdjacentGrid()[1]){
			if(getDirectionToStart()==face){
				getArea().removeItem(this);
				setArea(-1,-1);
				start.addItem(this);
			}
		}

		if(face==Face.NORTHERN) {
			if(getArea().getNorth()==null){
				System.out.println("YOU CAN'T GO THERE!");
				return false;
			}
			next = getArea().getNorth();
		}
		else if(face==Face.EASTERN) {
			if(getArea().getEast()==null){
				System.out.println("YOU CAN'T GO THERE!");
				return false;
			}
			next =getArea().getEast();
		}
		else if(face==Face.SOUTHERN) {
			if(getArea().getSouth()==null){
				System.out.println("YOU CAN'T GO THERE!");
				return false;
			}
			next =getArea().getSouth();
		}
		else if(face==Face.WESTERN) {
			if(getArea().getWest()==null){
				System.out.println("YOU CAN'T GO THERE!");
				return false;
			}
			next =getArea().getWest();
		}

		newi = getRoomCoords(next)[0];
		newj = getRoomCoords(next)[1];



		if(next!=null) {
			if(next.equals(start)){
				getArea().removeItem(this);
				setArea(-1,-1);
				start.addItem(this);
			}else{
				getArea().removeItem(this);
				setArea(newi, newj);
				getArea().addItem(this);
			}
			return true;
		}
		return false;
	}

	public int[] getRoomCoords(MansionArea room) {
		int[] coords = {-2, -2};
		if(room.equals(start)) {
			coords[0] = -1;
			coords[1] = -1;
		}else {
			for(int i=0; i<grid.length; i++) {
				for(int j=0; j<grid[0].length; j++) {
					if(room.equals(grid[i][j])) {
						coords[0] = i;
						coords[1] = j;
					}
				}
			}
		}
		return coords;
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
		getArea().removeItem(this);
		setArea(-1, -1);

		JOptionPane.showMessageDialog(
				null,
				"DEAD. Get out of here filthy Bandit!!","DEAD" , JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public String getDescription(){
		return "It's another bandit!";
	}

	@Override
	public String getImage(){
		return "player1";
	}

	/**
	 * sets the 6 inventory slots to null at the start
	 */
	public void initialiseInventory(){
		for(int i=0; i<inventory.length; i++){
			inventory[i] = null;
		}
	}

	@Override
	public Dimensions getDimensions(){
		return new Dimensions(40, 40, 70);
	}

}
