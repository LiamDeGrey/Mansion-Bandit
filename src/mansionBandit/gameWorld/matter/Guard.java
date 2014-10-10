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

	public Guard(String name, Face face, Dimensions position, MansionArea area) {
		super(name, null, null, face, position);
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
					this.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(area.getItems().contains(prey)){
					prey.killBandit();
				}
			}
		};
	}

	@Override
	public String getImage(){
		return "guard";
	}

	@Override
	public String getDescription(){
		return "Oh no! It's a guard! Stab him quick!";
	}


}
