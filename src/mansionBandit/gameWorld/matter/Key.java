package mansionBandit.gameWorld.matter;

import javax.swing.JOptionPane;


/**
 * A key that can be put in inventory, needs to be in
 * inventory in order to unlock certain doors
 * @author Liam De Grey
 *
 */
public class Key extends Grabable{

	public Key(String name, String description, String image, Dimensions position){
		super(name, description, image, null, position);
	}

	/**
	 * Checks to see if the item the key is being used on is a door
	 * , if yes then checks to see if its the right key thats being
	 * used
	 */
	@Override
	public boolean useItemOn(GameMatter itm){
		if(itm instanceof Door){
			if(((Door)itm).isLocked()){
				if(((Door)itm).getKeyNeeded().equals(getName())){
					((Door) itm).unlock();
					System.out.println("Door unlocked");
					return true;
				}
				JOptionPane.showMessageDialog(
						null,
						"This is the wrong key", "Wrong key", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Wrong Key");
				return false;
			}
			JOptionPane.showMessageDialog(
					null,
					"This door is UNLOCKED", "Duhhh", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("This door isn't locked");
			return false;
		}
		System.out.println("You can only use keys on doors");
		return false;
	}

	@Override
	public Face getFace(){
		return Face.FLOOR;
	}


}
