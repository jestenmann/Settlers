package game;

import game.components.Corner;
import game.components.Edge;
import game.components.Tile;
import game.players.Player;

import java.util.ArrayList;
import java.util.Collections;

//this class gets all the relevant info from the game board for the player 
public class GameInfo {
	
	public GameBoard board;
	
	public GameInfo (GameBoard board) {
		
		this.board = board;
		
	}
	
	public ArrayList<Edge> getEdges() {
		return board.edges;
	}
	
	public ArrayList<Corner> getCorners() {
		return board.corners;
	}
	
	public ArrayList<Tile> getTilesAtCorner(int corner) {
		Corner c = board.corners.get(corner);
		
		return c.getTiles();
	}
	
	public ArrayList<Corner> getPlayerCorners(Player player){
		ArrayList<Corner> playerCorners = new ArrayList<Corner>();
		for (int i = 0; i < board.corners.size(); i++) {
			Corner c = board.corners.get(i);
			if (c.getOwner() != null && c.getOwner().equals(player)) {		
					playerCorners.add(c);
			}
		}
		
		return playerCorners;
	}



}
