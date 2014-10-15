package mansionBandit.gameView.test;

import static org.junit.Assert.*;
import mansionBandit.factory.ItemTemplate;
import mansionBandit.gameWorld.matter.*;

import org.junit.Test;

public class ItemTemplateTests {
	
	@Test
	public void checkValidItems(){
		//Sellable items
		runTest("test1", "sell", "testName", "test Description", 0, 100, 0, 100);
		runTest("test2", "sell", "test Name", "test Description", 99, 1, 99, 1);
		runTest("test3", "sell", "test!#*&^%Name", "@*&^@$)", 0, 0, 0, 0);
		runTest("test4", "sell", " ", " ", 50, 0, 50, 0);
		
		//Static items
		runTest("test5", "static", "testName", "test Description", 0, 100, 0, 100);
		runTest("test6", "static", "test Name", "test Description", 100, 0, 10, 0);
		
		//Knives
		runTest("test7", "knife", "testName", "test Description", 0, 100, 0, 100);
		runTest("test8", "knife", "test Name", "test Description", 100, 0, 10, 0);
		
		//TODO add tests for keys if we add them to the game
	}
	
	@Test
	public void checkInvalidItems(){
		assertTrue("giving bad type should return a null item", runFailingTest("test1 name description badType 0 100 0 100"));
		assertTrue("giving bad less arguments in the input string should return a null item", runFailingTest("test2"));
		assertTrue("a negative bound should return a null item", runFailingTest("test3 name description sell -1 100 0 100"));
		assertTrue("a negative bound should return a null item", runFailingTest("test4 name description sell 0 100 1 -1"));
		assertTrue("a negative bound should return a null item", runFailingTest("test5 name description sell 0 100 -1 1"));
		assertTrue("a negative bound should return a null item", runFailingTest("test6 name description sell 0 -1 0 100"));
		assertTrue("bounds out of range should return a null item", runFailingTest("test7 name description sell 0 101 0 100"));
		assertTrue("bounds out of range should return a null item", runFailingTest("test8 name description sell -1 100 0 100"));
	}
	
	//passed lines should result in the Template returning a null item
	private boolean runFailingTest(String line){
		ItemTemplate template = new ItemTemplate(line);
		return template.getItem(Face.NORTHERN) == null;
	}
	
	/**
	 * runs tests over multiple generated objects from a ItemTemplate
	 */
	private void runTest(String image, String type, String name, String desc, int scaleMin, int scaleRange, int valMin, int valRange){
		TemplateTester test = new TemplateTester(image, type, name, desc, scaleMin, scaleRange, valMin, valRange);
		ItemTemplate template = new ItemTemplate(test.getLine());
		for (int i = 0; i < 1000; i++){
			GameMatter item = template.getItem(Face.NORTHERN);
			assertTrue("(" + image + ") generated item name should match the name given to template", test.compareName(item));
			assertTrue("(" + image + ") generated item image should match the image given to template", test.compareImage(item));
			assertTrue("(" + image + ") generated item description should match the description given to template", test.compareDesc(item));
			assertTrue("(" + image + ") generated item should be of the type given to template", test.compareType(item));
			assertTrue("(" + image + ") generated item scale should be within bounds given to template", test.compareScale(item));
			assertTrue("(" + image + ") generated item value should be within bounds given to template", test.compareValue(item));
		}
	}
	
	/**
	 * used to test templates
	 * @author Andy
	 *
	 */
	public class TemplateTester{
		String image;
		String type;
		String name;
		String desc;
		int scaleMin;
		int scaleRange;
		int valMin;
		int valRange;
		
		public TemplateTester(String image, String type, String name, String desc, int scaleMin, int scaleRange, int valMin, int valRange){
			this.image = image;
			this.type = type;
			this.name = name;
			this.desc = desc;
			this.scaleMin = scaleMin;
			this.scaleRange = scaleRange;
			this.valMin = valMin;
			this.valRange = valRange;
		}
		
		public String getLine(){
			return image + " " + name.replace(' ', '_') + " " + desc.replace(' ', '_') + " " +  type + " " + scaleMin + " " + scaleRange + " " + valMin + " " + valRange;
		}
		
		/**
		 * compare item types
		 */
		public boolean compareType(GameMatter i){
			switch (type){
			case "sell":
				return i instanceof Sellable;
			case "key":
				return i instanceof Key;
			case "static":
				return i instanceof FurnitureStatic;
			case "knife":
				return i instanceof Knife;
			}
			return false;
		}

		/**
		 * compare names
		 */
		public boolean compareName(GameMatter i){
			return name.equals(i.getName());
		}

		/**
		 * compare descriptions
		 */
		public boolean compareDesc(GameMatter i){
			if (i instanceof Sellable){
				//sellable items have their value appended to the end
				return i.getDescription().startsWith(desc);
			}
			return desc.equals(i.getDescription());
		}
		
		/**
		 * compare images
		 */
		public boolean compareImage(GameMatter i){
			return image.equals(i.getImage());
		}

		/**
		 * compare values
		 */
		public boolean compareValue(GameMatter i){
			if (i instanceof Sellable){
				int val = ((Sellable) i).getValue();
				return val >= valMin && val <= valMin + valRange;
			} 
			//only care about this if sellable
			return true;
		}

		/**
		 * compare scales
		 */
		public boolean compareScale(GameMatter i){
			int scale =  i.getDimensions().getScale();
			return scale >= scaleMin && scale <= scaleMin + scaleRange;
		}
	}
}
