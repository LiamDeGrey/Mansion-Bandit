package mansionBandit.gameWorld.matter;

/**
 * The position class used to find out position of 
 * every item
 * @author Liam De Grey
 *
 */
public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
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
}
