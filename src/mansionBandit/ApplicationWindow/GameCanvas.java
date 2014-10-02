package mansionBandit.ApplicationWindow;

import java.awt.Canvas;
import java.awt.Point;

import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Grabable;
import mansionBandit.gameWorld.matter.Key;

public class GameCanvas extends Canvas{

	public Grabable getItemAt(Point p){
		return new Key("key1", Face.NORTHERN,new Dimensions(10,10));
		//return null;
	}
}
