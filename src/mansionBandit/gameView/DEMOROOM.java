package mansionBandit.gameView;

public class DEMOROOM {
	
	public DEMOROOM(){
		wall = "wall1";
		floor = "carpet1";
		ceiling = "ceiling1";
	}
	
	public String getWall(){
		return wall;
	}
	
	public String getCeiling(){
		return ceiling;
	}
	
	public String getFloor(){
		return floor;
	}
	
	private DEMOWALL n,s,e,w,top,bottom;
	private String wall,floor,ceiling;
	
}
