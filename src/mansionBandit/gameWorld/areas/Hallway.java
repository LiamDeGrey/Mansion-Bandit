package mansionBandit.gameWorld.areas;

/**
 * This class is a hallway in the mansion and has links to other hallways
 * and or rooms
 * 
 * A hallway connects to any surrounding area unless it is a wall. For example
 * a hallway next to another hallway will always be connected. Hallways that aren't
 * next to walls will have four entries/exits.
 * @author Liam De Grey
 *
 */
public class Hallway implements MansionArea{
	private MansionArea north, east, south, west;
	
	public void setLinks(MansionArea north, MansionArea east, MansionArea south, MansionArea west){
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}
	
	public MansionArea getNorth(){
		return north;
	}
	
	public MansionArea getEast(){
		return east;
	}
	
	public MansionArea getSouth(){
		return south;
	}
	
	public MansionArea getWest(){
		return west;
	}
	
	@Override
	public boolean equals(Object o){
		MansionArea compared = (MansionArea)o;
		if(compared.getNorth()==getNorth()
				&&compared.getEast()==getEast()
				&&compared.getSouth()==getSouth()
				&&compared.getWest()==getWest())
			return true;
		return false;
	}

}
