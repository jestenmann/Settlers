package game.components;

import game.players.Player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;


public class Corner extends JComponent {
	
	//space on the board numbered 0 to 54 left to right, top to bottom
	private int position;
	
	//list of all the tiles the corner is touching 
	private ArrayList<Tile> tiles;
	
	//point on the screen for UI drawing 
	private Point point;
	
	//info about whether a city/settlement has been built on the corner and who built it
	private boolean hasSettlement;
	private boolean hasCity;
	private Player owner; 
	
	public Corner(int position) {
		this.position = position;
		tiles = new ArrayList<Tile>();
		point = new Point(0,0);
		
		hasSettlement = false;
		hasCity = false;
	}
	
	public void buildSettlement(Player p) {
		owner = p;
		hasSettlement = true;
		p.addSettlement(new Settlement(this));
	}
	
	public boolean hasCity() {
		return hasCity;
	}
	
	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public void upgradeSettlement() {
		hasSettlement = false;
		hasCity = true;
		owner.addCity(new City(this));
	}
	
	public int getPosition() {
		return position;
	}
	
	public void addTile(Tile t) {
		tiles.add(t);
	}
	
	public boolean isOccupied() {
		return hasSettlement || hasCity;
	}

	public String toString() {
		return "Position: " + position + ", Owner: " + owner;
	}
	
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public void paintComponent(Graphics g) {
		
		if (hasSettlement) {
			g.setColor(owner.getColor());
			g.fillRect(0, 0, 20, 20);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 20, 20);
		}
		else if (hasCity) {
			g.setColor(owner.getColor());
			g.fillRect(0, 0, 20, 20);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 20, 20);

			Graphics2D g2D = (Graphics2D) g; 
			if (owner.getColor().equals(Color.BLUE))
				g2D.setColor(Color.WHITE);
			else
				g2D.setColor(Color.BLACK);
			g2D.setStroke(new BasicStroke(1));
			
			int w = 0;
			int h = 0;
			
			while (w <=20 && h <=20) {
				g2D.drawLine(0, h, 20, h);
				g2D.drawLine(w, 0, w, 20);
				
				h+=4;
				w+=4;
			}
			
		}
	}
}
