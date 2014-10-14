package mansionBandit.gameWorld.areas;

import java.util.ArrayList;
import java.util.List;

import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * This is a type of mansionarea where the bandits start off,
 * There are four start points , meaning we can have a maximum of four players
 * @author Liam De Grey
 *
 */
public class StartSpace implements MansionArea {
	private MansionArea north, south, east, west;
	private List<GameMatter> items = new ArrayList<GameMatter>();
	private String wallTexture, ceilingTexture, floorTexture;

	@Override
	public void setLinks(MansionArea north, MansionArea east, MansionArea south, MansionArea west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	@Override
	public MansionArea getNorth() {
		return north;
	}

	@Override
	public MansionArea getSouth() {
		return south;
	}

	@Override
	public MansionArea getWest() {
		return west;
	}

	@Override
	public MansionArea getEast() {
		return east;
	}

	@Override
	public void addItem(GameMatter itm) {
		items.add(itm);
	}

	@Override
	public List<GameMatter> getItems() {
		return items;
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

	public Bandit getBandit(){
		for(GameMatter matter: items){
			if(matter instanceof Bandit){
				return (Bandit)matter;
			}
		}
		return null;
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
	public boolean equals(Object o){
		if(o instanceof StartSpace){
			if (o==this)
				return true;//if(((Room)o).getName()
		}
		return false;
	}

	public void setTextures(String wall, String ceiling, String floor){
		this.wallTexture = wall;
		this.ceilingTexture = ceiling;
		this.floorTexture = floor;
	}

	@Override
	public boolean removeItem(GameMatter itm) {
		return false;
	}

}
