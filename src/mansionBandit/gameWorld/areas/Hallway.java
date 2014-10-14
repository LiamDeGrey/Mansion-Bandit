package mansionBandit.gameWorld.areas;

import java.util.ArrayList;
import java.util.List;

import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.GameMatter;

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
	private List<GameMatter> items = new ArrayList<GameMatter>(); //need for chandaliers and stuff like that?
	private String wallTexture, ceilingTexture, floorTexture;
	private String name;

	public Hallway(String name){
		this.wallTexture = "hallway";
		this.ceilingTexture = "hallwayC";
		this.floorTexture = "hallwayF";
		this.name = name;
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

	public void addItem(GameMatter itm){
		items.add(itm);
	}

	public List<GameMatter> getItems(){
		return items;
	}
	
	public void setItems(List<GameMatter> newItems) {
		items = newItems;
	}

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
		if(o instanceof Hallway){
			return ((Hallway)o).getName().equals(getName());
		}
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
	public void setTextures(String wall, String ceiling, String floor) {
		//do nothing
	}

	@Override
	public boolean removeItem(GameMatter itm) {
		return items.remove(itm);
	}

	@Override
	public String getName() {
		return name;
	}

}
