package game;
import game.components.Corner;
import game.components.Edge;
import game.components.Edge.Orientation;
import game.components.NumberToken;
import game.components.Resource;
import game.components.Tile;
import game.players.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableColumn;



public class GameBoard extends JPanel {

	public ArrayList<Player> players;
	public ArrayList<Tile> tiles;
	public ArrayList<NumberToken> numberTokens;
	public ArrayList<Corner> corners;
	public ArrayList<Edge> edges;

	//who is the current player in the array list 
	private int currPlayerIndex;
	
	//the panel that contains the information 
	private JPanel infoPanel;
	
	//UI elements 
	private JTextArea d1;
	private JTextArea d2;
	
	//starting positions for the tiles and corners 
	int xStart = 25;
	int yStart = 50;
	
	public GameBoard(ArrayList<Player> players ) {

		this.players = players;
		currPlayerIndex = 0;
		
		createTiles();
		placeTiles();
		addNumbers();
		createCorners();
		createEdges();
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		//UI drawing stuff 
		drawTiles();
		buildInfoPanel();
		
		//mouse listener stuff 
		Mouse mouseHandler = new Mouse();
	    addMouseListener(mouseHandler);  
	    addMouseMotionListener(mouseHandler);

	}
	
	//takes in a corner to build a settlement and the player and builds the settlement 
	public void buildSettlement(int space, Player p) {
		Corner c = corners.get(space);
		c.buildSettlement(p);
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
		
		//need to figure this out 
		if (edge.getOrientation().equals(Orientation.VERTICAL)) 
			edge.setBounds(Math.min(beginningCorner.getX(), endCorner.getX())+10, Math.min(beginningCorner.getY(), endCorner.getY()) + 10, 100, 100);
		else if (edge.getOrientation().equals(Orientation.RIGHT))
			edge.setBounds(Math.min(beginningCorner.getX(), endCorner.getX())+13, Math.min(beginningCorner.getY(), endCorner.getY())+15, 100, 100);
		else
			edge.setBounds(Math.min(beginningCorner.getX(), endCorner.getX())+10, Math.min(beginningCorner.getY(), endCorner.getY())- (yStart - 67), 100, 100);
		
		//for drawing purposes
		/*for (int i = 0; i < tiles.size(); i++) {
			remove(tiles.get(i));
		}
		
		for (int i = 0; i < tiles.size(); i++) {
			add(tiles.get(i));
			
		}*/
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public void setDice(int first, int second) {
		d1.setText("Dice 1 value: " + Integer.toString(first));
		d2.setText("Dice 2 value: " + Integer.toString(second));
	}
	
	public void updateResources(ArrayList<Player> players) {
		this.players = players;
		System.out.println(players.get(0).getBrick());
		System.out.println(players.get(0).getGrain());
		System.out.println(players.get(0).getLumber());
		System.out.println(players.get(0).getOre());
		System.out.println(players.get(0).getWool());
		buildTable();
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
			else if (position == 1 || position == 5 || position == 9 || position == 13 || position == 8 || position == 4) 
				curr.addTile(tiles.get(1));
			else if (position == 2 || position == 6 || position == 10 || position == 14 || position == 9 || position == 5) 
				curr.addTile(tiles.get(2));
			else if (position == 7 || position == 12 || position == 17 || position == 22 || position == 16 || position == 11) 
				curr.addTile(tiles.get(3));
			else if (position == 8 || position == 13 || position == 18 || position == 23 || position == 17 || position == 12) 
				curr.addTile(tiles.get(4));
			else if (position == 9 || position == 14 || position == 19 || position == 24 || position == 18 || position == 13) 
				curr.addTile(tiles.get(5));
			else if (position == 10 || position == 15 || position == 20 || position == 25 || position == 19 || position == 14) 
				curr.addTile(tiles.get(6));
			else if (position == 16 || position == 22 || position == 28 || position == 33 || position == 27 || position == 21) 
				curr.addTile(tiles.get(7));
			else if (position == 17 || position == 23 || position == 29 || position == 34 || position == 28 || position == 22) 
				curr.addTile(tiles.get(8));
			else if (position == 18 || position == 24 || position == 30 || position == 35 || position == 29 || position == 23) 
				curr.addTile(tiles.get(9));
			else if (position == 19 || position == 25 || position == 31 || position == 36 || position == 30 || position == 24) 
				curr.addTile(tiles.get(10));
			else if (position == 20 || position == 26 || position == 32 || position == 37 || position == 31 || position == 25) 
				curr.addTile(tiles.get(11));
			else if (position == 28 || position == 34 || position == 39 || position == 43 || position == 38 || position == 33) 
				curr.addTile(tiles.get(12));
			else if (position == 29 || position == 35 || position == 40 || position == 44 || position == 39 || position == 34) 
				curr.addTile(tiles.get(13));
			else if (position == 30 || position == 36 || position == 41 || position == 45 || position == 40 || position == 35) 
				curr.addTile(tiles.get(14));
			else if (position == 31 || position == 37 || position == 42 || position == 46 || position == 41 || position == 36) 
				curr.addTile(tiles.get(15));
			else if (position == 39 || position == 44 || position == 48 || position == 51 || position == 47 || position == 43) 
				curr.addTile(tiles.get(16));
			else if (position == 40 || position == 45 || position == 49 || position == 52 || position == 48 || position == 44) 
				curr.addTile(tiles.get(17));
			else if (position == 41 || position == 46 || position == 50 || position == 53 || position == 49 || position == 45) 
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
	
	private JScrollPane createTable() {
		String[] columnNames = {"Player", "Brick", "Grain", "Lumber", "Ore", "Wool"};
		Object[][] data = {
			    {"Player 1 (blue)",  players.get(0).getBrick(), players.get(0).getGrain(), players.get(0).getLumber(), players.get(0).getOre(), players.get(0).getWool()},
			    {"Player 2 (orange)", players.get(1).getBrick(), players.get(1).getGrain(), players.get(1).getLumber(), players.get(1).getOre(), players.get(1).getWool()},
			    {"Player 2 (white)", players.get(2).getBrick(), players.get(2).getGrain(), players.get(2).getLumber(), players.get(2).getOre(), players.get(2).getWool()},
			    {"Player 2 (red)", players.get(3).getBrick(), players.get(3).getGrain(), players.get(3).getLumber(), players.get(3).getOre(), players.get(3).getWool()}
			};
		
		JTable table = new JTable(data, columnNames);
		
		//set the widths of each column
		TableColumn column = null;
		for (int i = 0; i < 5; i++) {
		    column = table.getColumnModel().getColumn(i);
		    if (i == 0)
		    	column.setMinWidth(100);
		    else if (i == 1)
		    	column.setMinWidth(25);
		    else if (i == 2)
		    	column.setMinWidth(25);
		    else if (i ==3)
		    	column.setMinWidth(25);
		    else
		    	column.setMinWidth(25);
		}
		

		table.setRowHeight(25);
		JScrollPane tableScrollPane = new JScrollPane(table);
		return tableScrollPane;

	}
	
	private JPanel buildTable() {
	    JScrollPane table = createTable();
	    JPanel tablePanel = new JPanel();
	    tablePanel.setBackground(Color.WHITE);
	    tablePanel.setSize(150, 100);
	    tablePanel.add(table);
	    
	    return tablePanel;
	}
	
	private JPanel buildButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setSize(100, 400);
		
		d1 = new JTextArea("Dice 1 value: ");
		d2 = new JTextArea("Dice 2 value: ");
		
		
	    JButton turn = new JButton("End turn");
        turn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	if (currPlayerIndex == 3) 
            		currPlayerIndex = 0;
            	else
            		currPlayerIndex += 1;
            	repaint();
                
            }
        });  
        
	    JButton build = new JButton("Build settlement");
        build.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	repaint();
                
            }
        });  
        
		JButton roll = new JButton("Roll dice");
        roll.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	int dice1 = 1 + (int)(Math.random() * ((6 - 1) + 1));
            	int dice2 = 1 + (int)(Math.random() * ((6 - 1) + 1));
            	
            	d1.setText("Dice 1 value: " + Integer.toString(dice1));
            	d2.setText("Dice 2 value: "  + Integer.toString(dice2));
            	repaint();
                
            }
        });
        
		JButton upgrade = new JButton("Upgrade to city");
		JButton trade = new JButton("Trade");
		
		buttonPanel.setBackground(Color.WHITE);
	    buttonPanel.add(roll);
	    buttonPanel.add(build);
	    buttonPanel.add(upgrade);
	    buttonPanel.add(trade);
	    buttonPanel.add(turn);
	    
	    buttonPanel.add(d1);
	    buttonPanel.add(d2);
	    
	    return buttonPanel;
	}
	
	private void buildInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setSize(300, 600);
		infoPanel.setLayout(new BorderLayout());
		infoPanel.add(buildButtons(), BorderLayout.CENTER);
		infoPanel.add(buildTable(), BorderLayout.SOUTH);
		
		infoPanel.setBounds(450, 0, 500, 600);
		
		add(infoPanel);
		
	}
	
	class Mouse extends MouseAdapter implements MouseMotionListener{
		
		public void mouseClicked(MouseEvent e){
			/*Point p = e.getPoint();
			System.out.println(corners.get(0).getPoint());
			System.out.println(p);
			
			for (int i = 0; i < corners.size(); i++) {
				if (Math.abs(corners.get(i).getPoint().getX() - p.getX()) < 20) {
					if (Math.abs(corners.get(i).getPoint().getY() - p.getY()) < 20) {
						
						corners.get(i).setBounds((int)p.getX()-10, (int)p.getY()-10, 100, 100);
						
						corners.get(i).buildSettlement(players.get(currPlayerIndex));

						repaint();
					}
				}
					
			}*/
			
		}
	
	}


}
