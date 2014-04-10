import java.awt.Color;
import java.util.ArrayList;


public class GameEngine {

	private GameBoard board; 
	private Player p1;
	private Player p2;
	private Player p3;
	private Player p4;
	private ArrayList<Player> players;
	
	public GameEngine () {
	
		players = new ArrayList<Player>();

		//use this to add AI strategy in the Player class 
		p1 = new Player(Color.BLUE);
		p2 = new Player(Color.ORANGE);
		p3 = new Player(Color.WHITE);
		p4 = new Player(Color.RED);
		
		
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);

		board = new GameBoard(players);
		board.setPlayers(players);
		
		
	}
	

	public void setup() {
		//each player places two settlements
		//each player places two roads
		
		boolean validSettlement;
		int settlement = -1; 
		Edge edge = null;
		
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < players.size(); i++) {
				
				Player currPlayer = players.get(i);

				
				while (!checkValidSettlement(settlement)) 
					settlement = currPlayer.placeInitialSettlement();
				
				board.placeSettlement(settlement, currPlayer);
				edge = currPlayer.placeRoad();
				
				while (!checkValidRoad(edge))
					board.placeRoad(edge, currPlayer);
			}
		}
	}
	
	public void turn(Player player) {
		//all the things that can be done in a turn
		//roll the dice
		//resource production/allocation
		//possibly moving the robber
		//build
		//buy development card
		//trade?
	}
	
	public boolean checkValidSettlement(int space) {
		//no one else on the space
		//distance rule
		//road must connect to it 
		
		if (space == -1) 
			return false;
		else 
			return true;
		
	}
	
	//takes in two corner numbers, and checks to see if a road can be placed between them
	public boolean checkValidRoad(Edge edge) {
		//check to see it connects to a settlement
		//check to see no one else on the space
		
		return true;
	}
	
	public GameBoard getBoard () {
		return board;
	}
	
	
}
