package tests;

import mansionBandit.factory.*;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

import org.junit.*;

import static org.junit.Assert.*;

public class factoryTests {

	@Test
	public void testWallpapers(){
		//ensure factory applies textures
		//as the factory is run on randomness, use brute force to try and
		//fail an assertertion
		RoomFactory factory = new RoomFactory();
		for (int i = 0; i < 100; i++){
			Room room = new Room("");
			factory.populateRoom(room);
			assertTrue("ceiling texture should be applied", room.getCeilingTexture() != null);
			assertTrue("floor texture should be applied", room.getFloorTexture() != null);
			assertTrue("wall texture should be applied", room.getWallTexture() != null);
		}
	}
	
	@Test
	public void testEachWallForItem(){
		//make sure each wall is given only one item
		RoomFactory factory = new RoomFactory();
		for (int i = 0; i < 100; i++){
			Room room = new Room("");
			int north = 0;
			int south = 0;
			int east = 0;
			int west = 0;
			factory.populateRoom(room);
			for (GameMatter item : room.getItems()){
				if (item.getFace() == Face.NORTHERN){
					north++;
				}
				if (item.getFace() == Face.SOUTHERN){
					south++;
				}
				if (item.getFace() == Face.WESTERN){
					west++;
				}
				if (item.getFace() == Face.EASTERN){
					east++;
				}
			}
			assertTrue("north wall should have an item", north == 1);
			assertTrue("west wall should have an item", west == 1);
			assertTrue("south wall should have an item", south == 1);
			assertTrue("east wall should have an item", east == 1);
		}
	}
	
	@Test
	public void ensureDoors(){
		//make sure each wall that is linked to another room has a door on it
		RoomFactory factory = new RoomFactory();
		for (int i = 0; i < 100; i++){
			MansionArea[][] grid = getTestGrid();
			for (int a = 0; a < grid.length; a++){
				for (int b = 0; b < grid[0].length; b++){
					factory.populateRoom(grid[a][b]);
					for (GameMatter item : grid[a][b].getItems()){
						if (item.getFace() == Face.NORTHERN && grid[a][b].getNorth() != null){
							assertTrue("item on a linked north wall should be a door", item instanceof Door);
						}
						if (item.getFace() == Face.EASTERN && grid[a][b].getEast() != null){
							assertTrue("item on a linked east wall should be a door", item instanceof Door);
						}
						if (item.getFace() == Face.SOUTHERN && grid[a][b].getSouth() != null){
							assertTrue("item on a linked  south wall should be a door", item instanceof Door);
						}
						if (item.getFace() == Face.WESTERN && grid[a][b].getWest() != null){
							assertTrue("item on a linked west wall should be a door", item instanceof Door);
						}
					}
				}
			}
		}
	}
	
	@Test
	public void testEachWallForItemPlusDoors(){
		//make sure each wall is given only one item
		//this time there are four rooms that are linked together
		RoomFactory factory = new RoomFactory();
		for (int i = 0; i < 100; i++){
			MansionArea[][] grid = getTestGrid();
			for (int a = 0; a < grid.length; a++){
				for (int b = 0; b < grid[0].length; b++){
					int north = 0;
					int south = 0;
					int east = 0;
					int west = 0;
					factory.populateRoom(grid[a][b]);
					for (GameMatter item : grid[a][b].getItems()){
						if (item.getFace() == Face.NORTHERN){
							north++;
						}
						if (item.getFace() == Face.SOUTHERN){
							south++;
						}
						if (item.getFace() == Face.WESTERN){
							west++;
						}
						if (item.getFace() == Face.EASTERN){
							east++;
						}
					}
					assertTrue("north wall should have an item", north == 1);
					assertTrue("west wall should have an item", west == 1);
					assertTrue("south wall should have an item", south == 1);
					assertTrue("east wall should have an item", east == 1);
				}
			}
		}
	}
	
	//TODO test hallways for doors
	//TODO test the floors
	//TODO test the ceilings
	
	private MansionArea[][] getTestGrid(){
		MansionArea[][] grid = new MansionArea[2][2];
		
		//create rooms
		grid[0][0] = new Room("");
		grid[0][1] = new Room("");
		grid[1][0] = new Room("");
		grid[1][1] = new Room("");
		
		//set links
		grid[0][0].setLinks(null, grid[0][1], grid[1][0], null);
		grid[0][1].setLinks(null, null, grid[1][1], grid[0][0]);
		grid[1][0].setLinks(grid[0][0], grid[1][0], null, null);
		grid[1][1].setLinks(grid[0][1], null, null, grid[1][1]);
		
		return grid;
	}
}
