package game.components;

import java.awt.Color;


public enum Resource {

	/*GRAIN (Color.YELLOW),
	LUMBER (Color.GREEN),
	WOOL(Color.WHITE),
	ORE(Color.LIGHT_GRAY),
	BRICK(Color.RED),
	NONE(Color.GRAY);*/
	
	GRAIN (Color.LIGHT_GRAY),
	LUMBER (Color.LIGHT_GRAY),
	WOOL(Color.LIGHT_GRAY),
	ORE(Color.LIGHT_GRAY),
	BRICK(Color.LIGHT_GRAY),
	NONE(Color.LIGHT_GRAY);
	
	private final Color color; 
	Resource (Color color) {
		this.color = color;
	}
	
	public Color color() {
		return color;
	}
	
}
