package mansionBandit.gameWorld.matter;

/**
 * The dimensions class used to find out position and size of
 * every item
 * @author Liam De Grey
 *
 */
public class Dimensions {
	private int x;
	private int y;
	private int w;
	private int h;

	public Dimensions(int x, int y){
		this.x = x;
		this.y = y;
	}

	public Dimensions(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getWidth(){
		return w;
	}

	public int getHeight(){
		return h;
	}
}
