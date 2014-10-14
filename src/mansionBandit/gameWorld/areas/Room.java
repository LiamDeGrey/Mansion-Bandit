package mansionBandit.gameWorld.areas;

import java.util.ArrayList;
import java.util.List;

import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;
import mansionBandit.gameWorld.matter.Guard;

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
	private String name;


	public Room(String name){
		addGuard();
		this.name = name;
	}

	private void addGuard(){
		int rand = (int)(Math.random()*10)+1;
		if(rand==5){
			Guard guard = new Guard("guard", Face.FLOOR, this);
			addItem(guard);
		}
	}

	public boolean removeItem(GameMatter itm){
		if(itm!=null&&items.contains(itm)){
			items.remove(itm);
			return true;
		}
		return false;
	}

	public void setTextures(String wall, String ceiling, String floor){
		this.wallTexture = wall;
		this.ceilingTexture = ceiling;
		this.floorTexture = floor;
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
		if(o != null && o instanceof Room){
			return ((Room)o).getName().equals(getName());
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
	public String getName() {
		return name;
	}

}
