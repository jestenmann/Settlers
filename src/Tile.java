import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;


public class Tile extends JComponent {

	private NumberToken numberToken;
	private boolean hasRobber;
	private Resource resource;
	private int space;
	
	public Tile(Resource resource) {
		hasRobber = false;
		this.resource = resource;
		
		setLayout(null);

	}
	
	public void setNumberToken (NumberToken n) {
		numberToken = n;
		numberToken.setBounds(23, 40, 50, 50);
		add(numberToken);
		
	}
	
	public void placeRobber() {
		hasRobber = true;
	}
	
	public void removeRobber() {
		hasRobber = false;
	}
	
	public String toString() {
		if (numberToken != null)
			return " Space: " + space + ", Resource: " + resource.toString() + ", Number Token: " + numberToken.toString();
					//+ "\n" + corners.toString() + "\n" + edges.toString();
		else
			return "Space: " + space + ", Resource: " + resource.toString() + ", Robber" ;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public void setSpace(int space) {
		this.space = space;
	}
	
	public void paintComponent(Graphics g) {
		int[] xPoints = {3, 43, 83, 83, 43, 3};
		int[] yPoints = {25, 0, 25, 70, 95, 70};
		Polygon hexagon = new Polygon(xPoints, yPoints, 6);
		
		
		Graphics2D g2D = (Graphics2D) g; 
		g2D.setColor(Color.BLACK);
		g2D.setStroke(new BasicStroke(3));
		g2D.drawPolygon(hexagon);
		
		g2D.setColor(resource.color());
		g.fillPolygon(hexagon);
		
		g.setColor(Color.BLACK);
		g2D.drawString(resource.toString(), 23, 30);
		
	
		
	}


	
}
