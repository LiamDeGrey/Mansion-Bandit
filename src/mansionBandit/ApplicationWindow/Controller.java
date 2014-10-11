package mansionBandit.ApplicationWindow;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import mansionBandit.gameView.GamePanel;
import mansionBandit.gameWorld.main.Player;
import mansionBandit.gameWorld.matter.Grabable;

/**
 * The controller handles control of a players movements and actions
 * @author Theo
 *
 */
public class Controller implements MouseListener, KeyListener{

	Player player;
	GamePanel gamePanel;
	Grabable draggingItem; //the item the player is dragging
	private final int mouseOffSetY;
	GameFrame gameFrame;
	
	Controller(Player p, GamePanel gp, GameFrame gameframe){
		player = p;
		gamePanel = gp;
		gameFrame = gameframe;
		mouseOffSetY = gameFrame.getInsets().top;
	}
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//only checks for user input if the game has started

		if(gameFrame.gameStarted()){

		System.out.println("key released: "
				+ KeyEvent.getKeyText(e.getKeyCode()));

		// if the user presses W
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {

			// attempt to move the player forwards
			player.moveForward();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {

			// turn the player right
			player.turnRight();
		}

		else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {

			// turn the player left
			player.turnLeft();
		}
		}
		
		//resets descriptiontext
		gameFrame.setDescriptionText("");
		
		gamePanel.update();
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent e) {
	
		

		if(e.getButton() == MouseEvent.BUTTON1){
		int mouseX = e.getPoint().x;
		int mouseY = e.getPoint().y - mouseOffSetY;

				//only checks for user input if the game has started

				if(gameFrame.gameStarted()){

					//debug code
					System.out.println("Mouse clicked at " + e.getPoint().toString());

				// firstly check that the player isn't already holding something
				if (draggingItem == null) {
					// check if there is an item that can be dragged at the current
					// mouse position
					if (gamePanel.getObject(mouseX,mouseY) instanceof Grabable) {

						// begin dragging the item at mouse position
						draggingItem = (Grabable) gamePanel.getObject(mouseX,mouseY);

						//hide the item from the room so that it no longer appears on the screen and CANT BE INTERRACTED WITH
						//TODO: remove item from room

						// SET CURSOR TO ITEM HERE //
						gameFrame.setCursorImage(e, draggingItem.getImage() +".png");
					}

					//else check if they selected an item in an inventory slot and that slot has an item in it
					else if (player.getItem(gameFrame.getInventorySlot(mouseX,mouseY)) != null) {

						Grabable inventoryItem = player.getItem(gameFrame.getInventorySlot(mouseX,mouseY));

						// remove the item at the selected position from the players
						// inventory
						player.removeItem(inventoryItem, gameFrame.getInventorySlot(mouseX,mouseY));

						// set the removed item as the dragged item
						draggingItem = inventoryItem;

						// SET CURSOR TO ITEM HERE //
						gameFrame.setCursorImage(e, draggingItem.getImage() +".png");
					}
				}

				
				gameFrame.getGUICanvas().repaint();
				
				//resets descriptiontext
				gameFrame.setDescriptionText("");
				}
		}
		
		
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	
		
		int mouseX = e.getPoint().x;
		int mouseY = e.getPoint().y - mouseOffSetY;

		//only checks for user input if gameplay has started

		if(gameFrame.gameStarted()){

			//debug statement
			System.out.println("Mouse release at " + (mouseX) + " " + mouseY);

		// left button released
		if (e.getButton() == MouseEvent.BUTTON1) {

			// Check if the player is dragging something

			if (draggingItem != null) {

				// see if there is an object or enemy at this position and
				// whether it can be used on it
				if (gamePanel.getObject(mouseX,mouseY) != null) {

					// check if the item being dragged can be used on the
					// targeted item
					//TODO ADD ability to use items on other items
					if (draggingItem.useItemOn(gamePanel.getObject(mouseX,mouseY))) {

						// if it was used successfully, the item will have been
						// used and is deleted and the item is removed from
						// inventory
						draggingItem = null;

						// set the cursor back to default
						e.getComponent()
								.setCursor(
										Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					} else {

						// if the interaction was invalid the item is returned
						// to the players inventory

						player.addItem(draggingItem);
						// remove the dragging item
						draggingItem = null;

						// set the cursor back to default
						e.getComponent()
								.setCursor(
										Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}

				// check if mouse is at an inventory slot and that slot is empty
				else if(gameFrame.getInventorySlot(mouseX,mouseY)>=0 && player.getItem(gameFrame.getInventorySlot(mouseX,mouseY)) == null){

					// if its at this spot then stop dragging and add it to the players inventory at specified position

					player.addItem(draggingItem, gameFrame.getInventorySlot(mouseX,mouseY));

					System.out.println("added to inventory " + draggingItem.getImage());

					draggingItem = null;

					// set the cursor back to default
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				 else {
					// otherwise just drop the object at this position in the room

					 //TODO add drop item method
					//player.dropItem(e.getPoint());

					 draggingItem = null;

					// set the cursor back to default
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}

			}

		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			
			// check if there is an object/creature/item at mouse position
			if (gamePanel.getObject(mouseX,mouseY) != null) {
				
				// change the description text to the items description
				String descriptionText =("<html><p><center>" +  gamePanel.getObject(mouseX, mouseY).getDescription() + "</center></p></html>");
				gameFrame.setDescriptionText(descriptionText);
			}
		}


		//repaint the canvas so that changes show up
		gameFrame.getGUICanvas().repaint();

		
		
	}

}
}