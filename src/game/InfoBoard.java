package game;

import game.components.DevelopmentCard;
import game.components.Edge;
import game.components.Resource;
import game.players.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableColumn;

public class InfoBoard extends JPanel{

	private JTextArea d1;
	private JTextArea d2;
	private JTextArea total;
	private JPanel resourceTable;
	
	private ArrayList<Player> players;
	GameEngine engine;
	
	boolean turnOver;
	
	public InfoBoard(ArrayList<Player> players, GameEngine engine) {
		this.players = players;
	
		setLayout(new BorderLayout());
		setBackground(Color.PINK);
		add(buildButtons(), BorderLayout.CENTER);
		
		//add
		
		this.engine = engine;
		
		turnOver = true;
		
		add(infoPanel(), BorderLayout.SOUTH);
		
		this.setPreferredSize(new Dimension(475, 400));

	}
	
	public void updateResources(ArrayList<Player> players) {
		//this.players = players;
		/*remove(resourceTable);
		resourceTable = buildTable();
		add(resourceTable, BorderLayout.SOUTH);
		repaint();*/

	}
	
	public void setDice(int first, int second) {
		d1.setText("Dice 1 value: " + Integer.toString(first));
		d2.setText("Dice 2 value: " + Integer.toString(second));
		total.setText("Total dice value: " + Integer.toString(first + second));
	}
	
	public void buildInitialSettlement() {
		JOptionPane.showMessageDialog(new JFrame(), "Please build a setttlement by clicking on a corner");
		engine.board.turnOver = false;
		boolean turnOver = false;
		
		while(!turnOver) {
			
			turnOver = engine.board.turnOver;
		}
		int settlement = engine.board.settlement;
		engine.board.buildSettlement(settlement, engine.currPlayer);
		engine.currPlayer.addVictoryPoint();
		engine.board.settlement = 0;
		
		add(infoPanel(), BorderLayout.SOUTH);

	}
	
	public void buildInitialRoad() {
		/*String inputValue = JOptionPane.showInputDialog("Please input a starting edge value");
		int start = Integer.parseInt(inputValue);
		String inputVal = JOptionPane.showInputDialog("Please input an ending edge value");
		int end = Integer.parseInt(inputVal);*/
		
		/*while (!engine.checkValidRoad(new Edge(start, end))) {
			start = Integer.parseInt(JOptionPane.showInputDialog("Please input a starting edge value"));
			end = Integer.parseInt(JOptionPane.showInputDialog("Please input an ending edge value"));
			
		}*/
		JOptionPane.showMessageDialog(new JFrame(), "Please select one end of your road by clicking on a corner");
		
		engine.board.turnOver = false;
		boolean turnOver = false;
		
		while(!turnOver) {
			turnOver = engine.board.turnOver;
		}
		
		int start = engine.board.start;
		
		JOptionPane.showMessageDialog(new JFrame(), "Please select the other end of your road by clicking on a corner");
		
		engine.board.turnOver = false;
		boolean turnOver2 = false;
		
		while(!turnOver2) {
			turnOver2 = engine.board.turnOver;
		}
		
		
		int end = engine.board.end;
		
		if (end < start) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		
		engine.board.buildRoad(new Edge(start, end), engine.currPlayer);
		
		engine.board.settlement = 0;
	}
	
	public void moveRobber() {
		String space = JOptionPane.showInputDialog("Please input a space to move the robber to");
		int s = Integer.parseInt(space);
		
		engine.board.moveRobber(s);
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
		tableScrollPane.setMaximumSize(new Dimension(200,150));
		return tableScrollPane;

	}
	
	private JPanel buildTable() {
	    JScrollPane table = createTable();
	    JPanel tablePanel = new JPanel();
	    tablePanel.setBackground(Color.WHITE);
	    tablePanel.add(table);
	    
	    return tablePanel;
	}
	
	private JPanel buildButtons() {
		JPanel buttonPanel = new JPanel();
		
		d1 = new JTextArea("Dice 1 value:  ");
		d2 = new JTextArea("Dice 2 value:  ");
		total = new JTextArea("Total dice value:  ");
		
		
	    JButton turn = new JButton("End turn");
        turn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	turnOver = true;
                
            }
        });  
        
	    JButton build = new JButton("Build settlement");
        build.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	
    			if (engine.checkSettlementResources()) 
            		engine.wantToBuildSettlement = true;
    
    			else {
					JOptionPane.showMessageDialog(new JFrame(), "You do not have enough resources to build a settlement");
    			}
            }
        });

        final JButton buildRoad = new JButton("Build road") ;
        buildRoad.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	if (engine.checkRoadResources()) 
        			engine.wantToBuildRoad = true;

        		
        		else {
					JOptionPane.showMessageDialog(new JFrame(), "You do not have enough resources to build a road");
    			}
            }
        });

        JButton getDevCard = new JButton("Get development card");
        getDevCard.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		
				if (engine.checkDevCardResources()) {
					engine.currPlayer.setGrain(engine.currPlayer.getGrain() - 1);
					engine.currPlayer.setOre(engine.currPlayer.getOre() - 1);
					engine.currPlayer.setWool(engine.currPlayer.getWool()- 1);
					DevelopmentCard d = engine.developmentCards.remove(0);
					engine.currPlayer.addDevelopmentCard(d);
					JOptionPane.showMessageDialog(new JFrame(), d.toString());
				}
				
    			else {
					
					JOptionPane.showMessageDialog(new JFrame(), "You do not have enough resources to get a development card");
    			}
				
				
        	}
        });
        
		JButton upgrade = new JButton("Upgrade to city");
        upgrade.addActionListener(new ActionListener() { 
 
            public void actionPerformed(ActionEvent e)
            {
    			if (engine.checkCityResources()) 

    				engine.wantToBuildCity = true;

    			else {
					JOptionPane.showMessageDialog(new JFrame(), "You do not have enough resources to build a city");
    			}
            }
        });
		
		JButton trade = new JButton("Trade");
        trade.addActionListener(new ActionListener() { 
        	 
            public void actionPerformed(ActionEvent e)
            {
    			String tradeAway = JOptionPane.showInputDialog("Please enter a resource to trade away 4 of that kind");
        		String tradeFor = JOptionPane.showInputDialog("Please enter a resource to trade for 1 of that kind");
        		
        		if (tradeAway.equalsIgnoreCase("Brick") && engine.currPlayer.getBrick() >= 4) {
    				//System.out.println("trading");
        			engine.currPlayer.setBrick(engine.currPlayer.getBrick() - 4);
    			}
    			else if (tradeAway.equalsIgnoreCase("Grain") && engine.currPlayer.getGrain() >= 4) {
    				//System.out.println("trading");
    				engine.currPlayer.setGrain(engine.currPlayer.getGrain() - 4);
    			}
    			else if (tradeAway.equalsIgnoreCase("Lumber") && engine.currPlayer.getLumber() >= 4) {
    				//System.out.println("trading");
    				engine.currPlayer.setBrick(engine.currPlayer.getLumber() - 4);
    			}
    			else if (tradeAway.equalsIgnoreCase("Ore") && engine.currPlayer.getOre() >= 4) {
    				//System.out.println("trading");
    				engine.currPlayer.setOre(engine.currPlayer.getOre() - 4);
    			}
    			else if (tradeAway.equalsIgnoreCase("Wool") && engine.currPlayer.getWool() >= 4) {
    				//System.out.println("trading");
    				engine.currPlayer.setWool(engine.currPlayer.getWool() - 4);
    			}
    			else {
    				JOptionPane.showMessageDialog(new JFrame(), "Trade unsuccessful: you do not have 4 " + tradeAway + " to trade");
    			}
    			
    			if (tradeFor.equalsIgnoreCase("Brick")) {
    				engine.currPlayer.setBrick(engine.currPlayer.getBrick() + 1);
    			}
    			else if (tradeFor.equalsIgnoreCase("Grain")) {
    				engine.currPlayer.setGrain(engine.currPlayer.getGrain() + 1);
    			}
    			else if (tradeFor.equalsIgnoreCase("Lumber")) {
    				engine.currPlayer.setBrick(engine.currPlayer.getLumber() + 1);
    			}
    			else if (tradeFor.equalsIgnoreCase("Ore")) {
    				engine.currPlayer.setOre(engine.currPlayer.getOre() + 1);
    			}
    			else if (tradeFor.equalsIgnoreCase("Wool")) {
    				engine.currPlayer.setWool(engine.currPlayer.getWool() + 1);
    			}
        		
            }
        });
        
        JButton playDevCard = new JButton("Play development card");
        playDevCard.addActionListener(new ActionListener() { 
       	 
            public void actionPerformed(ActionEvent e)
            {
            	String devCard = JOptionPane.showInputDialog("Please enter the development card you would like to play");
            	DevelopmentCard d;
            	if (devCard.equals(DevelopmentCard.KNIGHT))
            		d = DevelopmentCard.KNIGHT;
            	else if (devCard.equals(DevelopmentCard.MONOPOLY))
            		d = DevelopmentCard.MONOPOLY;
            	else if (devCard.equals(DevelopmentCard.ROAD_BUILDING))
            		d = DevelopmentCard.ROAD_BUILDING;
            	else if (devCard.equals(DevelopmentCard.VICTORY_POINT))
            		d = DevelopmentCard.VICTORY_POINT;
            	else
            		d = DevelopmentCard.YEAR_PLENTY;
            	
            	engine.currPlayer.removeDevelopmentCard(d);
        		//do different stuff based on what development card it is
        		
        		if (d.equals(DevelopmentCard.VICTORY_POINT))
        		{
        			System.out.println("victory point development card");
        			engine.currPlayer.addVictoryPoint();
        		}
        		else if (d.equals(DevelopmentCard.KNIGHT)){
        			engine.currPlayer.addKnight();
        		}
        		else if (d.equals(DevelopmentCard.ROAD_BUILDING)) {
        			//gets to build two free roads
        			buildRoad.doClick();
        			buildRoad.doClick();
  
        		}
        		else if (d.equals(DevelopmentCard.YEAR_PLENTY)) {
        			//randomly take resource cards 
        			int resource = 0 + (int)(Math.random()*4); 
        			if (resource == 0)
        				engine.currPlayer.setBrick(engine.currPlayer.getBrick() + 1);
        			else if (resource == 1)
        				engine.currPlayer.setGrain(engine.currPlayer.getGrain() + 1);
        			else if (resource == 2)
        				engine.currPlayer.setLumber(engine.currPlayer.getLumber() + 1);
        			else if (resource == 3)
        				engine.currPlayer.setOre(engine.currPlayer.getOre() + 1);
        			else if (resource == 4)
        				engine.currPlayer.setWool(engine.currPlayer.getWool() + 1);
        			
        			resource = 0 + (int)(Math.random()*4); 
        			if (resource == 0)
        				engine.currPlayer.setBrick(engine.currPlayer.getBrick() + 1);
        			else if (resource == 1)
        				engine.currPlayer.setGrain(engine.currPlayer.getGrain() + 1);
        			else if (resource == 2)
        				engine.currPlayer.setLumber(engine.currPlayer.getLumber() + 1);
        			else if (resource == 3)
        				engine.currPlayer.setOre(engine.currPlayer.getOre() + 1);
        			else if (resource == 4)
        				engine.currPlayer.setWool(engine.currPlayer.getWool() + 1);
        		}
        		else if (d.equals(DevelopmentCard.MONOPOLY)) {
        			//get to pick a card to take from every other player
        			String resource = JOptionPane.showInputDialog("Please enter a resource to monopolize");
        			Resource r;
            		if (resource.equalsIgnoreCase("Brick")) {
            			r = Resource.BRICK;
        			}
        			else if (resource.equalsIgnoreCase("Grain")) {
        				r = Resource.GRAIN;
        			}
        			else if (resource.equalsIgnoreCase("Lumber")) {
        				r = Resource.LUMBER;
        			}
        			else if (resource.equalsIgnoreCase("Ore")) {
        				r = Resource.ORE;
        			}
        			else {
        				r = Resource.WOOL;
        			}
        			int count = 0;
        			for (int i = 0; i < engine.players.size(); i++) {
        				Player p = engine.players.get(i);
        				if (!(p.equals(engine.currPlayer))) {
        					if (r.equals(Resource.BRICK)){
        						count = p.getBrick();
        						p.setBrick(0);
        						engine.currPlayer.setBrick(engine.currPlayer.getBrick() + count);
        					}
        					if (r.equals(Resource.GRAIN)){
        						count = p.getGrain();
        						p.setGrain(0);
        						engine.currPlayer.setGrain(engine.currPlayer.getGrain() + count);
        					}
        					if (r.equals(Resource.LUMBER)){
        						count = p.getLumber();
        						p.setLumber(0);
        						engine.currPlayer.setLumber(engine.currPlayer.getLumber() + count);
        					}
        					if (r.equals(Resource.ORE)){
        						count = p.getOre();
        						p.setOre(0);
        						engine.currPlayer.setBrick(engine.currPlayer.getOre() + count);
        					}
        					if (r.equals(Resource.WOOL)){
        						count = p.getWool();
        						p.setWool(0);
        						engine.currPlayer.setWool(engine.currPlayer.getWool() + count);
        					}
        				}
        			}
        		}
        		
            }
        });

		buttonPanel.setBackground(Color.WHITE);
	    buttonPanel.add(build);
	    buttonPanel.add(buildRoad);
	    buttonPanel.add(getDevCard);
	    buttonPanel.add(upgrade);
	    buttonPanel.add(playDevCard);
	    buttonPanel.add(trade);
	    buttonPanel.add(turn);
	   
	    JPanel dicePanel = new JPanel();
	    dicePanel.add(d1);
	    dicePanel.add(d2);
	    dicePanel.add(total);
	    dicePanel.setBackground(Color.WHITE);
	    buttonPanel.add(dicePanel);
	   
	    buttonPanel.setPreferredSize(new Dimension(450, 400));
	    return buttonPanel;
	}
	
	public JPanel infoPanel() {

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.WHITE);
		JTextArea lumber = new JTextArea("Lumber: " + engine.currPlayer.getLumber());
		JTextArea brick = new JTextArea("Brick: " + engine.currPlayer.getBrick());
		JTextArea grain = new JTextArea("Grain: " + engine.currPlayer.getGrain());
		JTextArea ore = new JTextArea("Ore: " + engine.currPlayer.getOre());
		JTextArea wool = new JTextArea("Wool: " + engine.currPlayer.getWool());
		
		JPanel subPanel = new JPanel();
		
		subPanel.add(lumber);
		subPanel.add(brick);
		subPanel.add(grain);
		subPanel.add(ore);
		subPanel.add(wool);
		subPanel.setBackground(Color.WHITE);
		JPanel devCards = new JPanel();
		String devString = "";
		
		for (int i = 0; i < engine.currPlayer.developmentCards.size(); i++) {
			devString = devString + ", " + engine.currPlayer.developmentCards.get(i);
		}
		JTextArea dev = new JTextArea("Development cards: " + devString);
		infoPanel.add(subPanel, BorderLayout.NORTH);
		infoPanel.add(dev, BorderLayout.SOUTH);
		
		return infoPanel;
	}
	
	
	

}
