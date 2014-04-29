package game.players;

import game.components.DevelopmentCard;
import game.components.Edge;
import game.components.Resource;

import java.awt.Color;
import java.util.Collections;

public class HumanPlayer extends Player {

	public HumanPlayer(Color color) {
		super(color);
		type = "HumanPlayer";
	}
	
	//returns the number of the corner to place the settlement at 
	public int buildInitialSettlement() {
		return 0;
	}
	
	public int buildSettlement() {
		return 0;
	}
	
	public Edge buildRoad() {
		return new Edge(0, 0);
	}
	
	
	//returns the number of the corner to build the city
	public int buildCity() {
		return 0;
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
		return Resource.NONE;
	}
	
	//returns the tile to move the robber to
	public int moveRobber() {
		return 0;
	}
	
	//gets rid of half of your resources if they are over 7
	public void halveResources() {
		
	}
	
	//pick a resource to trade away
	public Resource tradeAway() {
		return Resource.NONE;
	}
	
	//pick a resource to trade for 
	public Resource tradeFor() {
		return Resource.NONE;
	}

}
