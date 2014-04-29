package game;
import game.components.Corner;
import game.components.Edge;
import game.components.Edge.Orientation;
import game.components.NumberToken;
import game.components.Resource;
import game.components.Tile;
import game.players.Player;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTextArea;



public class GameBoard extends JPanel {

	public ArrayList<Player> players;
	public ArrayList<Tile> tiles;
	public ArrayList<NumberToken> numberTokens;
	public ArrayList<Corner> corners;
	public ArrayList<Edge> edges;

	//who is the current player in the array list 
	private Player currPlayer;
	
	//the panel that contains the information 
	private JPanel infoPanel;
	
	//UI elements 
	private JTextArea d1;
	private JTextArea d2;
	
	//starting positions for the tiles and corners 
	private int xStart = 25;
	private int yStart = 50;
	
	public GameBoard() {

		
		createTiles();
		placeTiles();
		addNumbers();
		createCorners();
		createEdges();
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		//UI drawing stuff 
		drawTiles();
		//buildInfoPanel();
		
		//mouse listener stuff 
		Mouse mouseHandler = new Mouse();
	    addMouseListener(mouseHandler);  
	    addMouseMotionListener(mouseHandler);

	}
	
	public void setCurrPlayer(Player p) {
		currPlayer = p;
	}
	
	//takes in a corner to build a settlement and the player and builds the settlement 
	public void buildSettlement(int space, Player p) {
		Corner c = corners.get(space);
		c.buildSettlement(p);
		repaint();
	}
	
	public void buildCity(int space, Player p) {
		Corner c = corners.get(space);
		c.upgradeSettlement();
		repaint();
	}
	
	public void buildRoad(Edge e, Player p) {

		Corner beginningCorner = corners.get(e.getBeginningId());
		Corner endCorner = corners.get(e.getEndId());
		Edge edge = null;
		for (int i = 0; i < edges.size(); i++) {
			edge = edges.get(i);
			if (edge.getBeginningCorner().equals(beginningCorner) && edge.getEndCorner().equals(endCorner)) {
				break;
			}
		}
		
		edge.buildRoad(p);
		
		if (edge.getOrientation().equals(Orientation.VERTICAL)) 
			edge.setBounds(Math.min(beginningCorner.getX(), endCorner.getX())+10, Math.min(beginningCorner.getY(), endCorner.getY()) + 10, 100, 100);
		else if (edge.getOrientation().equals(Orientation.RIGHT))
			edge.setBounds(Math.min(beginningCorner.getX(), endCorner.getX())+13, Math.min(beginningCorner.getY(), endCorner.getY())+15, 100, 100);
		else
			edge.setBounds(Math.min(beginningCorner.getX(), endCorner.getX())+10, Math.min(beginningCorner.getY(), endCorner.getY())- (yStart - 67), 100, 100);
		
	}
	
	public void moveRobber(int space) {
		
		for(int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).hasRobber())
				tiles.get(i).removeRobber();
		}
		
		tiles.get(space).placeRobber();
		repaint();
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	
	private void createTiles() {
		tiles = new ArrayList<Tile>();
		for (int i = 0; i < 4; i++) {
			tiles.add(new Tile(Resource.GRAIN));
		}
		for (int i = 0; i < 4; i++) {
			tiles.add(new Tile(Resource.LUMBER));
		}
		for (int i = 0; i < 4; i++) {
			tiles.add(new Tile(Resource.WOOL));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile(Resource.ORE));
		}
		for (int i = 0; i < 3; i++) {
			tiles.add(new Tile(Resource.BRICK));
		}
		Tile desert = new Tile(Resource.NONE);
		desert.placeRobber();
		tiles.add(desert);
	}
	
	private void placeTiles() {
		Collections.shuffle(tiles);
		
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).setSpace(i);	
		}
	}
	
	private void createCorners() {
		corners = new ArrayList<Corner>();
		for (int i = 0; i < 54 ; i++) {
			corners.add(new Corner(i));
		}
		int cornerX = xStart - 10;
		int cornerY = yStart - 10;
		
		//setting all the bounds of the corners 
		corners.get(0).setBounds(cornerX + 120, cornerY, 100, 100);
		corners.get(1).setBounds(cornerX + 200, cornerY, 100, 100);
		corners.get(2).setBounds(cornerX + 280, cornerY, 100, 100);
		
		corners.get(3).setBounds(cornerX + 80, cornerY + 25, 100, 100);
		corners.get(4).setBounds(cornerX + 160, cornerY + 25, 100, 100);
		corners.get(5).setBounds(cornerX + 240, cornerY + 25, 100, 100);
		corners.get(6).setBounds(cornerX + 320, cornerY + 25, 100, 100);
		
		corners.get(7).setBounds(cornerX + 80, cornerY + 70, 100, 100);
		corners.get(8).setBounds(cornerX + 160, cornerY + 70, 100, 100);
		corners.get(9).setBounds(cornerX + 240, cornerY + 70, 100, 100);
		corners.get(10).setBounds(cornerX + 320, cornerY + 70, 100, 100);
		
		corners.get(11).setBounds(cornerX + 40, cornerY + 95, 100, 100);
		corners.get(12).setBounds(cornerX + 120, cornerY + 95, 100, 100);
		corners.get(13).setBounds(cornerX + 200, cornerY + 95, 100, 100);
		corners.get(14).setBounds(cornerX + 280, cornerY + 95, 100, 100);
		corners.get(15).setBounds(cornerX + 360, cornerY + 95, 100, 100);
		
		corners.get(16).setBounds(cornerX + 40, cornerY + 140, 100, 100);
		corners.get(17).setBounds(cornerX + 120, cornerY + 140, 100, 100);
		corners.get(18).setBounds(cornerX + 200, cornerY + 140, 100, 100);
		corners.get(19).setBounds(cornerX + 280, cornerY + 140, 100, 100);
		corners.get(20).setBounds(cornerX + 360, cornerY + 140, 100, 100);
		
		corners.get(21).setBounds(cornerX, cornerY + 165, 100, 100);
		corners.get(22).setBounds(cornerX + 80, cornerY + 165, 100, 100);
		corners.get(23).setBounds(cornerX + 160, cornerY + 165, 100, 100);
		corners.get(24).setBounds(cornerX + 240, cornerY + 165, 100, 100);
		corners.get(25).setBounds(cornerX + 320, cornerY + 165, 100, 100);
		corners.get(26).setBounds(cornerX + 400, cornerY + 165, 100, 100);
		
		corners.get(27).setBounds(cornerX, cornerY + 210, 100, 100);
		corners.get(28).setBounds(cornerX + 80, cornerY + 210, 100, 100);
		corners.get(29).setBounds(cornerX + 160, cornerY + 210, 100, 100);
		corners.get(30).setBounds(cornerX + 240, cornerY + 210, 100, 100);
		corners.get(31).setBounds(cornerX + 320, cornerY + 210, 100, 100);
		corners.get(32).setBounds(cornerX + 400, cornerY + 210, 100, 100);
		
		corners.get(33).setBounds(cornerX + 40, cornerY + 235, 100, 100);
		corners.get(34).setBounds(cornerX + 120, cornerY + 235, 100, 100);
		corners.get(35).setBounds(cornerX + 200, cornerY + 235, 100, 100);
		corners.get(36).setBounds(cornerX + 280, cornerY + 235, 100, 100);
		corners.get(37).setBounds(cornerX + 360, cornerY + 235, 100, 100);
		
		corners.get(38).setBounds(cornerX + 40, cornerY + 285, 100, 100);
		corners.get(39).setBounds(cornerX + 120, cornerY + 285, 100, 100);
		corners.get(40).setBounds(cornerX + 200, cornerY + 285, 100, 100);
		corners.get(41).setBounds(cornerX + 280, cornerY + 285, 100, 100);
		corners.get(42).setBounds(cornerX + 360, cornerY + 285, 100, 100);
		
		corners.get(43).setBounds(cornerX + 80, cornerY + 310, 100, 100);
		corners.get(44).setBounds(cornerX + 160, cornerY + 310, 100, 100);
		corners.get(45).setBounds(cornerX + 240, cornerY + 310, 100, 100);
		corners.get(46).setBounds(cornerX + 320, cornerY + 310, 100, 100);
		
		corners.get(47).setBounds(cornerX + 80, cornerY + 355, 100, 100);
		corners.get(48).setBounds(cornerX + 160, cornerY + 355, 100, 100);
		corners.get(49).setBounds(cornerX + 240, cornerY + 355, 100, 100);
		corners.get(50).setBounds(cornerX + 320, cornerY +  355, 100, 100);
		
		corners.get(51).setBounds(cornerX + 120, cornerY + 380, 100, 100);
		corners.get(52).setBounds(cornerX + 200, cornerY + 380, 100, 100);
		corners.get(53).setBounds(cornerX + 280, cornerY + 380, 100, 100);
		
		for (int i = 0; i <54; i++) {
			Corner curr = corners.get(i);
			int position = curr.getPosition();
			
			if (position == 0 || position == 4 || position == 8 || position == 12 || position == 7 || position == 3) 
				curr.addTile(tiles.get(0));
			if (position == 1 || position == 5 || position == 9 || position == 13 || position == 8 || position == 4) 
				curr.addTile(tiles.get(1));
			if (position == 2 || position == 6 || position == 10 || position == 14 || position == 9 || position == 5) 
				curr.addTile(tiles.get(2));
			if (position == 7 || position == 12 || position == 17 || position == 22 || position == 16 || position == 11) 
				curr.addTile(tiles.get(3));
			if (position == 8 || position == 13 || position == 18 || position == 23 || position == 17 || position == 12) 
				curr.addTile(tiles.get(4));
			if (position == 9 || position == 14 || position == 19 || position == 24 || position == 18 || position == 13) 
				curr.addTile(tiles.get(5));
			if (position == 10 || position == 15 || position == 20 || position == 25 || position == 19 || position == 14) 
				curr.addTile(tiles.get(6));
			if (position == 16 || position == 22 || position == 28 || position == 33 || position == 27 || position == 21) 
				curr.addTile(tiles.get(7));
			if (position == 17 || position == 23 || position == 29 || position == 34 || position == 28 || position == 22) 
				curr.addTile(tiles.get(8));
			if (position == 18 || position == 24 || position == 30 || position == 35 || position == 29 || position == 23) 
				curr.addTile(tiles.get(9));
			if (position == 19 || position == 25 || position == 31 || position == 36 || position == 30 || position == 24) 
				curr.addTile(tiles.get(10));
			if (position == 20 || position == 26 || position == 32 || position == 37 || position == 31 || position == 25) 
				curr.addTile(tiles.get(11));
			if (position == 28 || position == 34 || position == 39 || position == 43 || position == 38 || position == 33) 
				curr.addTile(tiles.get(12));
			if (position == 29 || position == 35 || position == 40 || position == 44 || position == 39 || position == 34) 
				curr.addTile(tiles.get(13));
			if (position == 30 || position == 36 || position == 41 || position == 45 || position == 40 || position == 35) 
				curr.addTile(tiles.get(14));
			if (position == 31 || position == 37 || position == 42 || position == 46 || position == 41 || position == 36) 
				curr.addTile(tiles.get(15));
			if (position == 39 || position == 44 || position == 48 || position == 51 || position == 47 || position == 43) 
				curr.addTile(tiles.get(16));
			if (position == 40 || position == 45 || position == 49 || position == 52 || position == 48 || position == 44) 
				curr.addTile(tiles.get(17));
			if (position == 41 || position == 46 || position == 50 || position == 53 || position == 49 || position == 45) 
				curr.addTile(tiles.get(18));
		}
	}
	
	private void createEdges() {
		edges = new ArrayList<Edge>();
		edges.add(new Edge(corners.get(0), corners.get(3), Orientation.LEFT));
		edges.add(new Edge(corners.get(0), corners.get(4), Orientation.RIGHT));
		edges.add(new Edge(corners.get(1), corners.get(4), Orientation.LEFT));
		edges.add(new Edge(corners.get(1), corners.get(5), Orientation.RIGHT));
		edges.add(new Edge(corners.get(2), corners.get(5), Orientation.LEFT));
		edges.add(new Edge(corners.get(2), corners.get(6), Orientation.RIGHT));
		
		edges.add(new Edge(corners.get(3), corners.get(7), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(4), corners.get(8), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(5), corners.get(9), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(6), corners.get(10), Orientation.VERTICAL));
		
		edges.add(new Edge(corners.get(7), corners.get(11), Orientation.LEFT));
		edges.add(new Edge(corners.get(7), corners.get(12), Orientation.RIGHT));
		edges.add(new Edge(corners.get(8), corners.get(12), Orientation.LEFT));
		edges.add(new Edge(corners.get(8), corners.get(13), Orientation.RIGHT));
		edges.add(new Edge(corners.get(9), corners.get(13), Orientation.LEFT));
		edges.add(new Edge(corners.get(9), corners.get(14), Orientation.RIGHT));
		edges.add(new Edge(corners.get(10), corners.get(14), Orientation.LEFT));
		edges.add(new Edge(corners.get(10), corners.get(15), Orientation.RIGHT));
		
		edges.add(new Edge(corners.get(11), corners.get(16), Orientation.VERTICAL));;
		edges.add(new Edge(corners.get(12), corners.get(17), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(13), corners.get(18), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(14), corners.get(19), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(15), corners.get(20), Orientation.VERTICAL));
		
		edges.add(new Edge(corners.get(16), corners.get(21), Orientation.LEFT));
		edges.add(new Edge(corners.get(16), corners.get(22), Orientation.RIGHT));
		edges.add(new Edge(corners.get(17), corners.get(22), Orientation.LEFT));
		edges.add(new Edge(corners.get(17), corners.get(23), Orientation.RIGHT));
		edges.add(new Edge(corners.get(18), corners.get(23), Orientation.LEFT));
		edges.add(new Edge(corners.get(18), corners.get(24), Orientation.RIGHT));
		edges.add(new Edge(corners.get(19), corners.get(24), Orientation.LEFT));
		edges.add(new Edge(corners.get(19), corners.get(25), Orientation.RIGHT));
		edges.add(new Edge(corners.get(20), corners.get(25), Orientation.LEFT));
		edges.add(new Edge(corners.get(20), corners.get(26), Orientation.RIGHT));
		
		edges.add(new Edge(corners.get(21), corners.get(27), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(22), corners.get(28), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(23), corners.get(29), Orientation.VERTICAL));;
		edges.add(new Edge(corners.get(24), corners.get(30), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(25), corners.get(31), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(26), corners.get(32), Orientation.VERTICAL));
		
		edges.add(new Edge(corners.get(27), corners.get(33), Orientation.RIGHT));
		edges.add(new Edge(corners.get(28), corners.get(33), Orientation.LEFT));
		edges.add(new Edge(corners.get(28), corners.get(34), Orientation.RIGHT));
		edges.add(new Edge(corners.get(29), corners.get(34), Orientation.LEFT));
		edges.add(new Edge(corners.get(29), corners.get(35), Orientation.RIGHT));
		edges.add(new Edge(corners.get(30), corners.get(35), Orientation.LEFT));
		edges.add(new Edge(corners.get(30), corners.get(36), Orientation.RIGHT));
		edges.add(new Edge(corners.get(31), corners.get(36), Orientation.LEFT));
		edges.add(new Edge(corners.get(31), corners.get(37), Orientation.RIGHT));
		edges.add(new Edge(corners.get(32), corners.get(37), Orientation.LEFT));
		
		edges.add(new Edge(corners.get(33), corners.get(38), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(34), corners.get(39), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(35), corners.get(40), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(36), corners.get(41), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(37), corners.get(42), Orientation.VERTICAL));
		
		edges.add(new Edge(corners.get(38), corners.get(43), Orientation.RIGHT));
		edges.add(new Edge(corners.get(39), corners.get(43), Orientation.LEFT));
		edges.add(new Edge(corners.get(39), corners.get(44), Orientation.RIGHT));
		edges.add(new Edge(corners.get(40), corners.get(44), Orientation.LEFT));
		edges.add(new Edge(corners.get(40), corners.get(45), Orientation.RIGHT));
		edges.add(new Edge(corners.get(41), corners.get(45), Orientation.LEFT));
		edges.add(new Edge(corners.get(41), corners.get(46), Orientation.RIGHT));
		edges.add(new Edge(corners.get(42), corners.get(46), Orientation.LEFT));
		
		edges.add(new Edge(corners.get(43), corners.get(47), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(44), corners.get(48), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(45), corners.get(49), Orientation.VERTICAL));
		edges.add(new Edge(corners.get(46), corners.get(50), Orientation.VERTICAL));
		
		edges.add(new Edge(corners.get(47), corners.get(51), Orientation.RIGHT));
		edges.add(new Edge(corners.get(48), corners.get(51), Orientation.LEFT));
		edges.add(new Edge(corners.get(48), corners.get(52), Orientation.RIGHT));
		edges.add(new Edge(corners.get(49), corners.get(52), Orientation.LEFT));
		edges.add(new Edge(corners.get(49), corners.get(53), Orientation.RIGHT));
		edges.add(new Edge(corners.get(50), corners.get(53), Orientation.LEFT));
		
		for (int i = 0; i < edges.size(); i++) {
			add(edges.get(i));
		}
		
	}
	
	private void addNumbers() {
		numberTokens = new ArrayList<NumberToken>();
		
		numberTokens.add(new NumberToken (2, 'B'));
		numberTokens.add(new NumberToken (3, 'D'));
		numberTokens.add(new NumberToken (3, 'Q'));
		numberTokens.add(new NumberToken (4, 'J'));
		numberTokens.add(new NumberToken (4, 'N'));
		numberTokens.add(new NumberToken (5, 'A'));
		numberTokens.add(new NumberToken (5, 'O'));
		numberTokens.add(new NumberToken (6, 'C'));
		numberTokens.add(new NumberToken (6, 'P'));
		numberTokens.add(new NumberToken (8, 'E'));
		numberTokens.add(new NumberToken (8, 'K'));
		numberTokens.add(new NumberToken (9, 'G'));
		numberTokens.add(new NumberToken (9, 'M'));
		numberTokens.add(new NumberToken (10, 'F'));
		numberTokens.add(new NumberToken (10, 'L'));
		numberTokens.add(new NumberToken (11, 'I'));
		numberTokens.add(new NumberToken (11, 'R'));
		numberTokens.add(new NumberToken (12, 'H'));
		
		Collections.shuffle(numberTokens);
		
		int j = 0;
		for (int i = 0; i < tiles.size(); i++) {
			Tile curr = tiles.get(i);
			
			if (!(curr.getResource().equals(Resource.NONE))) {
				curr.setNumberToken(numberTokens.get(j));
				j++;
			}
		}
	}
	
	//UI methods 
	private void drawTiles() {
		
		int w = 200;
		int h = 200;
		
		tiles.get(0).setBounds(xStart + 80, yStart, w, h);
		tiles.get(1).setBounds(xStart + 160, yStart, w, h);
		tiles.get(2).setBounds(xStart + 240, yStart, w, h);
		
		tiles.get(3).setBounds(xStart + 40, yStart + 73, w, h);
		tiles.get(4).setBounds(xStart + 120, yStart + 73, w, h);
		tiles.get(5).setBounds(xStart + 200, yStart + 73, w, h);
		tiles.get(6).setBounds(xStart + 280, yStart + 73, w, h);
		
		tiles.get(7).setBounds(xStart, yStart + 146, w, h);
		tiles.get(8).setBounds(xStart + 80, yStart + 146, w, h);
		tiles.get(9).setBounds(xStart + 160, yStart + 146, w, h);
		tiles.get(10).setBounds(xStart + 240, yStart + 146, w, h);
		tiles.get(11).setBounds(xStart + 320, yStart + 146, w, h);
		
		tiles.get(12).setBounds(xStart + 40, yStart + 219, w, h);
		tiles.get(13).setBounds(xStart + 120, yStart + 219, w, h);
		tiles.get(14).setBounds(xStart + 200, yStart + 219, w, h);
		tiles.get(15).setBounds(xStart + 280, yStart + 219, w, h);
		
		tiles.get(16).setBounds(xStart + 80, yStart + 292, w, h);
		tiles.get(17).setBounds(xStart + 160, yStart + 292, w, h);
		tiles.get(18).setBounds(xStart + 240, yStart + 292, w, h);
		
		for (int i = 0; i < corners.size(); i++) {
			add(corners.get(i));
			
		}
		for (int i = 0; i < tiles.size(); i++) {
			add(tiles.get(i));
			
		}
		

	}
	
	class Mouse extends MouseAdapter implements MouseMotionListener{
		
		public void mouseClicked(MouseEvent e){
			Point p = e.getPoint();
			System.out.println(corners.get(0).getPoint());
			System.out.println(p);
			
			for (int i = 0; i < corners.size(); i++) {
				if (Math.abs(corners.get(i).getPoint().getX() - p.getX()) < 20) {
					if (Math.abs(corners.get(i).getPoint().getY() - p.getY()) < 20) {
						
						corners.get(i).setBounds((int)p.getX()-10, (int)p.getY()-10, 100, 100);
						
						corners.get(i).buildSettlement(currPlayer);

						repaint();
					}
				}
					
			}
			
		}
	
	}


}
