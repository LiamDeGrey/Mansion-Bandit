package mansionBandit.ApplicationWindow;
import java.awt.Point;

import javax.swing.JFrame;


public class ApplicationMain {

	private GameFrame gameFrame;
	public ApplicationMain(){
		gameFrame = new GameFrame(this);
		
	}
	
public static void main(String args[]) {
		 ApplicationMain game = new ApplicationMain();
}

}
