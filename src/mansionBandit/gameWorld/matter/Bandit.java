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

	private static final long serialVersionUID = 6391083299379880540L;

	private Grabable[] inventory = new Grabable[7];
	private StartSpace start;
	public MansionArea[][] grid;
	private boolean[][] visited;
	private int i, j;
	private Van van;
	private int id;

	private int[] adjacentGrid = new int[2];

	public Bandit(String name, int id, MansionArea[][] grid, Player player) {
		super(name, null, null, null, null);
		this.grid = grid;
		this.id = id;
		initialiseVisited();
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

		if(getId()==1){
			start.setLinks(null, leftMid, null, null);
			//leftMid.setWest(start);
			this.setFace(Face.EASTERN);
			adjacentGrid[0]=grid.length/2;
			adjacentGrid[1]=0;
		}else if(getId()==2){
			start.setLinks(null, null, topMid, null);
			//topMid.setNorth(start);
			this.setFace(Face.SOUTHERN);
			adjacentGrid[0]=0;
			adjacentGrid[1]=grid.length/2;
		}else if(getId()==3){
			start.setLinks(null, null, null, rightMid);
			//rightMid.setEast(start);
			this.setFace(Face.WESTERN);
			adjacentGrid[0]=grid.length/2;
			adjacentGrid[1]=grid[0].length-1;
		}else if(getId()==4){
			start.setLinks(botMid, null, null, null);
			//botMid.setSouth(start);
			this.setFace(Face.NORTHERN);
			adjacentGrid[0]=grid.length-1;
			adjacentGrid[1]=grid[0].length/2;
		}

		setArea(-1,-1);
		start.addItem(this);
		Dimensions dimens = new Dimensions(10, 10, 50);
		this.setDimensions(dimens);
		RoomFactory rf = new RoomFactory();
		rf.populateRoom(start);

		van = new Van(getId()+"van", Face.opposite(getFace()), this);
		start.addItem(van);
		Door exit = new Door(getId()+"startDoor", getFace(), false);
		Door entry = new Door(getId()+"toStart", Face.opposite(getFace()), false);
		start.addItem(exit);
		for(GameMatter itm : grid[adjacentGrid[0]][adjacentGrid[1]].getItems()){
			if(itm.getFace()==Face.opposite(getFace())){
				grid[adjacentGrid[0]][adjacentGrid[1]].removeItem(itm);
			}
		}
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

	public void giveId(int id){
		this.id = id;
	}

	public MansionArea[][] getGrid(){
		return grid;
	}

	public void setGrid(MansionArea[][] grid){
		this.grid = grid;
		initialiseVisited();
		setStartSpace();
	}

	public void setGridStart(MansionArea[][] grid){
		this.grid = grid;
		initialiseVisited();
		setStartSpace();
	}

	public void setArea(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getId(){
		return id;
	}

	public void setAreaInGrid(MansionArea area, int i, int j) {
		this.grid[i][j] = area;
	}

	public MansionArea getArea(){
		if(i==-1||j==-1)
			return start;
		return grid[i][j];
	}

	/**
	 * returns the direction the bandit must be facing in
	 * order to move into the start room
	 * @return
	 */
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
				//System.out.println("YOU CAN'T GO THERE!");
				return false;
			}
			next = getArea().getNorth();
		}
		else if(face==Face.EASTERN) {
			if(getArea().getEast()==null){
				//System.out.println("YOU CAN'T GO THERE!");
				return false;
			}
			next =getArea().getEast();
		}
		else if(face==Face.SOUTHERN) {
			if(getArea().getSouth()==null){
				//System.out.println("YOU CAN'T GO THERE!");
				return false;
			}
			next =getArea().getSouth();
		}
		else if(face==Face.WESTERN) {
			if(getArea().getWest()==null){
				//System.out.println("YOU CAN'T GO THERE!");
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
				//TODO - add in check for locked door
				if(checkForLockedDoor())
					return false;
				visited[newi][newj] = true;
				getArea().removeItem(this);
				unlockBothSides();
				setArea(newi, newj);
				checkForGuard();
				getArea().addItem(this);
			}
			return true;
		}
		return false;
	}

	private void checkForGuard(){
		for(GameMatter itm : getArea().getItems()){
			if(itm instanceof Guard){
				((Guard)itm).wakeUp((Guard)itm);
			}
		}
	}


	/**
	 * unlocks the opposite side of the door that
	 * has been unlocked
	 */
	public void unlockBothSides(){
		int i = getRoomCoords(getArea())[0];
		int j = getRoomCoords(getArea())[1];

		if(getFace()==Face.NORTHERN)i--;
		else if(getFace()==Face.EASTERN)j++;
		else if(getFace()==Face.SOUTHERN)i++;
		else if(getFace()==Face.WESTERN)j--;

		if(i==-1||j==-1){
			i = adjacentGrid[0];
			j = adjacentGrid[1];
		}


		for(GameMatter itm : grid[i][j].getItems()){
			if(itm.getFace()==Face.opposite(getFace())
					&&itm instanceof Door){
				((Door)itm).unlock();
			}
		}

	}

	/**
	 * Checks if the door the bandit is trying to go through is locked
	 * @return
	 */
	private boolean checkForLockedDoor(){
		for(GameMatter itm : getArea().getItems()){
			if(itm.getFace()==getFace()
					&&itm instanceof Door){
				if(((Door)itm).isLocked()){
					//System.out.println("Door is locked");
					JOptionPane.showMessageDialog(
							null,
							"This door is locked, try find the right key", "Locked Door", JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * gets the grid containing boolean values regarding whether
	 * the bandit has been to these coordinates or not
	 * @return
	 */
	public boolean[][] getVisited(){
		return visited;
	}

	/**
	 * Takes a mansionArea and returns its coordinates in an array
	 * @param room
	 * @return
	 */
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
		if(checkForFirePlace(itm))
			return false;
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
	 * Checks if the user has tried to pick up a fireplace,
	 * if yes then will burn all current items in inventory
	 */
	public boolean checkForFirePlace(Grabable itm){
		if(itm.getName().equals("Fireplace")){
			for(int i=0; i<inventory.length; i++){
				inventory[i] = null;
			}
			JOptionPane.showMessageDialog(
					null,
					"Why would you try pick up a firePlace?! You just burnt all your inventory items!", "Idiot", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * Adds an item to players inventory
	 * @param the item to add
	 * @return whether the item was added succesfully
	 */
	public boolean addItem(Grabable item){
		if(checkForFirePlace(item))
			return false;
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
				getArea().addItem(inventory[i]);
				inventory[i]=null;
			}
		}
		//System.out.println("DEAD. Get out of here filthy Bandit!!");
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
		return "player"+getId();
	}

	/**
	 * sets the 6 inventory slots to null at the start
	 */
	public void initialiseInventory(){
		for(int i=0; i<inventory.length; i++){
			inventory[i] = null;
		}
	}

	private void initialiseVisited() {
		visited = new boolean[grid.length][grid[0].length];
		for(int i=0; i<visited.length; i++) {
			for(int j=0; j<visited[0].length; j++) {
				visited[i][j] = false;
			}
		}
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Bandit){
			return((Bandit) o).getId()==getId();
		}
		return false;
	}

	/**
	 * Returns dimensions of the bandit depending on which player
	 * it is
	 */
	@Override
	public Dimensions getDimensions(){
		if(getId()==1){
			return new Dimensions(30, 30, 70);
		}else if(getId()==2){
			return new Dimensions(30, 70, 70);
		}else if(getId()==3){
			return new Dimensions(70, 70, 70);
		}else if(getId()==4){
			return new Dimensions(70, 30, 70);
		}
		return null;
	}

}
