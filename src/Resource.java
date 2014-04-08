import java.awt.Color;


public enum Resource {

	GRAIN (Color.YELLOW),
	LUMBER (Color.GREEN),
	WOOL(Color.WHITE),
	ORE(Color.LIGHT_GRAY),
	BRICK(Color.RED),
	NONE(Color.GRAY);
	
	private final Color color; 
	Resource (Color color) {
		this.color = color;
	}
	
	public Color color() {
		return color;
	}
	
}
