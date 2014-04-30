package game.players;

import game.components.Resource;

public class Action {
	public ActionType action;
	
	
	
	
	public Action(ActionType action){
		this.action = action;
	
	}
//	public Action(ActionType action,Resource TF,Resource TW){
//		this.action = action;
//		this.TradeFor = TF;
//		this.TradeWith = TW;
//	}
	public String ToString(){
		
		return this.action.toString();
	}

}
