package game.players;

import game.components.Corner;
import game.components.DevelopmentCard;
import game.components.Edge;
import game.components.Resource;
import game.components.Tile;

import java.awt.Color;
import java.util.ArrayList;

public class RandomPlayer extends Player {

	public int settlements;
	
	
	
	public RandomPlayer(Color color) {
		super(color);
		this.type = "AIPlayer";
		
		
		settlements = 0;
		
	}
	
	
	
	
	//returns the number of the corner to place the settlement at 
	public int buildInitialSettlement() {
		
		int	space = 0 + (int)(Math.random()*53); 
		return space;
	}
	
	public int buildSettlement() {
		int	space = 0 + (int)(Math.random()*53); 
		return space;
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
	
	private boolean checkDistance(Corner curr) {
		if (curr.isOccupied())
			return false;
		
		ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
		for (int i = 0; i < info.getEdges().size(); i++) {
			Edge e = info.getEdges().get(i);
			if (e.getBeginningCorner().equals(curr) || e.getEndCorner().equals(curr))
				adjacentEdges.add(e);	
		}
		
		for (int i = 0; i < adjacentEdges.size(); i++) {
			Edge currEdge = adjacentEdges.get(i);
			if (currEdge.getBeginningCorner().isOccupied() || currEdge.getEndCorner().isOccupied())
				return false;
		}
		
		return true;
	}
	
	private boolean checkValidSettlement(Corner curr) {
		if (curr.isOccupied())
			return false;
		
		ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
		for (int i = 0; i < info.getEdges().size(); i++) {
			Edge e = info.getEdges().get(i);
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
				if (currEdge.getOwner().equals(this)) {
					return true;
			}
			}
		}
		
		return false;
	}

}
