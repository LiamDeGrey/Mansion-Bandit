package mansionBandit.gameView;

import java.util.ArrayList;
import java.util.List;

public class DEMOWALL {
	private List<DEMOOBJECT> list = new ArrayList<DEMOOBJECT>();
	boolean debug = false;
	
	public DEMOWALL(){
		DEMOOBJECT ob = new DEMOOBJECT();
		list.add(ob);
	}
	
	public DEMOWALL(boolean debug){
		DEMOOBJECT ob = new DEMOOBJECT(20, 20);
		list.add(ob);
		ob = new DEMOOBJECT(30, 30);
		list.add(ob);
	}
	
	public List<DEMOOBJECT> getObjects(){
		return list;
	}
}
