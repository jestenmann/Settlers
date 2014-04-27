package game.players;



import game.GameInfo;
import game.components.Corner;
import game.components.DevelopmentCard;
import game.components.Edge;
import game.components.Resource;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;


public class Player {

	//the color of the player 
	private Color color;
	
	//an array of how many resource cards the player has (in alphabetical order)
	private int [] resources;
	
	//the interface used to get information about the game board 
	private GameInfo info;
	
	//boolean special cards 
	private boolean largestArmy;
	private boolean longestRoad;
	
	//the number of victory points the player has 
	private int points;
	
	//array list of all the development cards the player has 
	private ArrayList<DevelopmentCard> developmentCards;
	
	private int knights;
	private int roadLength;

	public Player(Color color) {
		this.color = color;
		
		resources = new int[5];
		for (int i =0; i < resources.length; i++) {
			resources[i] = 0;
		}
		
		largestArmy = false;
		longestRoad = false;
		points = 0;
		knights = 0;
		roadLength = 0;
		
		developmentCards = new ArrayList<DevelopmentCard>();
		
	}
	
	public GameInfo getInfo() {
		return info;
	}

	public void setInfo(GameInfo info) {
		this.info = info;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setBrick(int x) {
		resources[0] = x;
	}
	
	public void setGrain(int x) {
		resources[1] = x;
	}
	
	public void setLumber(int x) {
		resources[2] = x;
	}
	
	public void setOre(int x) {
		resources[3] = x;
	}
	
	public void setWool(int x) {
		resources[4] = x;
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
	
	public int getPoints() {
		return points;
	}

	public String toString() {
		if (color.equals(Color.RED)){
			return "Red";
		}
		else if (color.equals(Color.BLUE)) {
			return "Blue";
		}
		else if (color.equals(Color.WHITE)){
			return "White";
		}
		else
			return "Orange";
	}
	
	public void updateResource (Resource r) {
		if (r.equals(Resource.BRICK))
			resources[0] = resources[0] + 1;
		else if (r.equals(Resource.GRAIN))
			resources[1] = resources[1] + 1;
		else if (r.equals(Resource.LUMBER))
			resources[2] = resources[2] + 1;
		else if (r.equals(Resource.ORE))
			resources[3] = resources[3] + 1;
		else if (r.equals(Resource.WOOL))
			resources[4] = resources[4] + 1;
	}
	
	public void removeDevelopmentCard (DevelopmentCard d) {
		
		developmentCards.remove(d);
	}
	
	public void setLargestArmy(boolean b) {
		largestArmy = b;
	}
	
	public void setLongestRoad(boolean b) {
		longestRoad = b;
	}
	
	public void addKnight() {
		knights++;
	}
	
	public void addVictoryPoint() {
		points++;
		System.out.println("victory point added");
	}
	
	public void addDevelopmentCard(DevelopmentCard d) {
		developmentCards.add(d);
		
	}

	public boolean hasDevelopmentCards() {
		return developmentCards.size() > 0;
	}
	
	public int getArmySize() {
		return knights;
	}
	
	public void removeVictoryPoint() {
		points--;
	}
	
	public boolean hasLargestArmy() {
		return largestArmy;
	}
	
	public boolean hasLongestRoad() {
		return longestRoad;
	}
	
	public boolean wantsToBuildRoad() {
		return true;
	}
	
	public boolean wantsToBuildSettlement() {
		return true;
	}
	
	public boolean wantsToBuildCity() {
		return true;
	}
	
	public boolean wantsToBuildDevelopmentCard() {
		return true;
	}
	
	public boolean wantsToPlayDevelopmentCard() {
		return true;
	}
	
	public boolean wantsToTrade() {
		return true;
	}
	
	/**
	 * Below are all the methods used to make moves - override these 
	 */

	//returns the number of the corner to place the settlement at 
	public int buildInitialSettlement() {

		return 0 + (int)(Math.random()*53); 
	}
	
	public int buildSettlement() {
		
		int	space = 0 + (int)(Math.random()*53); 
		return space;
	}
	
	//returns an edge where the road should be placed (from lower index to higher index)
	public Edge buildRoad() {
		//int start = 0 + (int)(Math.random()*53);
		//int end = 0 + (int)(Math.random()*53); 
		
		int index = 0 + (int)(Math.random()*72);
		Edge e = info.getEdges().get(index);
		int start = 0;
		int end = 0;
		for (int i = 0; i < info.getCorners().size(); i++) {
			if (e.getBeginningCorner().getPosition() == info.getCorners().get(i).getPosition())
				start = info.getCorners().get(i).getPosition();
			if (e.getEndCorner().getPosition() == info.getCorners().get(i).getPosition())
				end = info.getCorners().get(i).getPosition();
		}
		
		//must connect to a road or settlement 

		return new Edge(start, end);
		
	}
	
	
	//returns the number of the corner to build the city
	public int buildCity() {
		ArrayList<Integer> mySettlements = new ArrayList<Integer>();
		for (int i = 0; i < info.board.corners.size(); i++) {
			Corner c = info.board.corners.get(i);
			if (c.getOwner() != null){
				if (c.getOwner().equals(this)) {
					
					mySettlements.add(i);
					
				}
			}
		}
		
		Collections.shuffle(mySettlements);
		return mySettlements.get(0);
	}
	
	//trade
	public void trade() {
		
	}
	
	//plays a development card 
	public DevelopmentCard playDevelopmentCard() {
		Collections.shuffle(developmentCards);
		if (developmentCards.size() > 0) {
			return developmentCards.get(0);
		}
		
		return null;
	}
	
	//returns the resource to get a monopoly of if the development card is played
	public Resource pickMonopolyResource() {
		int resource = 0 + (int)(Math.random()*4); 
		if (resource == 0)
			return Resource.BRICK;
		else if (resource == 1)
			return Resource.GRAIN;
		else if (resource == 2)
			return Resource.LUMBER;
		else if (resource == 3)
			return Resource.ORE;
		else
			return Resource.WOOL;
	}
	
	//returns the tile to move the robber to
	public int moveRobber() {
		return (int)(Math.random()*18);
	}
	
	//gets rid of half of your resources if they are over 7
	public void halveResources() {
		setBrick((int)getBrick()/2);
		setGrain((int)getGrain()/2);
		setLumber((int)getLumber()/2);
		setOre((int)getOre()/2);
		setOre((int)getOre()/2);
	}
	
	//pick a resource to trade away
	public Resource tradeAway() {
		int resource = 0 + (int)(Math.random()*4); 
		if (resource == 0)
			return Resource.BRICK;
		else if (resource == 1)
			return Resource.GRAIN;
		else if (resource == 2)
			return Resource.LUMBER;
		else if (resource == 3)
			return Resource.ORE;
		else
			return Resource.WOOL;
	}
	
	//pick a resource to trade for 
	public Resource tradeFor() {
		int resource = 0 + (int)(Math.random()*4); 
		if (resource == 0)
			return Resource.BRICK;
		else if (resource == 1)
			return Resource.GRAIN;
		else if (resource == 2)
			return Resource.LUMBER;
		else if (resource == 3)
			return Resource.ORE;
		else
			return Resource.WOOL;
	}
}
