package mansionBandit.gameWorld.areas;

import java.util.List;

import mansionBandit.gameWorld.matter.GameMatter;

/**
 * MansionArea is simply a space within the mansion, either a room or hallway
 * @author Liam De Grey
 *
 */
public interface MansionArea {
	
	/*
	 * Sets the links between the mansionArea and other MansionAreas
	 */
	public void setLinks(MansionArea north, MansionArea east, MansionArea south, MansionArea west);
	
	/*
	 * returns the mansionArea above the current MansionArea, 
	 * returns null if there is nothing there
	 */
	public MansionArea getNorth();
	
	/*
	 * returns the mansionArea below the current MansionArea, 
	 * returns null if there is nothing there
	 */
	public MansionArea getSouth();
	
	/*
	 * returns the mansionArea left of the current MansionArea, 
	 * returns null if there is nothing there
	 */
	public MansionArea getWest();
	
	/*
	 * returns the mansionArea right of the current MansionArea, 
	 * returns null if there is nothing there
	 */
	public MansionArea getEast();
	
	//TODO hey liam this is all new:<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	//TODO also as a note, use the /** comments for methods and stuff that you want 
	//included in javadocs and the context menu in eclipse. It will automatically 
	//pick out params and stuff for you to comment on too.
	
	/**
	 * adds an item to the area
	 * @param itm the item to add to the room
	 */
	public void addItem(GameMatter itm);
	
	/**
	 * gets a list of all items in the area
	 * @return the list of items
	 */
	public List<GameMatter> getItems();
	
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
	
	//TODO>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
