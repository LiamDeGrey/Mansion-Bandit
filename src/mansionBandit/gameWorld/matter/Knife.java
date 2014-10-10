package mansionBandit.gameWorld.matter;

public class Knife extends Grabable{

	public Knife(String name, Face face, Dimensions position) {
		super(name, null, null, face, position);
	}

	@Override
	public String getImage(){
		return "knife";
	}

	@Override
	public String getDescription(){
		return "Careful, you could kill someone with that!!";
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
