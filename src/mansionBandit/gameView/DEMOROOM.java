package mansionBandit.gameView;

public class DEMOROOM {
	
	public static final int N = 0;
	public static final int W = 3;
	public static final int E = 1;
	public static final int S = 2;
	public static final int C = 4;
	public static final int F = 5;
	
	private int direction;
	
	public static int getLeft(int dir){
		switch(dir){
		case N:
			return W;
		case E:
			return N;
		case S:
			return E;
		case W:
			return S;
		}
		return 0;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void setDirection(int s){
		direction = s;
	}
	
	public DEMOROOM(){
		direction = N;
		wall = "wall1";
		floor = "carpet1";
		ceiling = "ceiling1";
		n = new DEMOWALL();
		s = new DEMOWALL();
		e = new DEMOWALL();
		w = new DEMOWALL();
		top = new DEMOWALL();
		bottom = new DEMOWALL(true);
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
	
	
	public DEMOWALL getWall(int wall){
		switch (wall){
		case N:
			return n;
		case S:
			return s;
		case W:
			return w;
		case E:
			return e;
		case C:
			return top;
		case F:
			return bottom;
		}
		return null;
	}
	
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
