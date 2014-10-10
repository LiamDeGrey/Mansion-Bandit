package mansionBandit.gameWorld.areas;

import java.util.ArrayList;
import java.util.List;

import mansionBandit.factory.RoomFactory;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * This class makes the rooms and fits them together depending on the amount of rooms wanted
 * by the user
 * @author Liam De Grey
 *
 */
public class Grid {
	private List<Room> rooms = new ArrayList<Room>();
	private List<GameMatter> allItems = new ArrayList<GameMatter>();
	private MansionArea[][] grid;
	private RoomFactory factory;

	public Grid(int numRooms){
		factory = new RoomFactory();
		int rand = (int) (Math.random()*numRooms+numRooms);
		makeRooms(numRooms);
		setLinks();
	}

	public void makeRooms(int numRooms){
		for(int i=0; i<numRooms; i++){
			rooms.add(new Room());
		}
	}


	/**
	 * Sets the layout of the rooms and adds links to the hallways and
	 * the rooms
	 */
	public void setLinks(){
		int rows = (int)Math.sqrt(rooms.size())+2;
		int cols = rows;

		grid = new MansionArea[rows][cols];//A grid showing [rows][columns] of all the rooms

		int extraSpace = (rows * cols) - rooms.size();//find out how many free spaces there are in the grid

		int rand;


		int roomNum = 0;
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){

				if(extraSpace>0) {
					rand = (int)(Math.random() * 3 + 1);//either 1 , 2 or 3
					if(rand==1&&roomNum<rooms.size()) {
						grid[i][j] = rooms.get(roomNum);//put room in grid
						roomNum++;
					}else {
						grid[i][j] = new Hallway();
						extraSpace--;
					}
				}else {
					grid[i][j] = rooms.get(roomNum);
					roomNum++;
				}
			}
		}

		for(int i=0; i<grid.length; i++){//These loops go through each grid space and set links,
			for(int j=0; j<grid[0].length; j++){ //Hallways go horizontal across the mansion
				MansionArea current = grid[i][j]; //Rooms have 4 doors if not next to a wall
				MansionArea n = null, e = null, s = null, w = null;
				if(current instanceof Room) {
					if(i!=0)
						n = grid[i-1][j];
					if(i!=grid.length-1)
						s = grid[i+1][j];
				}else {
					if((j==0&&i>0)||(i!=0&&grid[i-1][j] instanceof Room))
						n = grid[i-1][j];
					if((j==grid.length-1&&i<grid.length-1)||(i!=grid.length-1&&grid[i+1][j] instanceof Room))
						s = grid[i+1][j];
				}
				if(j!=0)
					w = grid[i][j-1];
				if(j!=grid[0].length-1)
					e = grid[i][j+1];
				current.setLinks(n, e, s, w);
				factory.populateRoom(current);
			}
		}
	}

	public MansionArea[][] getGrid(){
		return grid;
	}

}
