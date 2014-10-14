package tests;

import mansionBandit.factory.*;
import mansionBandit.gameWorld.areas.Hallway;
import mansionBandit.gameWorld.areas.MansionArea;
import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

import org.junit.*;

import static org.junit.Assert.*;

public class factoryTests {
	/*
	 * as the factory is random, a lot of tests use many iterations
	 */
	
	@Test
	public void testWallpapers(){
		//ensure factory applies textures
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
		//make sure each wall is given only one item (including the ceiling)
		RoomFactory factory = new RoomFactory();
		for (int i = 0; i < 100; i++){
			Room room = new Room("");
			int north = 0;
			int south = 0;
			int east = 0;
			int west = 0;
			int ceiling = 0;
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
				if (item.getFace() == Face.CEILING){
					ceiling++;
				}
			}
			assertTrue("north wall should have exactly one item", north == 1);
			assertTrue("west wall should have exactly one item", west == 1);
			assertTrue("south wall should have exactly one item", south == 1);
			assertTrue("east wall should have exactly one item", east == 1);
			assertTrue("the ceiling should have exactly one item", ceiling == 1);
		}
	}
	
	@Test
	public void ensureDoors(){
		//make sure each wall that is linked to another room has a door on it
		RoomFactory factory = new RoomFactory();
		for (int i = 0; i < 100; i++){
			MansionArea[][] grid = getRoomsTestGrid();
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
			MansionArea[][] grid = getRoomsTestGrid();
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
	
	@Test
	public void ensureHallNoItemsBetween(){
		//make sure each wall that is linked to another room has a door on it
		RoomFactory factory = new RoomFactory();
		for (int i = 0; i < 100; i++){
			MansionArea[][] grid = getRoomAndHallTestGrid();
			for (int a = 0; a < grid.length; a++){
				for (int b = 0; b < grid[0].length; b++){
					//loop through the rooms
					if (!(grid[a][b] instanceof Hallway)){
						//skip non-hallways
						continue;
					}
										
					Boolean north = false;
					Boolean south = false;
					Boolean west = false;
					Boolean east = false;
					
					//check to see if any walls have links to another hallway
					if (grid[a][b].getNorth() instanceof Hallway){
						north = true;
					}
					if (grid[a][b].getWest() instanceof Hallway){
						west = true;
					}
					if (grid[a][b].getEast() instanceof Hallway){
						east = true;
					}
					if (grid[a][b].getSouth() instanceof Hallway){
						south = true;
					}
					
					//call factory
					factory.populateRoom(grid[a][b]);
					
					for (GameMatter item : grid[a][b].getItems()){
						//go through all the objects on the walls, and make sure they arent on a wall with a links to hallway
						if (item.getFace() == Face.NORTHERN){
							assertFalse("item should not be on wall between hallways", north);
						}
						if (item.getFace() == Face.EASTERN){
							assertFalse("item should not be on wall between hallways", east);
						}
						if (item.getFace() == Face.SOUTHERN){
							assertFalse("item should not be on wall between hallways", south);
						}
						if (item.getFace() == Face.WESTERN){
							assertFalse("item should not be on wall between hallways", west);
						}
					}
				}
			}
		}
	}
	
	//TODO test the floors
	
	private MansionArea[][] getRoomAndHallTestGrid(){
		MansionArea[][] grid = new MansionArea[2][2];
		
		// H H
		// R H
		
		//create rooms
		grid[0][0] = new Hallway("");
		grid[0][1] = new Hallway("");
		grid[1][0] = new Room("");
		grid[1][1] = new Hallway("");
		
		return linkTestGrid(grid);
	}
	
	/**
	 * generates a grid containing 4 rooms
	 * 
	 */
	private MansionArea[][] getRoomsTestGrid(){
		MansionArea[][] grid = new MansionArea[2][2];
		
		//create rooms
		grid[0][0] = new Room("");
		grid[0][1] = new Room("");
		grid[1][0] = new Room("");
		grid[1][1] = new Room("");
		
		return linkTestGrid(grid);
	}

	/**
	 * links a grid together (assumes [2][2] in size)
	 *
	 */
	private MansionArea[][] linkTestGrid(MansionArea[][] grid){
		//set links
		grid[0][0].setLinks(null, grid[0][1], grid[1][0], null);
		grid[0][1].setLinks(null, null, grid[1][1], grid[0][0]);
		grid[1][0].setLinks(grid[0][0], grid[1][0], null, null);
		grid[1][1].setLinks(grid[0][1], null, null, grid[1][1]);

		return grid;
	}
}
