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
	public boolean useItemOn(GameMatter itm){
		if(itm instanceof Door){
			if(((Door)itm).isLocked()){
				if(((Door)itm).getKeyNeeded().equals(getName())){
					((Door) itm).unlock();
					System.out.println("Door unlocked");
					return true;
				}
				System.out.println("Wrong Key");
			}
			System.out.println("This door isn't locked");
		}
		System.out.println("You can only use keys on doors");
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
