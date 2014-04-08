import java.util.ArrayList;


public class Corner {
	
	private int position;
	private Building building;
	private ArrayList<Tile> tiles;
	
	public Corner(int position) {
		this.position = position;
		tiles = new ArrayList<Tile>();
	}
	
	public void addBuilding(Building building) {
		this.building = building;
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
}
