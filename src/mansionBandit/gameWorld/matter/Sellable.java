package mansionBandit.gameWorld.matter;

/**
 * This class creates the sellable items
 * @author Liam De Grey
 *
 */
public class Sellable extends Grabable{
	private int value;
	
	public Sellable(String name, String description, String image, Face face, Dimensions position, int value){
		super(name, description, image, face, position);
		this.value = value;
	}

	public int getValue(){
		return value;
	}

}
