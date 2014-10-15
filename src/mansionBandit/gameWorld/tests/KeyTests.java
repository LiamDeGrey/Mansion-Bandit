package mansionBandit.gameWorld.tests;

import static org.junit.Assert.*;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Key;

import org.junit.Test;

/**
 *Tests unlocking the door
 * @author Liam De Grey
 *
 */
public class KeyTests {

	@Test
	public void checkDoorUnlockValid(){
		Door lockedDoor1 = makeLockedDoor();
		Door lockedDoor2 = makeLockedDoor();
		Key keyType1 = makeKey1();
		Key keyType2 = makeKey2();

		lockedDoor1.setKeyNeeded("key1");
		keyType1.useItemOn(lockedDoor1);

		lockedDoor2.setKeyNeeded("key2");
		keyType2.useItemOn(lockedDoor2);

		assertTrue(!lockedDoor1.isLocked());
		assertTrue(!lockedDoor2.isLocked());

	}

	@Test
	public void checkDoorUnlockInvalid(){
		Door lockedDoor1 = makeLockedDoor();
		Door lockedDoor2 = makeLockedDoor();
		Key keyType1 = makeKey1();
		Key keyType2 = makeKey2();

		lockedDoor1.setKeyNeeded("key2");
		keyType1.useItemOn(lockedDoor1);

		lockedDoor2.setKeyNeeded("key1");
		keyType2.useItemOn(lockedDoor2);

		assertFalse(!lockedDoor1.isLocked());
		assertFalse(!lockedDoor2.isLocked());

	}

	@Test
	public void checkDoorUnlocked(){
		Door unlockedDoor = makeUnLockedDoor();

		assertTrue(!unlockedDoor.isLocked());

	}

	public Key makeKey1(){
		return new Key("key1", "This is the description", "key", new Dimensions(50, 50, 20));
	}

	public Key makeKey2(){
		return new Key("key2", "This is the description", "keyblade", new Dimensions(50, 50, 20));
	}

	public Door makeLockedDoor(){
		return new Door("TestDoor", Face.NORTHERN, true);
	}

	public Door makeUnLockedDoor(){
		return new Door("TestDoor", Face.NORTHERN, false);
	}

}
