package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * A Bandit is any person that has entered the game to play
 * as a thief
 * @author Liam De Grey
 *
 */
public class Bandit extends Character{
	private Face face;
	private Dimensions pos;
	private String name;
	private List<Grabable> inventory = new ArrayList<Grabable>();

	public Bandit(String name) {

	}

	public void setFace(Face f) {
		face = f;
	}

	@Override
	public Face getFace() {
		return face;
	}

	public void setPos(Dimensions p) {
		pos = p;
	}

	@Override
	public Dimensions getPosition() {
		return pos;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

	/**
	 * Adds item to inventory
	 * @param itm
	 * @param slot
	 * @return
	 */
	public boolean addItem(Grabable itm, int slot){
		if(slot>=0&&slot<=6){
			if(inventory.get(slot)!=null){
				inventory.add(itm);
				return true;
			}
		}
		return false;
	}


	/**
	 * returns item at specified slot in players inventory
	 * @param slot
	 * @return
	 */
	public Grabable getItem(int slot){
		if(slot>=0&&slot<=5){
			return inventory.get(slot);
		}
		return null;
	}

	/**
	 * Adds an item to players inventory
	 * @param the item to add
	 * @return whether the item was added succesfully
	 */
	public boolean addItem(Grabable item){
		if(inventory.size()<6){
			return inventory.add(item);
		}
		return false;
	}

	/**
	 * Removes an item to players inventory
	 * @param the item to remove
	 * @return whether the item was removed succesfully
	 */
	public boolean removeItem(Grabable item){
		if(inventory.contains(item)){
			return inventory.remove(item);
		}
		return false;
	}

}
