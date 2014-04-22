import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JOptionPane;
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
	
	private Tile grainOne;
	private Tile grainTwo;
	private Tile grainThree;
	private Tile grainFour;
	
	private Tile lumberOne;
	private Tile lumberTwo;
	private Tile lumberThree;
	private Tile lumberFour;
	
	private Tile woolOne;
	private Tile woolTwo;
	private Tile woolThree;
	private Tile woolFour;
	
	private Tile oreOne;
	private Tile oreTwo;
	private Tile oreThree;
	
	private Tile brickOne;
	private Tile brickTwo;
	private Tile brickThree;
	
	private Tile desert;
	
	private int currPlayerIndex;
	
	//buttons
	private JButton turn;
	private JButton trade;
	private JButton build;
	private JButton roll;
	private JButton upgrade;
	
	private JPanel buttonPanel;
	private JPanel tablePanel;
	
	private JTextArea d1;
	private JTextArea d2;
	
	private JScrollPane table;
	
	
	public GameBoard(ArrayList<Player> players ) {
	
		
		this.players = players;
		
		currPlayerIndex = 0;
		setLayout(null);
		setBackground(Color.WHITE);
		
		createTiles();
		placeTiles();
		addNumbers();
		createCorners();
		
		//play with the bounds of each tile
		
		int w = 200;
		int h = 200;
		tiles.get(0).setBounds(83, 7, w, h);
		tiles.get(1).setBounds(163, 7, w, h);
		tiles.get(2).setBounds(243, 7, w, h);
		
		tiles.get(3).setBounds(43, 80, w, h);
		tiles.get(4).setBounds(123, 80, w, h);
		tiles.get(5).setBounds(203, 80, w, h);
		tiles.get(6).setBounds(283, 80, w, h);
		
		tiles.get(7).setBounds(3, 153, w, h);
		tiles.get(8).setBounds(83, 153, w, h);
		tiles.get(9).setBounds(163, 153, w, h);
		tiles.get(10).setBounds(243, 153, w, h);
		tiles.get(11).setBounds(323, 153, w, h);
		
		tiles.get(12).setBounds(43, 226, w, h);
		tiles.get(13).setBounds(123, 226, w, h);
		tiles.get(14).setBounds(203, 226, w, h);
		tiles.get(15).setBounds(283, 226, w, h);
		
		tiles.get(16).setBounds(83, 299, w, h);
		tiles.get(17).setBounds(163, 299, w, h);
		tiles.get(18).setBounds(243, 299, w, h);
		
		for (int i = 0; i < corners.size(); i++) {
			add(corners.get(i));
			
		}
		for (int i = 0; i < tiles.size(); i++) {
			add(tiles.get(i));
			
		}
		
		
		//tiles have been created and randomized

		d1 = new JTextArea("Dice 1 value: ");
		d2 = new JTextArea("Dice 2 value: ");
		
		//mouse stuff 
		
		Mouse mouseHandler = new Mouse();
	    addMouseListener(mouseHandler);  
	    addMouseMotionListener(mouseHandler);
	    
	    buttonPanel = new JPanel();
	    buttonPanel.setBounds(700, 7, 150, 400);
	    
	    
	    turn = new JButton("End turn");
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
	    build = new JButton("Build settlement");
        build.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	//JOptionPane.showMessageDialog(null, "Please select a corner to build a settlement");
            	repaint();
                
            }
        });  
		roll = new JButton("Roll dice");
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
		upgrade = new JButton("Upgrade to city");
		buttonPanel.setBackground(Color.WHITE);
	    buttonPanel.add(roll);
	    buttonPanel.add(build);
	    buttonPanel.add(upgrade);
	    buttonPanel.add(turn);
	    buttonPanel.add(d1);
	    buttonPanel.add(d2);
	    
	    table = createTable();
	    tablePanel = new JPanel();
	    tablePanel.setBackground(Color.WHITE);
	    tablePanel.setBounds(450, 300, 475, 126);
	    tablePanel.add(table);
	    
	    add(tablePanel);
	   add(buttonPanel);
	   

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
	
	public void placeSettlement(int corner, Player player) {
		//Building b = new Building(player);
		//corners.get(corner).addBuilding(b);
	}
	
	public void placeRoad(Edge edge, Player player) {
		//Road r = new Road(player);
		//edge.buildRoad(r);
		
	}
	
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	private void createTiles() {

		tiles = new ArrayList<Tile>();
		
		grainOne = new Tile(Resource.GRAIN);
		grainTwo = new Tile(Resource.GRAIN);
		grainThree = new Tile(Resource.GRAIN);
		grainFour = new Tile(Resource.GRAIN);
		
		lumberOne = new Tile(Resource.LUMBER);
		lumberTwo = new Tile(Resource.LUMBER);
		lumberThree = new Tile(Resource.LUMBER);
		lumberFour = new Tile(Resource.LUMBER);
		
		woolOne = new Tile(Resource.WOOL);
		woolTwo = new Tile(Resource.WOOL);
		woolThree = new Tile(Resource.WOOL);
		woolFour = new Tile(Resource.WOOL);
		
		oreOne = new Tile(Resource.ORE);
		oreTwo = new Tile(Resource.ORE);
		oreThree = new Tile(Resource.ORE);
		
		brickOne = new Tile(Resource.BRICK);
		brickTwo = new Tile(Resource.BRICK);
		brickThree = new Tile(Resource.BRICK);
		
		desert = new Tile(Resource.NONE);
		desert.placeRobber();
		
		tiles.add(grainOne);
		tiles.add(grainTwo);
		tiles.add(grainThree);
		tiles.add(grainFour);
		
		tiles.add(lumberOne);
		tiles.add(lumberTwo);
		tiles.add(lumberThree);
		tiles.add(lumberFour);
		
		tiles.add(woolOne);
		tiles.add(woolTwo);
		tiles.add(woolThree);
		tiles.add(woolFour);
		
		tiles.add(oreOne);
		tiles.add(oreTwo);
		tiles.add(oreThree);
		
		tiles.add(brickOne);
		tiles.add(brickTwo);
		tiles.add(brickThree);
		
		tiles.add(desert);
		
		
	}
	
	private void placeTiles() {
		Collections.shuffle(tiles);
		
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).setSpace(i);
			
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
	
	private void createCorners() {
		
		corners = new ArrayList<Corner>();
		for (int i = 0; i < 54 ; i++) {
			corners.add(new Corner(i));
		}
		
		corners.get(0).setPoint(new Point(125, 7));
		corners.get(12).setPoint(new Point(126, 102));
		corners.get(13).setPoint(new Point(206, 103));
		corners.get(28).setPoint(new Point(85, 226));
		corners.get(29).setPoint(new Point(166, 226));
		corners.get(47).setPoint(new Point(85, 368));
		
		
		
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
	
	class Mouse extends MouseAdapter implements MouseMotionListener{
		
		public void mouseClicked(MouseEvent e){
			Point p = e.getPoint();
			System.out.println(corners.get(0).getPoint());
			System.out.println(p);
			
			for (int i = 0; i < corners.size(); i++) {
				if (Math.abs(corners.get(i).getPoint().getX() - p.getX()) < 20) {
					if (Math.abs(corners.get(i).getPoint().getY() - p.getY()) < 20) {
						
						corners.get(i).setBounds((int)p.getX()-10, (int)p.getY()-10, 100, 100);
						
						corners.get(i).addBuilding(new Building(players.get(currPlayerIndex)));

						repaint();
					}
				}
					
			}
			
		}
	
	}


}
