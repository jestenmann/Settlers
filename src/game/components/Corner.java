package game.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;


public class Corner extends JComponent {
	
	private int position;
	private Building building;
	private ArrayList<Tile> tiles;
	private Point point;
	
	public Corner(int position) {
		this.position = position;
		tiles = new ArrayList<Tile>();
		point = new Point(0,0);
	}
	
	public void addBuilding(Building building) {
		this.building = building;
		add(building);

	}
	
	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public void upgradeBuilding() {
		building.upgrade();
	}
	
	
	public int getPosition() {
		return position;
	}
	
	public void addTile(Tile t) {
		tiles.add(t);
	}

	public String toString() {
		return "Position: " + position + ", Building: " + building;
				//+ "\n" + tiles.toString();
	}
	
	public void paintComponent(Graphics g) {
		

		if (building != null) {
			g.setColor(building.getOwner().getColor());
			g.fillRect(0, 0, 20, 20);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 20, 20);
		}
	}
}
