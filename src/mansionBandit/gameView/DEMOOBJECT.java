package mansionBandit.gameView;

import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

public class DEMOOBJECT {
	//wrap demo object around liams object
	static String face = "testFace";
	private int x, y;
	private int size;
	private GameMatter object;
	
	/* assuming that each wall is a 100x100 grid, and that each object will be a
	 * size based on that grid. the value 20 means that the object will be 20x20
	 * in terms of the the walls 100x100
	 * 
	 */
	public DEMOOBJECT(){
		size = 20;
		x = (int) ((100 - size) * Math.random()) + (size / 2);
		y = (int) ((100 - size) * Math.random()) + size;
		//x = 20;
		//y = 20;
		object = new Decoration("testFace", Face.NORTHERN, new Dimensions(x, y, size));
	}
	
	public DEMOOBJECT(int xx, int yy){
		size = 25;
		x = xx;
		y = yy;
		object = new Decoration("testFace", Face.NORTHERN, new Dimensions(x, y, size));
	}
	
	public int getSize(){
		return object.getDimensions().getScale();
	}
	
	public int getX(){
		return object.getDimensions().getX();
	}
	
	public int getY(){
		return object.getDimensions().getY();
	}
	
	public String getImage(){
		return object.getName();
	}
}
