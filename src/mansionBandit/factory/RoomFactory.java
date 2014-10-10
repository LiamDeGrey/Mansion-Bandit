package mansionBandit.factory;

import java.util.Random;

import mansionBandit.gameWorld.areas.*;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.Face;

public class RoomFactory {
	private static final int roomFloorObjects = 3;
	private ItemFactory floorItems;
	
	public RoomFactory(){
		//setup wall texture lists
		//.. floors
		//.. ceilings
		//setup object factory(s)
		floorItems = new ItemFactory("floor");
	}
	
	/**
	 * will texture, and fill a room based on what type of room it is
	 * 
	 * @param room the room to texture
	 */
	public void populateRoom(MansionArea room){
		//populate doors
		//TODO random placed doors? (x axis)
		if (room.getNorth() != null && !(room instanceof Hallway && room.getNorth() instanceof Hallway)){
			//room.addItem(new Door("North Door", new Dimensions(50, 100, 70), Face.NORTHERN, false, ""));
		}
		if (room.getEast() != null && !(room instanceof Hallway && room.getEast() instanceof Hallway)){
			//room.addItem(new Door("East Door", new Dimensions(50, 100, 70), Face.EASTERN, false, ""));
		}
		if (room.getSouth() != null && !(room instanceof Hallway && room.getSouth() instanceof Hallway)){
			//room.addItem(new Door("South Door", new Dimensions(50, 100, 70), Face.SOUTHERN, false, ""));
		}
		if (room.getWest() != null && !(room instanceof Hallway && room.getWest() instanceof Hallway)){
			//room.addItem(new Door("West Door", new Dimensions(50, 100, 70), Face.WESTERN, false, ""));
		}
		if (room instanceof Hallway){
			//call factory on the ceiling 
			popFloor(room, false);
		} else if (room instanceof StartSpace){
			//call factory on the ceiling 
		} else if (room instanceof Room){
			//call factory on the ceiling 
			popFloor(room, true);
		}
	}
	
	/**
	 * add objects to room
	 * 
	 * @param room the room to add objects to
	 * @param isRoom true if room is a Room object (as opposed to hallway)
	 */
	private void popFloor(MansionArea room, boolean isRoom){
		int numbObjects = 0;
		Random rand = new Random();
		//determine number of objects to create
		if (isRoom){
			numbObjects = rand.nextInt(roomFloorObjects);
		} else {
			// much lower chance of random
			numbObjects = rand.nextInt(5);
			if (numbObjects != 1){
				numbObjects = 3;
			}
		}
		for (int i = 0; i < numbObjects; i++){
			//add random object to room
			room.addItem(floorItems.getItem(Face.FLOOR));
		}
	}
}
