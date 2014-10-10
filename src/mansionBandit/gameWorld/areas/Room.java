package mansionBandit.gameWorld.areas;

import java.util.ArrayList;
import java.util.List;

import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * This is a room object that has links to other areas
 * and shows what objects are held in the room
 * @author Liam De Grey
 *
 */
public class Room implements MansionArea{
	private MansionArea north, east, south, west;
	private List<GameMatter> items = new ArrayList<GameMatter>();
	private String wallTexture, ceilingTexture, floorTexture;
	private Door nDoor, eDoor, sDoor, wDoor;

	public Room(String wall, String ceiling, String floor){
		this.wallTexture = wall;
		this.ceilingTexture = ceiling;
		this.floorTexture = floor;
	}
	
	private void makeDoors() {
		if(north!=null) {
			nDoor = new Door("NDoor", false, "keyA");
			north.setDoor(nDoor);
		}
		if(east!=null) {
			eDoor = new Door("EDoor", false, "keyA");
			east.setDoor(eDoor);
		}
		if(south!=null) {
			sDoor = new Door("SDoor", false, "keyA");
			south.setDoor(sDoor);
		}
		if(west!=null) {
			wDoor = new Door("WDoor", false, "keyA");
			west.setDoor(wDoor);
		}
	}

	/*
	 * For testing purposes primarily
	 */
	public Room(){

	}

	public String getWallTexture(){
		return wallTexture;
	}

	public String getCeilingTexture(){
		return ceilingTexture;
	}

	public String getFloorTexture(){
		return floorTexture;
	}

	public void setLinks(MansionArea north, MansionArea east, MansionArea south, MansionArea west){
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
		makeDoors();
	}

	public void addItem(GameMatter itm){
		items.add(itm);
	}

	public List<GameMatter> getItems(){
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

	@Override
	public void setNorth(MansionArea area) {
		this.north = area;
	}

	@Override
	public void setSouth(MansionArea area) {
		this.south = area;
	}

	@Override
	public void setWest(MansionArea area) {
		this.west = area;
	}

	@Override
	public void setEast(MansionArea area) {
		this.east = area;
	}

	@Override
	public void setDoor(Door d) {
		if(d.getName().equals("NDoor"))
			sDoor = d;
		else if(d.getName().equals("EDoor"))
			wDoor = d;
		else if(d.getName().equals("SDoor"))
			nDoor = d;
		else if(d.getName().equals("WDoor"))
			eDoor = d;
	}

}
