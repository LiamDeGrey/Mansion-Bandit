package mansionBandit.ApplicationWindow;
import java.awt.Point;

import javax.swing.JFrame;


public class Main {

	private GameFrame gameFrame;
	public Main(){
		gameFrame = new GameFrame(this);
		
	}
	
public static void main(String args[]) {
		 Main game = new Main();
}

public Object getObjectAt(Point point) {
	// TODO Auto-generated method stub
	return null;
}

public boolean useItemOnObject(Object objectAt) {
	// TODO Auto-generated method stub
	return false;
}

public boolean useItemOnNPC(Object objectAt) {
	// TODO Auto-generated method stub
	return false;
}

public Object getNPCAt(Point point) {
	// TODO Auto-generated method stub
	return null;
}

public boolean atEmptyInventorySlot(Point point) {
	// TODO Auto-generated method stub
	return false;
}

public void addToInventory(Item draggingItem) {
	// TODO Auto-generated method stub
	
}

public void dropObject(Point point) {
	// TODO Auto-generated method stub
	
}

public Object getInstanceAt(Point point) {
	// TODO Auto-generated method stub
	return null;
}

public String getDescription(Object instanceAt) {
	// TODO Auto-generated method stub
	return null;
}

public Item getItemAt(Point point) {
	// TODO Auto-generated method stub
	return null;
}

public void moveForward() {
	// TODO Auto-generated method stub
	
}

public void turnRight() {
	// TODO Auto-generated method stub
	
}

public void turnLeft() {
	// TODO Auto-generated method stub
	
}

public boolean useItemOnItem(Object objectAt) {
	// TODO Auto-generated method stub
	return false;
}

public void addItemToInventory(Item draggingItem) {
	// TODO Auto-generated method stub
	
}

public void dropItem(Point point) {
	// TODO Auto-generated method stub
	
}

public Item getInventoryItem(Point point) {
	// TODO Auto-generated method stub
	return null;
}

public void removeInventoryItem(Point point) {
	// TODO Auto-generated method stub
	
}



}
