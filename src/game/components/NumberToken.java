package game.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;


public class NumberToken extends JComponent {
	
	int number;
	char letter;
	int dots;
	Color color;
	
	public NumberToken(int number, char letter) {
		this.number = number;
		this.letter = letter;
		calculateDots();
		calculateColor();
	}
	
	private void calculateDots() {
		
		if (number == 2|| number == 12) 
			dots = 1;
		else if (number == 3 || number == 11)
			dots = 2;
		else if (number == 4 || number == 10)
			dots = 3;
		else if (number == 5 || number == 9)
			dots = 4;
		else if (number == 6 || number == 8)
			dots = 5;
	}
	
	private void calculateColor() {
		
		if (dots == 5)
			color = Color.red;
		else
			color = Color.black;
	}
	
	public String toString() {
		return letter + ", " + number + ", " + dots + " dots, " + color.toString();
	}
	
	public void paintComponent(Graphics g) {

		Graphics2D g2D = (Graphics2D) g; 
		g2D.setColor(Color.BLACK);
		g2D.setStroke(new BasicStroke(3));
		g2D.drawOval(0, 0, 40, 40);
		
		g.setColor(Color.WHITE);

		g.fillOval(0, 0, 40, 40);
		
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, 40, 40);
		
		g2D.setColor(color);
		g2D.drawString(Character.toString(letter), 15, 15);
		
		Font oldFont = g2D.getFont(); 
		Font newFont = new java.awt.Font(oldFont.getName(), java.awt.Font.BOLD, oldFont.getSize()+3);
		
		g2D.setFont(newFont);
		g2D.drawString(Integer.toString(number), 15, 27);
		
		int xPos = 7;
		for (int i = 0; i < dots; i++) {
			g.fillOval(xPos, 30, 5, 5);
			xPos += 6;
		}
		
		
	}

}
