package mansionBandit.gameWorld.matter;

import java.io.Serializable;

/**
 * The dimensions class used to find out position and size of
 * every item
 * @author Liam De Grey
 *
 */
public class Dimensions implements Serializable {
	private int x;
	private int y;
	private int scale;

	public Dimensions(int x, int y){
		this.x = x;
		this.y = y;
	}

	public Dimensions(int x, int y, int scale){
		this.x = x;
		this.y = y;
		this.scale = scale;
	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void setScale(int scale){
		this.scale = scale;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getScale(){
		return scale;
	}
}
