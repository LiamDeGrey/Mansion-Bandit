package mansionBandit.gameWorld.matter;


import java.util.ArrayList;



import java.util.List;

import mansionBandit.gameWorld.main.Player;

/**
 * This class is where the inventory items are stored and values
 * are calculated
 * @author Liam De Grey
 *
 */
public class Van extends GameMatter{
	private Player player;
	private List<Sellable> items = new ArrayList<Sellable>();
	private int total = 0;
	
	public Van(String name, Face face, Player player) {
		super(name, null, null, face, null);
		this.player = player;
	}
	
	/**
	 * drops off all items at once
	 */
	public void dropOff() {
		for(int i=0; i<player.getInventorySize(); i++) {
			if(player.getItem(i)!=null) {
				items.add((Sellable) player.getItem(i));
				total += ((Sellable) player.getItem(i)).getValue();
				player.addItem(null, i);
				
			}
		}
	}
	
	/**
	 * drag item to van
	 * @param itm
	 * @return
	 */
	public boolean useItemOn(Grabable itm) {
		if(itm instanceof Sellable) {
			items.add((Sellable) itm);
			total += ((Sellable) itm).getValue();
			player.removeItem(itm);
			return true;
		}
		return false;
	}
	
	public int getTotal() {
		return total;
	}
	
	@Override
	public String getDescription() {
		return "Your van currently holds "+items.size()+" items";
	}
	
	@Override
	public String getImage() {
		return "dropoff";
	}
	
	@Override
	public Dimensions getDimensions() {
		return new Dimensions(20, 20, 50);
	}

}
