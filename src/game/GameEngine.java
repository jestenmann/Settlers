package game;
import game.components.Corner;
import game.components.DevelopmentCard;
import game.components.Edge;
import game.components.NumberToken;
import game.components.Resource;
import game.components.Tile;
import game.players.AIPlayer;
import game.players.HumanPlayer;
import game.players.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GameEngine {

	GameBoard board;
	private InfoBoard infoBoard;
	private Player p1;
	private Player p2;
	private Player p3;
	private Player p4;
	ArrayList<Player> players;
	Player currPlayer;
	
	private int largestArmy;
	private int longestRoad;
	
	ArrayList<DevelopmentCard> developmentCards;
	
	public GameInfo gameInfo;
	boolean wantToBuildSettlement;
	boolean wantToBuildCity;
	boolean wantToBuildRoad;
	int start;
	
	public GameEngine () {
		
		makeDevelopmentCards();
		
		players = new ArrayList<Player>();

		//use this to add AI strategy in the Player class 
		p1 = new HumanPlayer(Color.BLUE);
		p2 = new AIPlayer(Color.ORANGE);
		//p3 = new AIPlayer(Color.WHITE);
		//p4 = new AIPlayer(Color.RED);
		
		
		players.add(p1);
		players.add(p2);
		//players.add(p3);
		//players.add(p4);

		board = new GameBoard();
		currPlayer = p1;
		
		gameInfo = new GameInfo(board);
		p1.setInfo(gameInfo);
		p2.setInfo(gameInfo);
		//p3.setInfo(gameInfo);
		//p4.setInfo(gameInfo);
		
		largestArmy= 0;
		longestRoad = 0;
		
		
		infoBoard = new InfoBoard(players, this);
		
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
				if (currPlayer.type.equals("AIPlayer")) {
					while (!checkValidInitialSettlement(settlement)) {
	
						settlement = currPlayer.buildInitialSettlement();
					}
					
					try {
					    Thread.sleep(1000);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
					
					board.buildSettlement(settlement, currPlayer);
					currPlayer.addVictoryPoint();
					
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
				else {
					infoBoard.buildInitialSettlement();
					infoBoard.buildInitialRoad();
				}
				
			}
		}
	}
	
	private void turn(Player player) {
		currPlayer = player;
		board.setCurrPlayer(player);
		
		/**
		 * building stuff
		 */
		
		//settlement
		if (player.wantsToBuildSettlement()) { 
			if (checkSettlementResources()) {
				int settlement = player.buildSettlement();
				if (checkValidSettlement(settlement)) {
					board.buildSettlement(settlement, player);
					player.setBrick(player.getBrick() - 1);
					player.setLumber(player.getLumber() - 1);
					player.setGrain(player.getGrain() - 1);
					player.setWool(player.getWool() - 1);
					System.out.println("built settlement");
					currPlayer.addVictoryPoint();
					
				}
			}
		}
		
		//development card 
		if (developmentCards.size() > 0) {
			if (player.wantsToBuildDevelopmentCard()) {
				if (checkDevCardResources()) {
						player.setGrain(player.getGrain() - 1);
						player.setOre(player.getOre() - 1);
						player.setWool(player.getWool()- 1);
						DevelopmentCard d = developmentCards.remove(0);
						player.addDevelopmentCard(d);
					}
				
			}
		}
		
		//city
		if (player.wantsToBuildCity()) {
			if (checkCityResources()) {
				int city = player.buildCity();
				if (checkValidCity(city)) {
					board.buildCity(city, player);
					player.setGrain(player.getGrain() - 2);
					player.setOre(player.getOre() - 3);
					System.out.println("built city");
					currPlayer.addVictoryPoint();
				}
			}
		}
		
		//road
		if (player.wantsToBuildRoad()) {
			if (checkRoadResources()) {
				Edge edge = player.buildRoad();
				if (checkValidRoad(edge)) {
					board.buildRoad(edge, player);
					player.setBrick(player.getBrick() - 1);
					player.setLumber(player.getLumber() - 1);
				}
			}
		}

		//trade
		
		if (player.wantsToTrade()) {
			Resource tradeAway = player.tradeAway();
			Resource tradeFor = player.tradeFor();
			
			
			if (tradeAway.equals(Resource.BRICK) && player.getBrick() >= 4) {
				//System.out.println("trading");
				player.setBrick(player.getBrick() - 4);
			}
			else if (tradeAway.equals(Resource.GRAIN) && player.getGrain() >= 4) {
				//System.out.println("trading");
				player.setGrain(player.getGrain() - 4);
			}
			else if (tradeAway.equals(Resource.LUMBER) && player.getLumber() >= 4) {
				//System.out.println("trading");
				player.setBrick(player.getLumber() - 4);
			}
			else if (tradeAway.equals(Resource.ORE) && player.getOre() >= 4) {
				//System.out.println("trading");
				player.setOre(player.getOre() - 4);
			}
			else if (tradeAway.equals(Resource.WOOL) && player.getWool() >= 4) {
				//System.out.println("trading");
				player.setWool(player.getWool() - 4);
			}
			
			if (tradeFor.equals(Resource.BRICK)) {
				player.setBrick(player.getBrick() + 1);
			}
			else if (tradeFor.equals(Resource.GRAIN)) {
				player.setGrain(player.getGrain() + 1);
			}
			else if (tradeFor.equals(Resource.LUMBER)) {
				player.setBrick(player.getLumber() + 1);
			}
			else if (tradeFor.equals(Resource.ORE)) {
				player.setOre(player.getOre() + 1);
			}
			else if (tradeFor.equals(Resource.WOOL)) {
				player.setWool(player.getWool() + 1);
			}
			
		}
		
		//play development card 
		if (player.wantsToPlayDevelopmentCard()) {
			if (player.hasDevelopmentCards()) {
				DevelopmentCard d = player.playDevelopmentCard();
				//System.out.println(currPlayer + " " + d);
				playDevelopmentCard(d);
			}
		}
		
		//check for change in longest road and largest army
		checkLargestArmy();
		checkLongestRoad();
		
		//debug print statements
		System.out.println("Player # cities " + player.getCities().size());
		System.out.println("Player # settlements " + player.getSettlements().size());
	}
	
	
	
	public void playDevelopmentCard(DevelopmentCard d) {
		currPlayer.removeDevelopmentCard(d);
		//do different stuff based on what development card it is
		
		if (d.equals(DevelopmentCard.VICTORY_POINT))
		{
			System.out.println("victory point development card");
			currPlayer.addVictoryPoint();
		}
		else if (d.equals(DevelopmentCard.KNIGHT)){
			currPlayer.addKnight();
		}
		else if (d.equals(DevelopmentCard.ROAD_BUILDING)) {
			//gets to build two free roads
			Edge edge = currPlayer.buildRoad();
			if (checkValidRoad(edge)) {
				board.buildRoad(edge, currPlayer);
			}
			Edge e = currPlayer.buildRoad();
			if(checkValidRoad(e)) {
				board.buildRoad(e, currPlayer);
			}
		}
		else if (d.equals(DevelopmentCard.YEAR_PLENTY)) {
			//take two resource cards 
			int resource = 0 + (int)(Math.random()*4); 
			if (resource == 0)
				currPlayer.setBrick(currPlayer.getBrick() + 1);
			else if (resource == 1)
				currPlayer.setGrain(currPlayer.getGrain() + 1);
			else if (resource == 2)
				currPlayer.setLumber(currPlayer.getLumber() + 1);
			else if (resource == 3)
				currPlayer.setOre(currPlayer.getOre() + 1);
			else if (resource == 4)
				currPlayer.setWool(currPlayer.getWool() + 1);
			
			//the second card 
			resource = 0 + (int)(Math.random()*4); 
			if (resource == 0)
				currPlayer.setBrick(currPlayer.getBrick() + 1);
			else if (resource == 1)
				currPlayer.setGrain(currPlayer.getGrain() + 1);
			else if (resource == 2)
				currPlayer.setLumber(currPlayer.getLumber() + 1);
			else if (resource == 3)
				currPlayer.setOre(currPlayer.getOre() + 1);
			else if (resource == 4)
				currPlayer.setWool(currPlayer.getWool() + 1);
		}
		else if (d.equals(DevelopmentCard.MONOPOLY)) {
			//get to pick a card to take from every other player
			Resource r = currPlayer.pickMonopolyResource();
			int count = 0;
			for (int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				if (!(p.equals(currPlayer))) {
					if (r.equals(Resource.BRICK)){
						count = p.getBrick();
						p.setBrick(0);
						currPlayer.setBrick(currPlayer.getBrick() + count);
					}
					if (r.equals(Resource.GRAIN)){
						count = p.getGrain();
						p.setGrain(0);
						currPlayer.setGrain(currPlayer.getGrain() + count);
					}
					if (r.equals(Resource.LUMBER)){
						count = p.getLumber();
						p.setLumber(0);
						currPlayer.setLumber(currPlayer.getLumber() + count);
					}
					if (r.equals(Resource.ORE)){
						count = p.getOre();
						p.setOre(0);
						currPlayer.setBrick(currPlayer.getOre() + count);
					}
					if (r.equals(Resource.WOOL)){
						count = p.getWool();
						p.setWool(0);
						currPlayer.setWool(currPlayer.getWool() + count);
					}
				}
			}
		}
		
	}
	
	public boolean checkSettlementResources() {
		if (currPlayer.getBrick() >= 1 && currPlayer.getLumber() >= 1 && currPlayer.getWool() >= 1 && currPlayer.getGrain() >= 1)
			return true;
		else
			return false;
	}
	
	public boolean checkRoadResources() {
		if (currPlayer.getBrick() >= 1 && currPlayer.getLumber() >= 1)
			return true;
		else
			return false;
	}
	
	public boolean checkCityResources() {
		if (currPlayer.getGrain() >= 2 && currPlayer.getOre() >= 3)
			return true;
		else
			return false;
	}
	
	public boolean checkDevCardResources() {
		if (currPlayer.getWool() >= 1 && currPlayer.getGrain() >= 1 && currPlayer.getOre() >= 1)
			return true;
		else
			return false;
	}
	
	
	
	public boolean checkValidCity(int space) {
		if (space == -1)
			return false;
		Corner curr = board.corners.get(space);
		if (curr.hasCity())
			return false;
		return curr.getOwner().equals(currPlayer);
	}
	
	public boolean checkValidSettlement(int space) {
		//if no space has been picked return false
		if (space == -1)
			return false;
		
		Corner curr = board.corners.get(space);
		
		//check if someone else is on the settlement
		if (curr.isOccupied())
			return false;
		
		//check the distance rule
		ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
		
		for (int i = 0; i < board.edges.size(); i++) {
			Edge e = board.edges.get(i);
			if (e.getBeginningCorner().equals(curr) || e.getEndCorner().equals(curr))
				adjacentEdges.add(e);	
		}
		
		for (int i = 0; i < adjacentEdges.size(); i++) {
			Edge currEdge = adjacentEdges.get(i);
			if (currEdge.getBeginningCorner().isOccupied() || currEdge.getEndCorner().isOccupied())
				return false;
		}
		
		//check if connected to a road
		for (int i = 0; i < adjacentEdges.size(); i++) {
			Edge currEdge = adjacentEdges.get(i);
			if (currEdge.getOwner() != null) {
				if (currEdge.getOwner().equals(currPlayer)) {
					return true;
			}
			}
		}
		
		return false;

	}
	
	public boolean checkValidInitialSettlement(int space) {
		//if no space has been picked return false
		if (space == -1)
			return false;
		
		Corner curr = board.corners.get(space);
		
		//check if someone else is on the settlement
		if (curr.isOccupied())
			return false;
		
		//check the distance rule
		ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
		
		for (int i = 0; i < board.edges.size(); i++) {
			Edge e = board.edges.get(i);
			if (e.getBeginningCorner().equals(curr) || e.getEndCorner().equals(curr))
				adjacentEdges.add(e);	
		}
		
		for (int i = 0; i < adjacentEdges.size(); i++) {
			Edge currEdge = adjacentEdges.get(i);
			if (currEdge.getBeginningCorner().isOccupied() || currEdge.getEndCorner().isOccupied())
				return false;
		}
		
		//return true if everything is good 
			return true;
		
	}
	
	//takes in two corner numbers, and checks to see if a road can be placed between them
	public boolean checkValidRoad(Edge edge) {
		
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
		Player endOwner = e.getEndCorner().getOwner();
		
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
			//check if it's not the same edge
			if (!(e.getBeginningCorner().equals(beginningCorner) && e.getEndCorner().equals(endCorner))) {
				
				if (beginningCorner.equals(e.getEndCorner()) || beginningCorner.equals(e.getBeginningCorner()))
					adjacentEdges.add(e);
				else if (endCorner.equals(e.getEndCorner()) || endCorner.equals(e.getEndCorner()))
						adjacentEdges.add(e);
			}
		}
		
		for (int i = 0; i < adjacentEdges.size(); i++) {
			Edge curr = adjacentEdges.get(i);
			if (curr.getOwner() != null) {
				if (curr.getOwner().equals(currPlayer)) {
					valid = true;
					break;
				}
			}
		}
		
		if (!valid) {
			return false;
		}
		
		//if everything is true return true
		return true;
	}
	
	public GameBoard getBoard () {
		return board;
	}
	
	public InfoBoard getInfoBoard() {
		return infoBoard;
	}
	
	public void run() {
		setup();
		int maxPoints = 0;
		int winner = 0;
		int numTurns = 0;
	
		while (currPlayer.getPoints() < 10) {
			
			for (int j = 0; j < players.size(); j++) {
				currPlayer = players.get(j);
				rollDice();
				
				if (players.get(j).type.equals("HumanPlayer")) {
					currPlayer = players.get(j);
					infoBoard.add(infoBoard.infoPanel(), BorderLayout.SOUTH);
					//keep going until the player clicks end turn 
					infoBoard.turnOver = false;
					boolean turnOver = false;
					
					while(!turnOver) {
						turnOver = infoBoard.turnOver;
						
						if (wantToBuildSettlement && board.settlement != 0) {
							playerBuildSettlement();
						}
						
						if(wantToBuildRoad && board.settlement != 0) {
							playerBuildRoad();
						}
						
						if (wantToBuildCity && board.settlement != 0) {
							playerBuildCity();
						}
					}
					
					
				}
				else 
					turn(players.get(j));
				
				//check victory points after 
				if (players.get(j).getPoints() >= 10) {
					winner = j;
					break;
				}
				System.out.println(players.get(j) + " " + players.get(j).getPoints());
				numTurns++;
				try {
				    Thread.sleep(100);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
			}
			
			
		}
		//System.out.println(numTurns);
		System.out.println(players.get(winner));
	}
	
	public void checkLargestArmy() {
		if (currPlayer.getArmySize() > largestArmy) {		
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).hasLargestArmy()) {
					players.get(i).setLargestArmy(false);
					players.get(i).removeVictoryPoint();
				}
			}
			
			currPlayer.setLargestArmy(true);
			currPlayer.addVictoryPoint();
			largestArmy = currPlayer.getArmySize();
			
		}
		
	}
	
	public void checkLongestRoad() {
		
	}
	
	public void testPrint() {
		System.out.println("testing");
	}
	
	private void rollDice() {

		
		//roll the dice 
		int dice1 = 1 + (int)(Math.random() * ((6 - 1) + 1));
		int dice2 = 1 + (int)(Math.random() * ((6 - 1) + 1));
		infoBoard.setDice(dice1, dice2);
		
		int sum = dice1 + dice2;
		
		//update the resources
		
		if (sum != 7) {
			updateResources(sum);
		}
		//robber
		else {
			//System.out.println("Sum: " + sum + ", move robber");
			if (currPlayer.type.equals("HumanPlayer")) {
				infoBoard.moveRobber();
			}
			else
				board.moveRobber(currPlayer.moveRobber());
			halveResources();
		}
	}
	
	private void halveResources() {
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			
			if ((p.getBrick() + p.getGrain() + p.getLumber() + p.getOre() + p.getWool()) > 7) {
				p.halveResources();
			}
				
		}
	}
	
	private void updateResources(int sum) {
		for (int i = 0; i < board.corners.size(); i++) {
			Corner c = board.corners.get(i);
			ArrayList<Tile> tiles = c.getTiles();
			for (int j = 0; j < tiles.size(); j++) {
				Tile t = tiles.get(j);
				if (!t.hasRobber()) {
					NumberToken n = t.getNumberToken();
					if (n != null) {
						if (n.getNumber() == sum) {
							Resource r = t.getResource();
							Player owner = c.getOwner();
							if (owner != null) {
								owner.updateResource(r);
							}
						}
					}
				}
			}
		}

		//update the resources in the game board 
		infoBoard.updateResources(players);
	}
	
	private void makeDevelopmentCards() {
		developmentCards = new ArrayList<DevelopmentCard>();
		
		for (int i = 0; i < 14; i++) {
			developmentCards.add(DevelopmentCard.KNIGHT);
		}
		
		developmentCards.add(DevelopmentCard.MONOPOLY);
		developmentCards.add(DevelopmentCard.MONOPOLY);
		
		developmentCards.add(DevelopmentCard.ROAD_BUILDING);
		developmentCards.add(DevelopmentCard.ROAD_BUILDING);
		
		developmentCards.add(DevelopmentCard.YEAR_PLENTY);
		developmentCards.add(DevelopmentCard.YEAR_PLENTY);
		
		developmentCards.add(DevelopmentCard.VICTORY_POINT);
		developmentCards.add(DevelopmentCard.VICTORY_POINT);
		
		Collections.shuffle(developmentCards);
	}
	
	public void playerBuildSettlement() {
		int settlement = board.settlement;
		
		
		if (this.checkValidSettlement(settlement)) {
			board.buildSettlement(settlement, currPlayer);
			currPlayer.setBrick(currPlayer.getBrick() - 1);
			currPlayer.setLumber(currPlayer.getLumber() - 1);
			currPlayer.setGrain(currPlayer.getGrain() - 1);
			currPlayer.setWool(currPlayer.getWool() - 1);
			System.out.println("built settlement");
			currPlayer.addVictoryPoint();
			
		}
		board.settlement = 0;
		wantToBuildSettlement = false;
	}
	
	public void playerBuildRoad() {
			start = board.settlement;
			board.turnOver = false;
			boolean turnOver = false;
			
			while(!turnOver) {
				turnOver = board.turnOver;
			
			}
			playerFinishRoad();
			
			wantToBuildRoad = false;
	}
	
	public void playerFinishRoad() {
		
		if (board.settlement < start) {
			int tmp = board.settlement;
			board.settlement = start;
			start = tmp;
		}
		Edge edge = new Edge(start, board.settlement);
		if (checkValidRoad(edge)) {
			board.buildRoad(edge, currPlayer);
			currPlayer.setBrick(currPlayer.getBrick() - 1);

		}
		start = 0;
		board.settlement = 0;
		
	}
	
	public void playerBuildCity() {
		
		int settlement = board.settlement;
		
		if (checkValidCity(board.settlement)) {
			board.buildCity(board.settlement, currPlayer);
			currPlayer.setGrain(currPlayer.getGrain() - 2);
			currPlayer.setOre(currPlayer.getOre() - 3);
			System.out.println("built city");
			currPlayer.addVictoryPoint();
			
		}
		
		board.settlement = 0;
		wantToBuildCity = false;
	}
}
