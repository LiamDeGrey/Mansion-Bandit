package mansionBandit.factory;

import java.util.NoSuchElementException;
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
import mansionBandit.gameWorld.matter.Knife;
import mansionBandit.gameWorld.matter.Sellable;

/**
 * item template contains a set of specifications that it uses to create objects in the game
 * @author Andy
 *
 */
public class ItemTemplate {

	private int valueMin, valueRange, sizeMin, sizeRange, yMin, yRange;
	private String name, description, image, type;
	private Random random;
	private boolean corruptedTemplate = false;

	public ItemTemplate(String input){
		//read the string into a template
		try {
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
		} catch (NoSuchElementException e){
			//bad scan
			corruptedTemplate = true;
			return;
		}
		//run checks on inputs
		//check for negatives
		if (valueMin < 0 || valueRange < 0 || sizeMin < 0 || sizeRange < 0){
			corruptedTemplate = true; return;
		}
		//check to make sure within 100x100 bounds
		if (sizeMin + sizeRange > 100){
			corruptedTemplate = true;
			return;
		}
		random = new Random();
	}

	/**
	 * generates and returns a new randomised item
	 * @param face the face to set the item to
	 * @return the new item (GameMatter)
	 */
	public GameMatter getItem(Face face){
		if (corruptedTemplate){
			//template is bad, return null.
			return null;
		}

		int value, scale, x, y = 0;
		double r = random.nextDouble();
		value = (int) (valueMin + (valueRange * r));
		scale = (int) (sizeMin + (sizeRange * r));

		//get x and y
		if (face == Face.CEILING || face == Face.FLOOR){
			x = random.nextInt(100 - scale + 1) + (scale / 2) - 1;
			y = random.nextInt(100 - scale + 1) + (scale / 2) - 1;
		} else {
			x = random.nextInt(100 - scale + 1) + (scale / 2) - 1;
			y = random.nextInt(yRange + 1) + yMin - 1;
		}

		Dimensions position = new Dimensions(x, y, scale);

		if (type.equals("sell")){
			return new Sellable(name, description, image, face, position, value);
		} else if (type.equals("static")){
			//return new furniture
			return new FurnitureStatic(name, description, image, face, position);
		} else if (type.equals("key")){
			return new Key(name, description, image, position);
		} else if (type.equals("knife")){
			return new Knife(name, description, image, face, position);
		} else {
			return null;
		}
	}
}
