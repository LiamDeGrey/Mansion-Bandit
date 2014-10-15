package mansionBandit.gameWorld.tests;

import static org.junit.Assert.*;
import mansionBandit.gameWorld.main.Host;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Face;

import org.junit.Test;

/**
 *Sometimes have issues passing due to the randomly placed
 *locked rooms
 * @author Liam De Grey
 *
 */
public class BanditMoveTests {

	@Test
	public void moveNorthValid(){
		Bandit bandit = makeHost();

		bandit.setArea(2, 2);
		bandit.setFace(Face.NORTHERN);


		if(bandit.moveForward()){
			assertTrue(bandit.getRoomCoords(bandit.getArea())[0]==1
					&&bandit.getRoomCoords(bandit.getArea())[1]==2);
		}

	}

	@Test
	public void moveSouthValid(){
		Bandit bandit = makeHost();

		bandit.setArea(2, 2);
		bandit.setFace(Face.SOUTHERN);

		if(bandit.moveForward()){
			assertTrue(bandit.getRoomCoords(bandit.getArea())[0]==3
					&&bandit.getRoomCoords(bandit.getArea())[1]==2);
		}
	}

	@Test
	public void moveWestValid(){
		Bandit bandit = makeHost();

		bandit.setArea(2, 2);
		bandit.setFace(Face.WESTERN);

		if(bandit.moveForward()){
			assertTrue(bandit.getRoomCoords(bandit.getArea())[0]==2
					&&bandit.getRoomCoords(bandit.getArea())[1]==1);
		}
	}

	@Test
	public void moveEastValid(){
		Bandit bandit = makeHost();

		bandit.setArea(2, 2);
		bandit.setFace(Face.EASTERN);

		if(bandit.moveForward()){
			assertTrue(bandit.getRoomCoords(bandit.getArea())[0]==2
					&&bandit.getRoomCoords(bandit.getArea())[1]==3);
		}
	}

	@Test
	public void moveNorthInvalid(){
		Bandit bandit = makeHost();

		bandit.setArea(0, 2);
		bandit.setFace(Face.NORTHERN);

		assertFalse(bandit.moveForward());
	}

	@Test
	public void moveSouthInvalid(){
		Bandit bandit = makeHost();

		bandit.setArea(3, 2);
		bandit.setFace(Face.SOUTHERN);

		assertFalse(bandit.moveForward());
	}

	@Test
	public void moveWestInvalid(){
		Bandit bandit = makeHost();

		bandit.setArea(2, 0);
		bandit.setFace(Face.WESTERN);

		assertFalse(bandit.moveForward());
	}

	@Test
	public void moveEastInvalid(){
		Bandit bandit = makeHost();

		bandit.setArea(2, 4);
		bandit.setFace(Face.EASTERN);

		assertFalse(bandit.moveForward());
	}

	@Test
	public void moveToStartSpaceValid(){
		Bandit bandit = makeHost();

		bandit.setArea(2, 0);
		bandit.setFace(Face.WESTERN);

		bandit.moveForward();

		assertTrue(bandit.getRoomCoords(bandit.getArea())[0]==-1
				&&bandit.getRoomCoords(bandit.getArea())[1]==-1);
	}

	@Test
	public void moveToStartSpaceInvalid(){
		Bandit bandit = makeHost();

		bandit.setArea(0, 2);
		bandit.setFace(Face.NORTHERN);

		bandit.moveForward();

		assertFalse(bandit.getRoomCoords(bandit.getArea())[0]==-1
				&&bandit.getRoomCoords(bandit.getArea())[1]==-1);
	}


	public Bandit makeHost(){
		Host host = new Host("testBandit", 1, 10);
		return host.getBandit();
	}

}
