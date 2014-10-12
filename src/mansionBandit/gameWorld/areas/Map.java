package mansionBandit.gameWorld.areas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * This class creates a visual representation of the mansion
 * @author Liam De Grey
 *
 */
public class Map extends JPanel{
	private MansionArea[][] grid;
	private int[] adjacentGrid;
	private Player player;
	private static final int widthBlock = 10;
	private static final int heightBlock = 10;
	private static final int padding = 10;
	private static final int bandit = 5;
	private static final int doorWidth = 6;//widthBlock/2;
	private static final int doorX = 2;//doorWidth/2;
	private int widthMap;
	private int heightMap;

	public Map(Player player){
		this.player = player;
		grid = player.getGrid();
		adjacentGrid = player.getBandit().getAdjacentGrid();
		widthMap = (grid[0].length*widthBlock)+(padding*2);
		heightMap = (grid.length*heightBlock)+(padding*2);
		setVisible(true);
		setLayout(null);
	}
	
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(widthMap, heightMap);
	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, widthMap, heightMap);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, widthMap-1, heightMap-1);
		drawGrid(g);
		drawWalls(g);
		drawPlayers(g);
	}
	
	/**
	 * Draws the grid
	 * @param graphics
	 */
	private void drawGrid(Graphics g) {
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				if(grid[i][j] instanceof Room){
					g.setColor(Color.decode("#99FF33"));//Greenish
					g.fillRect(j*widthBlock+padding, i*heightBlock+padding, widthBlock, heightBlock);
				}else if(grid[i][j] instanceof Hallway){
					g.setColor(Color.decode("#CCFFFF"));//Light blue
					g.fillRect(j*widthBlock+padding, i*heightBlock+padding, widthBlock, heightBlock);
				}
				/*
				 * Draw the start position(van) on the map
				 */
				if(adjacentGrid[0]==i&&adjacentGrid[1]==j) {
					g.setColor(Color.decode("#FF66FF"));//Purple
					if(i==0)
						g.fillRect(j*widthBlock+padding, padding-heightBlock, widthBlock, heightBlock);
					else if(i==grid.length-1)
						g.fillRect(j*widthBlock+padding, i*heightBlock+padding+heightBlock, widthBlock, heightBlock);
					else if(j==0)
						g.fillRect(padding-widthBlock, i*heightBlock+padding, widthBlock, heightBlock);
					else if(j==grid[0].length-1)
						g.fillRect(j*widthBlock+padding+widthBlock, i*heightBlock+padding, widthBlock, heightBlock);
					else
						System.out.println("I can't find your van!!!");
				}
			}
		}
	}
	
	/**
	 * Draws on lines to show a door or show a wall
	 */
	private void drawWalls(Graphics g) {
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				if(grid[i][j].getNorth()!=null) {
					g.setColor(Color.decode("#CC3300"));//Brown
					g.drawLine(j*widthBlock+padding+doorX, i*heightBlock+padding, j*widthBlock+padding+doorWidth, i*heightBlock+padding);
				}else {
					g.setColor(Color.BLACK);
					g.drawLine(j*widthBlock+padding, i*heightBlock+padding, j*widthBlock+padding+widthBlock, i*heightBlock+padding);
				}
				if(grid[i][j].getEast()!=null) {
					g.setColor(Color.decode("#CC3300"));//Brown
					g.drawLine((j+1)*widthBlock+padding, i*heightBlock+padding+doorX, (j+1)*widthBlock+padding, i*heightBlock+padding+doorWidth);
				}else {
					g.setColor(Color.BLACK);
					g.drawLine((j+1)*widthBlock+padding, i*heightBlock+padding, (j+1)*widthBlock+padding, (i+1)*heightBlock+padding);
				}
				if(grid[i][j].getSouth()!=null) {
					g.setColor(Color.decode("#CC3300"));//Brown
					g.drawLine(j*widthBlock+padding+doorX, (i+1)*heightBlock+padding, j*widthBlock+padding+doorWidth, (i+1)*heightBlock+padding);
				}else {
					g.setColor(Color.BLACK);
					g.drawLine(j*widthBlock+padding, (i+1)*heightBlock+padding, (j+1)*widthBlock+padding, (i+1)*heightBlock+padding);
				}
				if(grid[i][j].getWest()!=null) {
					g.setColor(Color.decode("#CC3300"));//Brown
					g.drawLine(j*widthBlock+padding, i*heightBlock+padding+doorX, j*widthBlock+padding, i*heightBlock+padding+doorWidth);
				}else {
					g.setColor(Color.BLACK);
					g.drawLine(j*widthBlock+padding, i*heightBlock+padding, j*widthBlock+padding, (i+1)*heightBlock+padding);
				}
			}
		}
	}

	/**
	 * draws the players on the map
	 * @param graphics
	 */
	private void drawPlayers(Graphics g){
		g.setColor(Color.BLACK);
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				for(GameMatter itm: grid[i][j].getItems()){
					if(itm instanceof Bandit){
						if(itm.equals(player))
							g.setColor(Color.MAGENTA);
						g.fillRect(j*widthBlock+padding, i*heightBlock+padding, bandit, bandit);
						break;
					}
				}
			}
		}
	}

	/**
	 * Draws a textual map in the console to show
	 * the layout of the rooms
	 */
	public void drawTextualMap(){
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				if(grid[i][j] instanceof Room){
					if(((Room)grid[i][j]).getItems().size()>0)
						System.out.print("  I  ");
					else
						System.out.print("  R  ");
				}else{
					System.out.print("  =  ");
				}
			}
			System.out.println();
		}

		System.out.println("rows = "+grid.length+" cols = "+grid[0].length);
		System.out.println("I = Room has Item, R = Room has no Item, '=' = Is a Hallway");


		/*if(room.getNorth()!=null&&!drawn.contains(room.getNorth())){
			drawMap(room.getNorth());
		}
		if(room.getWest()!=null&&!drawn.contains(room.getWest())){
			drawMap(room.getWest());
		}

		if(!drawn.contains(room)){
			if(drawn.size()%2==0)
				System.out.println();
			if(room.getItems().size()>0)
				System.out.print("  I  ");//Show if the room has an Item
			else
				System.out.print("  R  ");
			drawn.add(room);
		}

		if(room.getEast()!=null&&!drawn.contains(room.getEast())){
			drawMap(room.getEast());
		}
		if(room.getSouth()!=null&&!drawn.contains(room.getSouth())){
			drawMap(room.getSouth());
		}*/
	}

}
