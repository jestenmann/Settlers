package game.players;

import game.GameInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class QFunction {
	public HashMap<StateActionPair,Double> QGraph;
	public float alpha = 1;
	public float gamma = 1;
	
	public QFunction(AIPlayer player,ArrayList<Player> players){
		QGraph = new HashMap<StateActionPair,Double>();
		QGraph.put(new StateActionPair(new State(player,players),new Action(ActionType.Pass)), 0.0);
	}
	public void UpdateQ(LearningTuple LT){
		if(!QGraph.containsKey(LT.Sap1)){
			QGraph.put(LT.Sap1,0.0);
		}
		if(!QGraph.containsKey(LT.Sap2BC)){
			QGraph.put(LT.Sap2BC,0.0);
		}
		if(!QGraph.containsKey(LT.Sap2BDC)){
			QGraph.put(LT.Sap2BDC,0.0);
		}
		if(!QGraph.containsKey(LT.Sap2BR)){
			QGraph.put(LT.Sap2BR,0.0);
		}
		if(!QGraph.containsKey(LT.Sap2BS)){
			QGraph.put(LT.Sap2BS,0.0);
		}
		if(!QGraph.containsKey(LT.Sap2T)){
			QGraph.put(LT.Sap2T,0.0);
		}
		if(!QGraph.containsKey(LT.Sap2P)){
			QGraph.put(LT.Sap2P,0.0);
		}
		if(!QGraph.containsKey(LT.Sap2RDC)){
			QGraph.put(LT.Sap2RDC,0.0);
		}
		
		double QValue = QGraph.get(LT.Sap1)+alpha*(LT.Reward+gamma*getMaxQ(LT)-QGraph.get(LT.Sap1));
		QGraph.put(LT.Sap1, QValue);
	}
	private double getMaxQ(LearningTuple LT){
		
		double maxQ = QGraph.get(LT.Sap2BC);
		if(QGraph.get(LT.Sap2BDC)>maxQ){
			maxQ = QGraph.get(LT.Sap2BDC);
		}
		if(QGraph.get(LT.Sap2BR)>maxQ){
			maxQ = QGraph.get(LT.Sap2BR);
		}
		if(QGraph.get(LT.Sap2BS)>maxQ){
			maxQ = QGraph.get(LT.Sap2BS);
		}
		if(QGraph.get(LT.Sap2T)>maxQ){
			maxQ = QGraph.get(LT.Sap2T);
		}
		if(QGraph.get(LT.Sap2P)>maxQ){
			maxQ = QGraph.get(LT.Sap2P);
		}
		if(QGraph.get(LT.Sap2RDC)>maxQ){
			maxQ = QGraph.get(LT.Sap2RDC);
		}
		return maxQ;
	
	}
	

}
