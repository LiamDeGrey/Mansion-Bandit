package mansionBandit.gameView;

public class DEMOOBJECT {
	static String face = "testFace";
	private int x, y;
	
	public DEMOOBJECT(){
		x = (int) (100 * Math.random());
		y = (int) (100 * Math.random());
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String getFace(){
		return face;
	}
}
