package mansionBandit.gameWorld.rooms;

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

}
