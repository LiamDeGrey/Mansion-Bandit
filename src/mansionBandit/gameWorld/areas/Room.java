package mansionBandit.gameWorld.areas;

import java.util.ArrayList;
import java.util.List;

import mansionBandit.gameWorld.items.Item;

/**
 * This is a room object that has links to other areas
 * and shows what objects are held in the room
 * @author Liam De Grey
 *
 */
public class Room implements MansionArea{
	private MansionArea north, east, south, west;
	private List<Item> items = new ArrayList<Item>();
	
	public Room(){
	}
	
	public void setLinks(MansionArea north, MansionArea east, MansionArea south, MansionArea west){
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}
	
	public void addItem(Item itm){
		items.add(itm);
	}
	
	public List<Item> getItems(){
		return items;
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
