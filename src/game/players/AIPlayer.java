package game.players;

import game.components.Corner;
import game.components.DevelopmentCard;
import game.components.Edge;
import game.components.Resource;
import game.components.Tile;

import java.awt.Color;
import java.util.ArrayList;

public class AIPlayer extends Player {

	int settlements;
	
	public AIPlayer(Color color) {
		super(color);
		type = "AIPlayer";
		
		settlements = 0;
		
	}
	
	//returns the number of the corner to place the settlement at 
	public int buildInitialSettlement() {
		
		int cornerIndex = 0;
		Corner best = new Corner(0);
		
		ArrayList<Corner> bestCorners = new ArrayList<Corner>();
		
		for (int i = 0; i < info.getCorners().size(); i++) {
			Corner curr = info.getCorners().get(i);
			
			if (curr.getTiles().size() == 3) 
				bestCorners.add(curr);
		}
		
		
		//now we have an array list full of corners that are touching three tiles 
		
		for (int j = 0; j < bestCorners.size(); j++) {
			Corner curr = bestCorners.get(j);
			ArrayList<Tile> tiles = curr.getTiles();
			
			Tile one = tiles.get(0);
			Tile two = tiles.get(1);
			Tile three = tiles.get(2);
			
			Resource Rone = one.getResource();
			Resource Rtwo = two.getResource();
			Resource Rthree = three.getResource();
			
			if (checkDistance(curr) && (!(Rone.equals(Rtwo))) && (!(Rtwo.equals(Rthree))) && (!(Rone.equals(Rthree)))) {
				if (!(Rone.equals(Resource.NONE)) &&!(Rtwo.equals(Resource.NONE)) && !(Rthree.equals(Resource.NONE))) { 
					best = curr;
					System.out.println(best);
					break;
				}
			}
		}
		
		for (int i = 0; i < info.getCorners().size(); i++) {
			Corner curr = info.getCorners().get(i);
			if (curr.equals(best)) {
				cornerIndex = i;
				break;
			}
		}
		
		return cornerIndex;
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
	
	private boolean checkDistance(Corner curr) {
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

}
