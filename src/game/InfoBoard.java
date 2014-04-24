package game;

import game.players.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
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
	
	public InfoBoard(ArrayList<Player> players) {
		this.players = players;
	
		setLayout(new BorderLayout());
		setBackground(Color.PINK);
		add(buildButtons(), BorderLayout.CENTER);
		//resourceTable = buildTable();
		//add(resourceTable, BorderLayout.SOUTH);

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
		buttonPanel.setSize(100, 400);
		
		d1 = new JTextArea("Dice 1 value:  ");
		d2 = new JTextArea("Dice 2 value:  ");
		total = new JTextArea("Total dice value:  ");
		
		
	    JButton turn = new JButton("End turn");
        /*turn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	if (currPlayerIndex == 3) 
            		currPlayerIndex = 0;
            	else
            		currPlayerIndex += 1;
            	repaint();
                
            }
        });  */
        
	    JButton build = new JButton("Build settlement");
       /* build.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	repaint();
                
            }
        });  */
        
		JButton roll = new JButton("Roll dice");
      /*  roll.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	int dice1 = 1 + (int)(Math.random() * ((6 - 1) + 1));
            	int dice2 = 1 + (int)(Math.random() * ((6 - 1) + 1));
            	
            	d1.setText("Dice 1 value: " + Integer.toString(dice1));
            	d2.setText("Dice 2 value: "  + Integer.toString(dice2));
            	repaint();
                
            }
        });*/
        
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
	    buttonPanel.add(total);
	    buttonPanel.setPreferredSize(new Dimension(200, 400));
	    return buttonPanel;
	}
	
	
	

}
