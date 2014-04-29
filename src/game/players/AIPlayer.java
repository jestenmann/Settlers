package game.players;

import game.components.DevelopmentCard;
import game.components.Edge;
import game.components.Resource;

import java.awt.Color;
import java.util.Collections;

public class AIPlayer extends Player {

	int settlements;
	
	public AIPlayer(Color color) {
		super(color);
		type = "AIPlayer";
		
		settlements = 0;
		
	}
	
	//returns the number of the corner to place the settlement at 
	public int buildInitialSettlement() {
		
		
		
		settlements++;
		
		return 0;
	}
	
	public int buildSettlement() {
		return super.buildSettlement();
	}
	
	public Edge buildRoad() {
		return super.buildRoad();
	}
	
	
	//returns the number of the corner to build the city
	public int buildCity() {
		return super.buildCity();
	}
	
	//trade
	public void trade() {
		super.trade();
	}
	
	//plays a development card 
	public DevelopmentCard playDevelopmentCard() {
		return super.playDevelopmentCard();
	}
	
	//returns the resource to get a monopoly of if the development card is played
	public Resource pickMonopolyResource() {
		return super.pickMonopolyResource();
	}
	
	//returns the tile to move the robber to
	public int moveRobber() {
		return super.moveRobber();
	}
	
	//gets rid of half of your resources if they are over 7
	public void halveResources() {
		super.halveResources();
	}
	
	//pick a resource to trade away
	public Resource tradeAway() {
		return super.tradeAway();
	}
	
	//pick a resource to trade for 
	public Resource tradeFor() {
		return super.tradeFor();
	}

}
