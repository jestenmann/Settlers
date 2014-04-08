import java.awt.Color;


public class Player {

	private Color color;
	private GameBoard board;
	
	public Player(Color color, GameBoard board) {
		this.color = color;
		this.board = board;
		//Mladen sucks
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
