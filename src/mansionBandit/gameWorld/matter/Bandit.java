package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import mansionBandit.gameWorld.areas.MansionArea;


/**
 * A Bandit is any person that has entered the game to play
 * as a thief
 * @author Liam De Grey
 *
 */
public class Bandit extends Character{
	private Face face;
	private Dimensions dimensions;
	private String name;
	private List<Grabable> inventory = new ArrayList<Grabable>();
	private MansionArea area;

	public Bandit(String name) {

	}

	@Override
	public MansionArea getArea() {
		return area;
	}

	public void setFace(Face f) {
		face = f;
	}

	@Override
	public Face getFace() {
		return face;
	}

	@Override
	public Dimensions getDimensions() {
		return dimensions;
	}

	@Override
	public void setDimensions(Dimensions d) {
		dimensions = d;
	}

	@Override
	public void setArea(MansionArea r) {
		area = r;
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
	 * bandit moves forward
	 */
	public boolean moveForward() {
		Face face = getFace();
		MansionArea area = null;
		if(face==Face.NORTHERN) area = getArea().getNorth();
		else if(face==Face.EASTERN) area =getArea().getEast();
		else if(face==Face.SOUTHERN) area =getArea().getSouth();
		else if(face==Face.WESTERN) area =getArea().getWest();

		if(area!=null) {
			setArea(area);
			return true;
		}
		return false;
	}

	/**
	 * makes the bandit look to the left
	 */
	public void turnLeft(){
		int current = Face.getFaceNum(getFace());
		if(current==0) current = 3;
		else current--;
		setFace(Face.getFace(current));
	}

	/**
	 * makes the bandit look right
	 */
	public void turnRight(){
		int current = Face.getFaceNum(getFace());
		if(current==3) current = 0;
		else current++;
		setFace(Face.getFace(current));
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
		if(inventory.size() >slot){
		if(slot>=0&&slot<=5){
			return inventory.get(slot);
		}
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
