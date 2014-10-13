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
	
	@Override
	public boolean useItemOn(GameMatter itm) {
		if(itm instanceof Van) {
			((Van)itm).addItem(this);
			return true;
		}
		return false;
	}
	
	@Override
	public String getDescription(){
		return super.getDescription() + " (Value: $" + value + ")";
	}

}
