package mansionBandit.gameView;

import java.util.ArrayList;
import java.util.List;

public class DEMOWALL {
	private DEMOOBJECT ob;
	
	public DEMOWALL(){
		ob = new DEMOOBJECT();
	}
	
	public List<DEMOOBJECT> getObjects(){
		List<DEMOOBJECT> list = new ArrayList<DEMOOBJECT>();
		list.add(ob);
		return list;
	}
}
