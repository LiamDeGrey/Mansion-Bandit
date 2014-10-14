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
	private Bandit player;
	private List<Sellable> items = new ArrayList<Sellable>();
	private int total = 0;

	public Van(String name, Face face, Bandit player) {
		super(name, null, null, face, null);
		this.player = player;
	}

	/**
	 * drops off all items at once
	 */
	public void dropOff() {
		for(int i=0; i<7; i++) {
			if(player.getItem(i)!=null) {
				items.add((Sellable) player.getItem(i));
				total += ((Sellable) player.getItem(i)).getValue();
				player.addItem(null, i);

			}
		}
	}

	public boolean addItem(Sellable itm) {
		total += itm.getValue();
		return items.add(itm);
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
		return new Dimensions(50, 100, 100);
	}

}
