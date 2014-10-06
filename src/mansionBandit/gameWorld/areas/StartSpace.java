package mansionBandit.gameWorld.areas;

import java.util.List;

import mansionBandit.gameWorld.matter.GameMatter;

public class StartSpace implements MansionArea {

	@Override
	public void setLinks(MansionArea north, MansionArea east, MansionArea south, MansionArea west) {

	}

	@Override
	public MansionArea getNorth() {
		return null;
	}

	@Override
	public MansionArea getSouth() {
		return null;
	}

	@Override
	public MansionArea getWest() {
		return null;
	}

	@Override
	public MansionArea getEast() {
		return null;
	}

	@Override
	public void addItem(GameMatter itm) {

	}

	@Override
	public List<GameMatter> getItems() {
		return null;
	}

	@Override
	public String getWallTexture() {
		return null;
	}

	@Override
	public String getCeilingTexture() {
		return null;
	}

	@Override
	public String getFloorTexture() {
		return null;
	}

}
