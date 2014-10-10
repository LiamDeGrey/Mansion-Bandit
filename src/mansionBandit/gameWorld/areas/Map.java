package mansionBandit.gameWorld.areas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import mansionBandit.gameWorld.matter.Bandit;
import mansionBandit.gameWorld.matter.GameMatter;

/**
 * This class creates a visual representation of the mansion
 * @author Liam De Grey
 *
 */
public class Map extends JPanel{
	private MansionArea[][] grid;
	private static final int widthBlock = 10;
	private static final int heightBlock = 10;
	private static final int padding = 20;
	private static final int bandit = 5;

	public Map(MansionArea[][] grid){
		this.grid = grid;
		setPreferredSize(new Dimension((grid[0].length*widthBlock)+(padding*2), (grid.length*heightBlock)+(padding*2)));
		setLayout(null);
		setVisible(true);
	}

	@Override
	public void paintComponents(Graphics g){
		MansionArea leftMid = grid[grid.length/2][0];
		MansionArea topMid = grid[0][grid[0].length/2];
		MansionArea rightMid = grid[grid.length/2][grid[0].length-1];
		MansionArea botMid = grid[0][grid[0].length/2];

		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				if(grid[i][j] instanceof Room){
					g.setColor(Color.RED);
					g.fillRect(i*widthBlock+padding, j*heightBlock+padding, widthBlock, heightBlock);
				}else if(grid[i][j] instanceof Hallway){
					g.setColor(Color.GRAY);
					g.fillRect(i*widthBlock+padding, j*heightBlock+padding, widthBlock, heightBlock);
				}
				if(grid[i][j].equals(leftMid)){
					g.setColor(Color.BLUE);
					g.fillRect(padding, j*heightBlock+padding, widthBlock, heightBlock);
				}else if(grid[i][j].equals(topMid)){
					g.setColor(Color.BLUE);
					g.fillRect(i*widthBlock+padding, padding, widthBlock, heightBlock);
				}else if(grid[i][j].equals(rightMid)){
					g.setColor(Color.BLUE);
					g.fillRect(i*widthBlock+widthBlock+padding, j*heightBlock+padding, widthBlock, heightBlock);
				}else if(grid[i][j].equals(botMid)){
					g.setColor(Color.BLUE);
					g.fillRect(i*widthBlock+padding, j*heightBlock+heightBlock+padding, widthBlock, heightBlock);
				}
				drawPlayers(g);
			}
		}
	}

	private void drawPlayers(Graphics g){
		g.setColor(Color.BLACK);
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				for(GameMatter itm: grid[i][j].getItems()){
					if(itm instanceof Bandit){
						g.fillRect(i*widthBlock+padding, j*heightBlock+padding, bandit, bandit);
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
