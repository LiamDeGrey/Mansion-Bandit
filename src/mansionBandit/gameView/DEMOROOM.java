package mansionBandit.gameView;

public class DEMOROOM {
	
	public static int N = 0;
	public static int W = 3;
	public static int E = 1;
	public static int S = 2;
	
	
	public DEMOROOM(){
		wall = "wall1";
		floor = "carpet1";
		ceiling = "ceiling1";
		n = new DEMOWALL();
		s = new DEMOWALL();
		e = new DEMOWALL();
		w = new DEMOWALL();
		top = new DEMOWALL();
		bottom = new DEMOWALL();
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
	
	public DEMOWALL getN() {
		return n;
	}

	public DEMOWALL getS() {
		return s;
	}

	public DEMOWALL getE() {
		return e;
	}

	public DEMOWALL getW() {
		return w;
	}

	public DEMOWALL getTop() {
		return top;
	}

	public DEMOWALL getBottom() {
		return bottom;
	}
	
}
