package game.players;



import game.GameInfo;
import game.components.DevelopmentCard;
import game.components.Edge;

import java.awt.Color;
import java.util.ArrayList;


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

	public Player(Color color) {
		this.color = color;
		
		resources = new int[5];
		for (int i =0; i < resources.length; i++) {
			resources[i] = 0;
		}
		
		largestArmy = false;
		longestRoad = false;
		points = 0;
		
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

	public String toString() {
		return color.toString();
	}
	
	/**
	 * Below are all the methods used to make moves 
	 * 
	 */

	//returns the number of the corner to place the settlement at 
	public int buildInitialSettlement() {
		return -1;
	}
	
	//returns an edge where the road should be placed
	public Edge buildRoad() {
		int start = -1;
		int end = -1;
		//must connect to a road or settlement 
		return new Edge(start, end);
	}
	
	//returns the number of the corner to place the settlement at 
	public int buildSettlement() {
		return -1;
	}
	
	//returns the number of the corner to build the city
	public int buildCity() {
		return -1;
	}
	
	//gets a development card 
	public void getDevelopmentCard() {
		
	}
	
	//trade
	public void trade() {
		
	}
	
	//plays a development card 
	public void playDevelopmentCard() {
		
	}
	
	
	
}
