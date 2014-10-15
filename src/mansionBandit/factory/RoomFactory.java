package mansionBandit.factory;

import java.util.Random;


//import jdk.internal.org.objectweb.asm.commons.StaticInitMerger;
import mansionBandit.gameWorld.areas.*;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.FurnitureStatic;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * the RoomFactory will fill a room with randomly generated content
 * @author Andy
 *
 */
public class RoomFactory {
	private final int roomFloorObjects = 4, chanceToLock = 6, chanceToHallwayItem = 5, chanceToHallWallItem = 4;
	private ItemFactory floorItems, ceilingItems, hallWallItems, wallItems;
	private RoomPainter roomPainter;
	private Random random;

	public RoomFactory(){
		//setup wall texture lists
		roomPainter = new RoomPainter("/texture/room.txt");
		//setup object factory(s)
		floorItems = new ItemFactory("/object/floor.txt");
		hallWallItems = new ItemFactory("/object/hallWall.txt");
		wallItems = new ItemFactory("/object/wall.txt");
		ceilingItems = new ItemFactory("/object/ceiling.txt");

		random = new Random();
	}

	/**
	 * will texture, and fill a room based on what type of room it is
	 *
	 * @param room the room to texture
	 */
	public void populateRoom(MansionArea room){
		//populate doors
		//TODO random placed doors? (x axis)
		if (room instanceof StartSpace){
			roomPainter.paintRoom(room);

		}else {
			if (room.getNorth() != null && (room instanceof Room || room.getNorth() instanceof Room)){
				room.addItem(new Door("North Door", Face.NORTHERN, new Dimensions(50, 100, 70), random.nextInt(chanceToLock) == 1));
			}
			if (room.getEast() != null && (room instanceof Room || room.getEast() instanceof Room)){
				room.addItem(new Door("East Door", Face.EASTERN, new Dimensions(50, 100, 70), random.nextInt(chanceToLock) == 1));
			}
			if (room.getSouth() != null && (room instanceof Room || room.getSouth() instanceof Room)){
				room.addItem(new Door("South Door", Face.SOUTHERN, new Dimensions(50, 100, 70), random.nextInt(chanceToLock) == 1));
			}
			if (room.getWest() != null && (room instanceof Room || room.getWest() instanceof Room)){
				room.addItem(new Door("West Door", Face.WESTERN, new Dimensions(50, 100, 70), random.nextInt(chanceToLock) == 1));
			}

			//place objects
			if (room instanceof Hallway){
				popWall(room, false);
				popFloor(room, false);
				popCeiling(room, false);
			} else if (room instanceof Room){
				//apply textures to the room
				roomPainter.paintRoom((Room) room);

				popWall(room, true);
				popFloor(room, true);
				popCeiling(room, true);
			}
		}
	}

	/**
	 * add objects to the ceiling of a room
	 *
	 * @param room the room to add objects to
	 * @param isRoom true if room is a Room object (as opposed to hallway)
	 */
	private void popCeiling(MansionArea room, boolean isRoom){

		if (isRoom){
			GameMatter light = ceilingItems.getItem(Face.CEILING);
			light.setDimensions(new Dimensions(50, 50, light.getDimensions().getScale()));
			room.addItem(light);
		} else {
			room.addItem(new FurnitureStatic("Hall Light", "What a sweet light bro", "light2", Face.CEILING, new Dimensions(50,50,30)));
		}
	}

	/**
	 * add objects to the floor of a room
	 *
	 * @param room the room to add objects to
	 * @param isRoom true if room is a Room object (as opposed to hallway)
	 */
	private void popFloor(MansionArea room, boolean isRoom){
		int numbObjects = 0;
		//determine number of objects to create
		if (isRoom){
			numbObjects = random.nextInt(roomFloorObjects) + 1;
		} else {
			// much lower chance of random
			numbObjects = random.nextInt(chanceToHallwayItem);
			if (numbObjects != 1){
				numbObjects = 0;
			}
		}
		for (int i = 0; i < numbObjects; i++){
			//add random object to room
			room.addItem(floorItems.getItem(Face.FLOOR));
		}
	}

	/**
	 * add objects to the walls of a room
	 *
	 * @param room the room to add objects to
	 * @param isRoom true if room is a Room object (as opposed to hallway)
	 */
	private void popWall(MansionArea room, boolean isRoom){
		if (isRoom){
			if (room.getNorth() == null){
				room.addItem(wallItems.getItem(room, Face.NORTHERN));
			}
			if (room.getEast() == null){
				room.addItem(wallItems.getItem(room, Face.EASTERN));
			}
			if (room.getSouth() == null){
				room.addItem(wallItems.getItem(room, Face.SOUTHERN));
			}
			if (room.getWest() == null){
				room.addItem(wallItems.getItem(room, Face.WESTERN));
			}
		} else {
			//check hall way for valid walls
			if (room.getNorth() == null){
				room.addItem(hallWallItems.getItem(room, Face.NORTHERN));
			}
			if (room.getEast() == null){
				room.addItem(hallWallItems.getItem(room, Face.EASTERN));
			}
			if (room.getSouth() == null){
				room.addItem(hallWallItems.getItem(room, Face.SOUTHERN));
			}
			if (room.getWest() == null){
				room.addItem(hallWallItems.getItem(room, Face.WESTERN));
			}
		}
	}
}
