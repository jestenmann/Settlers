package game;
import game.components.Corner;
import game.components.Edge;
import game.components.NumberToken;
import game.components.Resource;
import game.components.Tile;
import game.players.Player;

import java.awt.Color;
import java.util.ArrayList;


public class GameEngine {

	private GameBoard board; 
	private Player p1;
	private Player p2;
	private Player p3;
	private Player p4;
	private ArrayList<Player> players;
	private Player currPlayer;
	
	public GameInfo gameInfo;
	
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
		
		gameInfo = new GameInfo(board);
		p1.setInfo(gameInfo);
		p2.setInfo(gameInfo);
		p3.setInfo(gameInfo);
		p4.setInfo(gameInfo);
		
	}
	

	private void setup() {
		//each player places two settlements
		//each player places two roads
		
		int settlement = -1; 
		Edge edge = null;
		
		//goes through twice, each player gets to place a settlement and a road 
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < players.size(); i++) {
				currPlayer = players.get(i);

				while (!checkValidInitialSettlement(settlement)) {

					settlement = currPlayer.buildInitialSettlement();
				}
				
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
				board.buildSettlement(settlement, currPlayer);
				edge = currPlayer.buildRoad();
				while (!checkValidRoad(edge)) {
					edge = currPlayer.buildRoad();
				}
				
				try {
			    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				board.buildRoad(edge, currPlayer);

				
			}
		}
	}
	
	private void turn(Player player) {
		//all the things that can be done in a turn
		//resource production/allocation
		//possibly moving the robber
		//build
		//buy development card
		//trade?
	}
	
	private boolean checkValidInitialSettlement(int space) {
		//if no space has been picked return false
		if (space == -1)
			return false;
		
		Corner curr = board.corners.get(space);
		
		//check if someone else is on the settlement
		if (curr.isOccupied())
			return false;
		
		//check the distance rule
		
		else 
			return true;
		
	}
	
	//takes in two corner numbers, and checks to see if a road can be placed between them
	private boolean checkValidRoad(Edge edge) {
		
		//check to see if it is a valid edge 
		Corner beginningCorner = board.corners.get(edge.getBeginningId());
		Corner endCorner = board.corners.get(edge.getEndId());
		Edge e = null;
		
		boolean valid = false;
		for (int i = 0; i < board.edges.size(); i++) {
			e = board.edges.get(i);
			if (e.getBeginningCorner().equals(beginningCorner) && e.getEndCorner().equals(endCorner)) {
				valid = true;
				break;
			}
		}
		
		if (!valid) {
			return false;
		}
		
		//check to see no one else on the space
		if (e.getOwner() != null)
			return false;
		
		//check to see it connects to a settlement
		
		Player beginningOwner = e.getBeginningCorner().getOwner();
		Player endOwner = e.getBeginningCorner().getOwner();
		
		if (beginningOwner != null) {
			if ((beginningOwner.equals(currPlayer))) {
				return true;
			}
		}
		
		if (endOwner != null) {
			if ((endOwner.equals(currPlayer))) {
				return true;
			}
		}
		
		//check to see if it connects to a road
		valid = false;
		ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
		
		for (int i = 0; i < board.edges.size(); i++) {
			e = board.edges.get(i);
			if (e.getBeginningCorner().equals(beginningCorner) && !(e.getEndCorner().equals(endCorner))) {
				adjacentEdges.add(e);
			}
			if (!(e.getBeginningCorner().equals(beginningCorner)) && e.getEndCorner().equals(endCorner)) {
				adjacentEdges.add(e);
			}
			if (e.getBeginningCorner().equals(endCorner) || e.getEndCorner().equals(beginningCorner))
				adjacentEdges.add(e);
		}
		
		for (int i = 0; i < adjacentEdges.size(); i++) {
			Edge curr = adjacentEdges.get(i);
			//System.out.println(curr.getBeginningCorner());
			//System.out.println(curr.getEndCorner());
			if (curr.getOwner() != null) {
				if (curr.getOwner().equals(currPlayer)) {
					valid = true;
					break;
				}
			}
		}
		
		if (!valid) {
			System.out.println("loop");
			return false;
		}
		
		//if everything is true return true
		return true;
	}
	
	public GameBoard getBoard () {
		return board;
	}
	
	public void run() {
		setup();
		rollDice();
		//check winner
		//go through a player's turn
	}
	
	private void rollDice() {
		
		//roll the dice 
		int dice1 = 1 + (int)(Math.random() * ((6 - 1) + 1));
		int dice2 = 1 + (int)(Math.random() * ((6 - 1) + 1));
		board.setDice(dice1, dice2);
		
		int sum = dice1 + dice2;
		
		//update the resources
		
		if (sum != 7) {
			updateResources(sum);
		}
		//robber
		else {
			System.out.println("Robber!");
		}
	}
	
	private void updateResources(int sum) {
		System.out.println("beginning resources update");
		for (int i = 0; i < board.corners.size(); i++) {
			Corner c = board.corners.get(i);
			ArrayList<Tile> tiles = c.getTiles();
			
			for (int j = 0; j < tiles.size(); j++) {
				Tile t = tiles.get(j);
				NumberToken n = t.getNumberToken();
				if (n != null) {
					if (n.getNumber() == sum) {
						Resource r = t.getResource();
						Player owner = c.getOwner();
						if (owner != null)
							owner.updateResource(r);
					}
				}
			}
		}
		System.out.println("resources updated");
		//update the resources in the game board 
		board.updateResources(players);
	}
}
