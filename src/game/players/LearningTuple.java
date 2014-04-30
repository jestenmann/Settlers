package game.players;

public class LearningTuple {
	public State St,St1;
	public StateActionPair Sap1,Sap2BR,Sap2BC,Sap2BS,Sap2BDC,Sap2RDC,Sap2P,Sap2T;
	public Action a;
	public int Reward;
	
	public LearningTuple(State St,State St1,Action a,int Reward){
		this.St = St;
		this.St1 = St1;
		this.Sap1 = new StateActionPair(St,a);
		this.Sap2BR = new StateActionPair(St1,new Action(ActionType.BuildRoad));
		this.Sap2BC = new StateActionPair(St1,new Action(ActionType.BuildCity));
		this.Sap2BS = new StateActionPair(St1,new Action(ActionType.BuildSettlement));
		this.Sap2BDC = new StateActionPair(St1,new Action(ActionType.BuildDC));
		this.Sap2RDC = new StateActionPair(St1,new Action(ActionType.PlayDC));
		this.Sap2T = new StateActionPair(St1,new Action(ActionType.Trade));
		this.Sap2P = new StateActionPair(St1,new Action(ActionType.Pass));
		this.a = a;
		this.Reward = Reward;
	}
	
	public String ToString(){
		
		return "St:"+this.St.toString()+" St1:"+this.St1.toString()+" A:"+this.a.toString()+" R:"+this.Reward+"\n";
	}
	
}
