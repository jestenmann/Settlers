package game.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import game.players.Player;

public class Edge extends JComponent{
	
	//Corner beginning and end - for checking if it's a valid road placement
	private Corner beginningCorner;
	private Corner endCorner;
	
	//player who has a road on the edge
	private Player owner;
	private boolean hasRoad;
	
	//integer IDs of the corner - for drawing
	private int beginningId;
	private int endId;
	
	public enum Orientation {LEFT, RIGHT, VERTICAL};
	Orientation orientation;

	public Edge(Corner beginningCorner, Corner endCorner, Orientation orientation) {
		this.beginningCorner = beginningCorner;
		this.endCorner = endCorner; 
		this.orientation = orientation;
		
		hasRoad = false;
	}
	
	public Corner getBeginningCorner() {
		return beginningCorner;
	}

	public void setBeginningCorner(Corner beginningCorner) {
		this.beginningCorner = beginningCorner;
	}

	public Corner getEndCorner() {
		return endCorner;
	}

	public void setEndCorner(Corner endCorner) {
		this.endCorner = endCorner;
	}

	public Edge(int beginningId, int endId) {
		this.beginningId = beginningId;
		this.endId = endId;
		
		hasRoad = false;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	public int getBeginningId() {
		return beginningId;
	}

	public void setBeginningId(int beginningId) {
		this.beginningId = beginningId;
	}

	public int getEndId() {
		return endId;
	}

	public void setEndId(int endId) {
		this.endId = endId;
	}
	
	public void buildRoad(Player p) {
		hasRoad = true;
		owner = p;
	}
	
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void paintComponent(Graphics g) {
		
		if (hasRoad) {
			Graphics2D g2D = (Graphics2D) g; 
			g2D.setColor(owner.getColor());
			
			
			if (orientation.equals(Orientation.VERTICAL)) {
				g2D.setStroke(new BasicStroke(15));
				g2D.drawLine(0, 0, 0, 46);
			}
			else if (orientation.equals(Orientation.LEFT)) {
				g2D.setStroke(new BasicStroke(8));
				g2D.drawLine(0, 25, 40, 0);
			}
			else if (orientation.equals(Orientation.RIGHT)) {
				g2D.setStroke(new BasicStroke(8));
				g2D.drawLine(0, 0, 40, 25);
			}
		}
	}

}