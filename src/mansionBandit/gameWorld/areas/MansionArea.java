package mansionBandit.gameWorld.areas;

import java.io.Serializable;
import java.util.List;

import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * MansionArea is simply a space within the mansion, either a room or hallway
 * @author Liam De Grey
 *
 */
public interface MansionArea extends Serializable {


	/**
	 * Sets the surrounding areas and makes links
	 * @param north
	 * @param east
	 * @param south
	 * @param west
	 */
	public void setLinks(MansionArea north, MansionArea east, MansionArea south, MansionArea west);


	/**
	 * @return the northern link to a MansionArea
	 */
	public MansionArea getNorth();

	/**
	 *
	 * @return the southern link to a mansionarea
	 */
	public MansionArea getSouth();

	/**
	 *
	 * @return The western link to a mansionArea
	 */
	public MansionArea getWest();

	/**
	 *
	 * @return the Eastern link to mansionArea
	 */
	public MansionArea getEast();


	public void setNorth(MansionArea area);


	public void setSouth(MansionArea area);


	public void setWest(MansionArea area);


	public void setEast(MansionArea area);

	public String getName();


	/**
	 * adds an item to the area
	 * @param itm the item to add to the room
	 */
	public void addItem(GameMatter itm);

	/**\
	 * Removes item
	 */
	public boolean removeItem(GameMatter itm);

	/**
	 * gets a list of all items in the area
	 * @return the list of items
	 */
	public List<GameMatter> getItems();
	
	/**
	 * replaces all items in the area with a new list
	 * @param newItems the new list of items for the area
	 */
	public void setItems(List<GameMatter> newItems);

	/**
	 * gets the texture of the walls in this area
	 * @return String name of texture
	 */
	public String getWallTexture();

	/**
	 * gets the texture of the ceiling in this area
	 * @return String name of texture
	 */
	public String getCeilingTexture();

	/**
	 * gets the texture of the floor in this area
	 * @return String name of texture
	 */
	public String getFloorTexture();

	public void setTextures(String wall, String ceiling, String floor);

}
