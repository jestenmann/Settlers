
public class Building {
	
	private boolean isSettlement;
	//otherwise it's a city (2 resources)
	private Player owner;
	
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

}
