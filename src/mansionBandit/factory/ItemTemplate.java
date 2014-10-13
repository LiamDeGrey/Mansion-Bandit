package mansionBandit.factory;

import java.util.Random;
import java.util.Scanner;

import com.sun.xml.internal.ws.api.pipe.NextAction;

import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Door;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.FurnitureStatic;
import mansionBandit.gameWorld.matter.GameMatter;
import mansionBandit.gameWorld.matter.Key;
import mansionBandit.gameWorld.matter.Sellable;

/**
 * item template contains a set of specifications that it uses to create objects in the game
 * @author Andy
 *
 */
public class ItemTemplate {

	private int valueMin, valueRange, sizeMin, sizeRange;
	private String name, description, image, type;
	private Random random;
	
	public ItemTemplate(String input){
		//read the string into a template
		Scanner scan = new Scanner(input);
		image = scan.next();
		name = scan.next().replace('_', ' ');
		description = scan.next().replace('_', ' ');
		type = scan.next(); 
		sizeMin = scan.nextInt();
		sizeRange = scan.nextInt();
		valueMin = scan.nextInt();
		valueRange = scan.nextInt();
		scan.close();
		random = new Random();
	}
	
	/**
	 * generates and returns a new randomised item
	 * @param face the face to set the item to
	 * @return the new item (GameMatter)
	 */
	public GameMatter getItem(Face face){
		int value, scale, x, y = 0;
		double r = random.nextDouble();
		value = (int) (valueMin + (valueRange * r));
		scale = (int) (sizeMin + (sizeRange * r));
		
		//get x and y
		if (face == Face.CEILING || face == Face.FLOOR){
			x = random.nextInt(100 - scale) + (scale / 2);
			y = random.nextInt(100 - scale) + (scale / 2);
		} else {
			x = random.nextInt(100 - scale) + (scale / 2);
			y = random.nextInt(100 - scale) + scale;
		}
		
		//hack solution for bookshelf
		if (image.equals("bookshelf")){
			y = 100;
		}
		
		Dimensions position = new Dimensions(x, y, scale);
		
		if (type.equals("sell")){
			return new Sellable(name + GameMatter.getItemCounter(), description, image, face, position, value);
		} else if (type.equals("static")){
			//return new furniture
			return new FurnitureStatic(name + GameMatter.getItemCounter(), description, image, face, position);
		} else if (type.equals("key")){
			return new Key(name, face, position);
		} else {
			return null;
		}
	}
	
	
	public GameMatter getItem(Face face, Door door){
		int value, scale, x, y = 0;
		double r = random.nextDouble();
		value = (int) (valueMin + (valueRange * r));
		scale = (int) (sizeMin + (sizeRange * r));
		
		//get x and y
		if (face == Face.CEILING || face == Face.FLOOR){
			x = random.nextInt(100 - scale) + (scale / 2);
			y = random.nextInt(100 - scale) + (scale / 2);
		} else {
			x = random.nextInt(100 - scale) + (scale / 2);
			y = random.nextInt(100 - scale) + scale;
		}
		
		//hack solution for bookshelf
		if (image.equals("bookshelf")){
			y = 100;
		}
		
		if (x > door.getDimensions().getX() - (door.getDimensions().getScale() / 2)
				&& x < door.getDimensions().getX() + (door.getDimensions().getScale() / 2)){
			//x position is overlapping door so shift to the edge
			if (x < door.getDimensions().getX()){
				x = scale / 2;
			} else {
				x = 100 - (scale / 2);
			}
		}
		
		Dimensions position = new Dimensions(x, y, scale);
		
		if (type.equals("sell")){
			return new Sellable(name + GameMatter.getItemCounter(), description, image, face, position, value);
		} else if (type.equals("static")){
			//return new furniture
			return new FurnitureStatic(name + GameMatter.getItemCounter(), description, image, face, position);
		} else if (type.equals("key")){
			return new Key(name, face, position);
		} else {
			return null;
		}
	}
}
