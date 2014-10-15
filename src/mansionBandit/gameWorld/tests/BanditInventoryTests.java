package mansionBandit.gameWorld.tests;

import static org.junit.Assert.*;
import mansionBandit.gameWorld.main.Host;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;
import mansionBandit.gameWorld.matter.Grabable;
import mansionBandit.gameWorld.matter.Sellable;

import org.junit.Test;

/**
 * Tests the bandits inventory
 * @author degreliam
 *
 */
public class BanditInventoryTests {
	private static int itemCount = 1;

	@Test
	public void testInventoryAddValid(){
		Bandit bandit = makeHost();

		GameMatter item1 = makeItem();
		GameMatter item2 = makeItem();
		GameMatter item3 = makeItem();
		GameMatter item4 = makeItem();
		GameMatter item5 = makeItem();
		GameMatter item6 = makeItem();
		GameMatter item7 = makeItem();

		assertTrue(bandit.addItem((Grabable) item1));
		assertTrue(bandit.addItem((Grabable) item2));
		assertTrue(bandit.addItem((Grabable) item3));
		assertTrue(bandit.addItem((Grabable) item4));
		assertTrue(bandit.addItem((Grabable) item5));
		assertTrue(bandit.addItem((Grabable) item6));
		assertTrue(bandit.addItem((Grabable) item7));
	}

	@Test
	public void testInventoryAddInvalid(){
		Bandit bandit = makeHost();

		GameMatter item1 = makeItem();
		GameMatter item2 = makeItem();
		GameMatter item3 = makeItem();
		GameMatter item4 = makeItem();
		GameMatter item5 = makeItem();
		GameMatter item6 = makeItem();
		GameMatter item7 = makeItem();
		GameMatter item8 = makeItem();

		bandit.addItem((Grabable) item1);
		bandit.addItem((Grabable) item2);
		bandit.addItem((Grabable) item3);
		bandit.addItem((Grabable) item4);
		bandit.addItem((Grabable) item5);
		bandit.addItem((Grabable) item6);
		bandit.addItem((Grabable) item7);

		assertFalse(bandit.addItem((Grabable) item8));
	}

	@Test
	public void testInventorygetValid(){
		Bandit bandit = makeHost();

		GameMatter item1 = makeItem();
		GameMatter item2 = makeItem();
		GameMatter item3 = makeItem();
		GameMatter item4 = makeItem();
		GameMatter item5 = makeItem();
		GameMatter item6 = makeItem();
		GameMatter item7 = makeItem();
		GameMatter item8 = makeItem();

		bandit.addItem((Grabable) item1);
		bandit.addItem((Grabable) item2);
		bandit.addItem((Grabable) item3);
		bandit.addItem((Grabable) item4);
		bandit.addItem((Grabable) item5);
		bandit.addItem((Grabable) item6);
		bandit.addItem((Grabable) item7);

		assertTrue(bandit.getItem(0).equals(item1));
		assertTrue(bandit.getItem(1).equals(item2));
		assertTrue(bandit.getItem(2).equals(item3));
		assertTrue(bandit.getItem(3).equals(item4));
		assertTrue(bandit.getItem(4).equals(item5));
		assertTrue(bandit.getItem(5).equals(item6));
		assertTrue(bandit.getItem(6).equals(item7));
	}

	@Test
	public void testInventorygetInvalid(){
		Bandit bandit = makeHost();

		GameMatter item1 = makeItem();
		GameMatter item2 = makeItem();
		GameMatter item3 = makeItem();

		bandit.addItem((Grabable) item1);
		bandit.addItem((Grabable) item2);
		bandit.addItem((Grabable) item3);

		assertTrue(bandit.getItem(3)==null);
		assertFalse(bandit.getItem(1).equals(item3));
	}


	public Bandit makeHost(){
		Host host = new Host("testBandit", 1, 10);
		return host.getBandit();
	}

	public GameMatter makeItem(){
		return new Sellable("item"+itemCount, "Description", "keyblade", Face.FLOOR, new Dimensions(50, 50, 20), 20);
	}

}
