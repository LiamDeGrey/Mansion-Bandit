package mansionBandit.gameWorld.matter;


import java.io.Serializable;


/**
 * All Items come under this title
 * @author Liam De Grey
 *
 */
public abstract class  GameMatter implements Serializable {
	private static int itemCounter = 0;
	private Face face;
	private Dimensions dimens;
	private String name;
	private String image;
	private String description;
	private int itemID;

	public GameMatter(String name, String description, String image, Face face, Dimensions position){
		this.name = name;
		this.image = image;
		this.face = face;
		this.dimens = position;
		this.description = description;
		itemID = getItemCounter();
	}

	/**
	 * returns the face that an item is on
	 */
	public Face getFace(){
		return face;
	}

	/**
	 * sets the Face that the item is on/looking at
	 */
	public void setFace(Face f){
		this.face = f;
	}

	/**
	 * returns the position of the item
	 */
	public Dimensions getDimensions(){
		return dimens;
	}

	/**
	 * sets the Dimensions of the item
	 */
	public void setDimensions(Dimensions d){
		this.dimens = d;
	}

	public boolean useItemOn(GameMatter itm) {
		return false;
	}

	/**
	 * returns the name of object
	 */
	public String getName(){
		return name;
	}

	/**
	 * returns the Image of the matter
	 */
	public String getImage(){
		return image;
	}

	/**
	 * Returns a description about the item
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * returns amount of items made
	 */
	public static int getItemCounter(){
		return itemCounter++;
	}

	public int getID(){
		return itemID;
	}


	@Override
	public boolean equals(Object o){
		if(o!=null && o instanceof GameMatter){
			return itemID == ((GameMatter) o).getID();
		}
		return false;
	}


}
