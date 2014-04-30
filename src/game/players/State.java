package game.players;

import game.GameBoard;
import game.GameInfo;
import game.components.Building;
import game.components.City;
import game.components.Corner;
import game.components.DevelopmentCard;
import game.components.Resource;
import game.components.Settlement;

import java.util.ArrayList;

public class State {
	public AIPlayer player;
	public ArrayList<Integer> VP;
	public int Reward = 0;
	public boolean canGrain = false,canLumber = false,canWool = false,canOre = false,canBrick = false;
	public boolean canSettlement = false,canRoad = false,canCity = false,canDC = false;
	
	
	public State(AIPlayer player,ArrayList<Player> players){
		
		this.player = player;
		
		
		ArrayList<Building> buildings = player.getBuildings();
		
		VP = new ArrayList<Integer>();
		for(int i=0;i<players.size();i++){
			if(!players.get(i).equals(player)){
				VP.add(players.get(i).points);
			}
		}
		for(int i=0;i<VP.size();i++){
			Reward+=(player.points - VP.get(i));
			
		}
		
		if(player.getBrick()>0 && player.getLumber()>0){
			canRoad = true;
		}
		if(player.getBrick()>0 && player.getLumber()>0 && player.getGrain()>0 && player.getWool()>0){
			canRoad = true;
		}
		if(player.getOre()>2 && player.getGrain()>1){
			canCity = true;
		}
		if(player.getOre()>0 && player.getGrain()>0 && player.getWool()>0){
			canDC = true;
		}
		for(int i=0;i<buildings.size();i++){
			if(buildings.get(i).getResources().contains(Resource.BRICK)){
				canBrick = true;
			}
			if(buildings.get(i).getResources().contains(Resource.GRAIN)){
				canGrain = true;
			}
			if(buildings.get(i).getResources().contains(Resource.LUMBER)){
				canLumber = true;
			}
			if(buildings.get(i).getResources().contains(Resource.ORE)){
				canOre = true;
			}
			if(buildings.get(i).getResources().contains(Resource.WOOL)){
				canWool = true;
			}
		}
	}
	public String ToString(){
		return ""+canGrain + canLumber + canWool + canOre + canBrick + canSettlement+canRoad +canCity +canDC;
	}
	

}
