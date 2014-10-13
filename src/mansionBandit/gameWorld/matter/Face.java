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
	FLOOR,
	CEILING;

	/**
	 * Gets the face when given a number between 0 and 5 inclusive
	 */
	public static Face getFace(int place){
		if(place==0) return NORTHERN;
		else if(place==1) return EASTERN;
		else if(place==2) return SOUTHERN;
		else if(place==3) return WESTERN;
		else if(place==4) return FLOOR;
		else if(place==5) return CEILING;
		return null;
	}

	/**
	 * Gets the placing of a face when given a face
	 */
	public static int getFaceNum(Face face){
		if(face==NORTHERN) return 0;
		else if(face==EASTERN) return 1;
		else if(face==SOUTHERN) return 2;
		else if(face==WESTERN) return 3;
		else if(face==FLOOR) return 4;
		else if(face==CEILING) return 5;
		return -1;
	}

	public static Face getLeft(Face face){
		switch (face){
		case NORTHERN:
			return WESTERN;
		case WESTERN:
			return SOUTHERN;
		case SOUTHERN:
			return EASTERN;
		case EASTERN:
			return NORTHERN;
		}
		return null;
	}
	
	public static Face opposite(Face face) {
		switch(face) {
		case NORTHERN:
			return SOUTHERN;
		case WESTERN:
			return EASTERN;
		case SOUTHERN:
			return NORTHERN;
		case EASTERN:
			return WESTERN;
		}
		return null;
	}

}
