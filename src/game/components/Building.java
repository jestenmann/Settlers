package game.components;

import java.util.ArrayList;

public class Building {
	private Corner corner;
	
	public Building(Corner corner) {
		this.corner = corner;
	}
	
	
	public Corner getCorner() {
		return corner;
	}
	
	public ArrayList<Resource> getResources() {
		ArrayList<Resource> resources = new ArrayList<Resource>();
		for(Tile t : corner.getTiles()) {
			resources.add(t.getResource());
		}
		
		return resources;
	}

}
