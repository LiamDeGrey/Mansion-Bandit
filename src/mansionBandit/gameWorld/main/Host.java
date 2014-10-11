package mansionBandit.gameWorld.main;

/**
 * There is only one host object in each game, this object creates the
 * grid and stores a bandit for the player
 * @author Liam De Grey
 *
 */
public class Host extends Player{

	//TODO: Create a reference to a server. Host needs Server to broadcast out,
	//it doesn't need to send messages anywhere else.
	public Host (String name, int rooms){
		super(name, rooms);
	}

}
