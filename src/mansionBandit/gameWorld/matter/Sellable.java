package mansionBandit.gameWorld.matter;

import mansionBandit.gameWorld.areas.MansionArea;

/**
 * This class makes random objects that a bandit can steal
 * and sell
 * @author Liam De Grey
 *
 */
public class Sellable extends Grabable{
	private static final int numItems = 5;
	private String name;
	private String image;
	private int value;

	public Sellable(){
		randomize();
	}

	private void randomize(){
		int rand = (int)(Math.random()*numItems+1);
		name = makeItem(rand);
		image = name+".png";
		value = (int)(Math.random()*(rand*100)+(rand*40));
	}

	private String makeItem(int i){
		if(i==1)
			return "picture";
		else if(i==2)
			return "lamp";
		else if(i==3)
			return "bookcase";
		else if(i==4)
			return "piano";
		return null;
	}

	@Override
	public Face getFace() {
		return null;
	}

	@Override
	public void setFace(Face f) {
	}

	@Override
	public Dimensions getDimensions() {
		return null;
	}

	@Override
	public void setDimensions(Dimensions d) {
	}

	@Override
	public MansionArea getArea() {
		return null;
	}

	@Override
	public void setArea(MansionArea r) {
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getImage() {
		return image;
	}

}
