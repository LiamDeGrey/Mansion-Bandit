package mansionBandit.ApplicationWindow;

/**
 *Main class of the game. Starts the game.
 * @author carrtheo
 *
 */
public class ApplicationMain {

	private GameFrame gameFrame;
	public ApplicationMain(){
		gameFrame = new GameFrame(this);

	}

public static void main(String args[]) {
		 ApplicationMain game = new ApplicationMain();
}

}
