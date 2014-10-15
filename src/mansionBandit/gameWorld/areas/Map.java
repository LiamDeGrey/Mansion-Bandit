package mansionBandit.gameWorld.areas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * This class creates a visual representation of the mansion
 * @author Liam De Grey
 *
 */
public class Map extends JPanel{
	private MansionArea[][] grid;
	private boolean[][] visited;
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
	private BufferedImage pointer;
	private double locationX;
	private double locationY;
	private AffineTransform tx;
	private AffineTransformOp op;
	private double rotationRequired;

	public Map(Player player){
		this.player = player;
		grid = player.getGrid();
		initialiseVisited();
		adjacentGrid = player.getBandit().getAdjacentGrid();
		widthMap = (grid[0].length*widthBlock)+(padding*2);
		heightMap = (grid.length*heightBlock)+(padding*2);
		setVisible(true);
		setLayout(null);
		setPointer();
	}

	private void initialiseVisited() {
		visited = new boolean[grid.length][grid[0].length];
		for(int i=0; i<visited.length; i++) {
			for(int j=0; j<visited[0].length; j++) {
				visited[i][j] = false;
			}
		}
	}

	public void updateVisited() {
		visited = player.getBandit().getVisited();
	}


	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(widthMap, heightMap);
	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.decode("#D6D6CC"));
		g.fillRect(0, 0, widthMap, heightMap);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, widthMap-1, heightMap-1);
		drawGrid(g);
		drawWalls(g);
		//drawPlayers(g);
		drawBanditInitial(g);
	}

	public void drawBanditInitial(Graphics g) {
		if(player.getBandit().getRoomCoords(player.getBandit().getArea())[0]==-1
				||player.getBandit().getRoomCoords(player.getBandit().getArea())[1]==-1) {
			int i = player.getBandit().getAdjacentGrid()[0];
			int j = player.getBandit().getAdjacentGrid()[1];
			if(i==0) {
				i = i*heightBlock+padding+2-heightBlock;
				j = j*widthBlock+padding+2;
			}else if(i==grid.length-1) {
				i = i*heightBlock+padding+2+heightBlock;
				j = j*widthBlock+padding+2;
			}else if(j==grid[0].length-1) {
				j = j*widthBlock+padding+2+widthBlock;
				i = i*heightBlock+padding+2;
			}else if(j==0) {
				j = j*widthBlock+padding+2-widthBlock;
				i = i*heightBlock+padding+2;
			}
			transformPointer(player.getBandit().getFace());
			g.drawImage(op.filter(pointer, null), j, i, bandit, bandit, null);
		}
	}

	/**
	 * Draws the grid
	 * @param graphics
	 */
	private void drawGrid(Graphics g) {
		updateVisited();
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				if(visited[i][j]) {
					if(grid[i][j] instanceof Room){
						g.setColor(Color.decode("#818181"));
						g.fillRect(j*widthBlock+padding, i*heightBlock+padding, widthBlock, heightBlock);
					}else if(grid[i][j] instanceof Hallway){
						g.setColor(Color.decode("#BDBDBD"));
						g.fillRect(j*widthBlock+padding, i*heightBlock+padding, widthBlock, heightBlock);
					}
				}else {
					g.setColor(Color.BLACK);
					g.fillRect(j*widthBlock+padding, i*heightBlock+padding, widthBlock, heightBlock);
				}
				/*
				 * Draw the start position(van) on the map
				 */
				if(adjacentGrid[0]==i&&adjacentGrid[1]==j) {
					g.setColor(Color.decode("#B2B2FF"));//Purple
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
				if(player.getBandit().getRoomCoords(player.getBandit().getArea())[0]==i
						&&player.getBandit().getRoomCoords(player.getBandit().getArea())[1]==j){
					transformPointer(player.getBandit().getFace());
					g.drawImage(op.filter(pointer, null), j*widthBlock+padding+2, i*heightBlock+padding+2, bandit, bandit, null);
						}
			}
		}
	}

	public void transformPointer(Face face) {
		if(face==Face.SOUTHERN){
			rotationRequired = Math.toRadians(90);
		}else if(face==Face.WESTERN){
			rotationRequired = Math.toRadians(180);
		}else if(face==Face.NORTHERN){
			rotationRequired = Math.toRadians(270);
		}else if(face==Face.EASTERN){
			rotationRequired = Math.toRadians(0);
		}
		tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
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

	public void setPointer() {
		try {
			pointer = ImageIO.read(this.getClass().getResource("/GUIgraphics/pointer.png"));
			locationX = pointer.getWidth() / 2;
			locationY = pointer.getHeight() / 2;
		} catch (IOException e) {
			e.printStackTrace();
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

	}

}
