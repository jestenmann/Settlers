package game.components;

public class Edge {
	
	private Corner beginning;
	private Corner end;
	private Road road;
	
	//integer IDs of the corner 
	private int beginningId;
	private int endId;
	
	public Edge(Corner beginning, Corner end) {
		this.beginning = beginning;
		this.end = end; 
	}
	
	public Edge(int beginningId, int endId) {
		this.beginningId = beginningId;
		this.endId = endId;
	}
	
	public void buildRoad(Road road) {
		this.road = road;
	}
	
	

}