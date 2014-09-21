package mansionBandit.gameWorld.matter;


/**
 * Declares the six sides of each room
 * @author Liam De Grey
 *
 */
public enum Face {
	NORTHERN,
	EASTERN,
	SOUTHERN,
	WESTERN,
	BOTTOM,
	TOP;
	
	/*
	 * Gets the face when given a number between 0 and 5 inclusive
	 */
	public static Face getFace(int place){
		if(place==0) return NORTHERN;
		else if(place==1) return EASTERN;
		else if(place==2) return SOUTHERN;
		else if(place==3) return WESTERN;
		else if(place==4) return BOTTOM;
		else if(place==5) return TOP;
		return null;
	}
	
	
}
