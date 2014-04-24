package game;

import game.components.Corner;
import game.components.Edge;
import game.players.Player;

import java.util.ArrayList;

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



}
