package mansionBandit.gameWorld.matter;


/**
 * A key that can be put in inventory, needs to be in
 * inventory in order to unlock certain doors
 * @author Liam De Grey
 *
 */
public class Key extends Grabable{

	public Key(String name, Face face, Dimensions position){
		super(name, null, null, face, position);
	}

	@Override
	public boolean useItemOn(Grabable playerItm, GameMatter itm){
		if(itm instanceof Door){
			((Door) itm).unlock();
			return true;
		}
		return false;
	}

	@Override
	public String getImage(){
		return "key";
	}

	@Override
	public String getDescription(){
		return "This is a key";
	}

}
