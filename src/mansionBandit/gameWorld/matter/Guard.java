package mansionBandit.gameWorld.matter;

import mansionBandit.gameWorld.areas.MansionArea;

/**
 * Guard is an enemy of the bandit and can decrease the
 * bandits health bar
 * @author Liam De Grey
 *
 */
public class Guard extends Character {
	private MansionArea area;

	public Guard(String name, Face face, MansionArea area) {
		super(name, null, null, face, null);
		this.area = area;
	}

	public void wakeUp(){
		Thread clockTick = new Thread(){
			private Bandit prey = null;

			@Override
			public void run(){

				for(GameMatter itm: area.getItems()){
					if(itm instanceof Bandit){
						prey = (Bandit) itm;
					}
				}
				try {
					this.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(area.getItems().contains(prey)){
					prey.kill();
				}
			}
		};
		clockTick.start();
	}

	@Override
	public void kill(){
		area.addItem(new Knife("dropped Knife" + GameMatter.getItemCounter(), "The Guard dropped this", "knife", Face.FLOOR, new Dimensions(50, 50, 2)));
		area.getItems().remove(this);
	}

	@Override
	public String getImage(){
		return "guard";
	}

	@Override
	public String getDescription(){
		return "Oh no! It's a guard! Stab him quick!";
	}

	@Override
	public Dimensions getDimensions(){
		return new Dimensions(50, 50, 70);
	}


}
