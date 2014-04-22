package game.components;

import game.players.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;


public class Building extends JComponent {
	
	private boolean isSettlement;
	//otherwise it's a city (2 resources)
	private Player owner;
	
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Building(Player owner) {
		this.owner = owner;
		isSettlement = true;
		
	}
	
	public void upgrade() {
		isSettlement = false;
	}
	
	public String toString() {
		return owner.toString();
	}
	
	public void paintComponent(Graphics g) {

	}


}
