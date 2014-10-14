package mansionBandit.gameWorld.matter;

public class Knife extends Grabable{

	public Knife(String name, String description, String image, Face face, Dimensions position) {
		super(name, description, image, face, position);
	}

	@Override
	public boolean useItemOn(GameMatter itm){
		if(itm instanceof Character){
			((Character) itm).kill();
			return true;
		}
		return false;
	}

}
