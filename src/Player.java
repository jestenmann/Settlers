import java.awt.Color;


public class Player {

	private Color color;
	private int [] resources;
	private GameInfo info;
	
	public GameInfo getInfo() {
		return info;
	}

	public void setInfo(GameInfo info) {
		this.info = info;
	}

	public Player(Color color) {
		this.color = color;
		resources = new int[5];
		for (int i =0; i < resources.length; i++) {
			resources[i] = 0;
		}
		
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getBrick() {
		return resources[0];
	}
	
	public int getGrain() {
		return resources[1];
	}
	
	public int getLumber() {
		return resources[2];
	}
	
	public int getOre() {
		return resources[3];
	}
	
	public int getWool() {
		return resources[4];
	}

	//different than place settlement b/c it doesn't have constraints about 
	public int placeInitialSettlement() {
		
		//some sort of algorithm to decide the best place to put a settlement 
		//returns the corner number to place the settlement at 
		//can query the tiles attached to the corner to find the best place
		//can query other corners 
		return 0;
	}
	
	
	public Edge placeRoad() {
		int start = 0;
		int end = 4;
		//must connect to a road or settlement 
		return new Edge(start, end);
	}
	
	public String toString() {
		return color.toString();
	}
	
}
