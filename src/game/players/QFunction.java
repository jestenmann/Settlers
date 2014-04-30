package game.players;

import game.GameInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class QFunction {
	public HashMap<StateActionPair,Double> QGraph;
	public float alpha = 1;
	public float gamma = 1;
	
	public QFunction(AIPlayer player,ArrayList<Player> players){
		QGraph = new HashMap<StateActionPair,Double>();
		QGraph.put(new StateActionPair(new State(player,players),new Action(ActionType.Pass)), 0.0);
	}
	
	public void write_file(String pathname) {
		try {
			 
			//String content = "This is the content to write into file";
 
			File file = new File(pathname);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(ToString());
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String ToString(){
		Set<StateActionPair> keySet = QGraph.keySet();
		String returnString = "";
		for(StateActionPair sap:keySet){
			returnString+=sap.ToString()+","+QGraph.get(sap)+"\n";
		}
		return returnString;
	}
	
	public void FromString(String s){
		String[] split = s.split(",");
		QGraph.put(SapFromString(split[0]), Double.parseDouble(split[1]));
		
	}
	
	public void readFile(String pathname) throws FileNotFoundException {
	    String line = null;
	    BufferedReader reader = new BufferedReader(new FileReader(pathname));
	    try {
	        while((line = reader.readLine()) != null){
	            FromString(line);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public StateActionPair SapFromString(String s){
		String[] stateAction = s.split(":");
		return new StateActionPair(StateFromString(stateAction[0]),ActionFromString(stateAction[1]));
	}
	public State StateFromString(String s){
		String[] split = s.split(";");
		State state = new State(Boolean.parseBoolean(split[0]),Boolean.parseBoolean(split[1]),Boolean.parseBoolean(split[2]),Boolean.parseBoolean(split[3]),Boolean.parseBoolean(split[4]),Boolean.parseBoolean(split[5]),Boolean.parseBoolean(split[6]),Boolean.parseBoolean(split[7]),Boolean.parseBoolean(split[8]));
		return state;
	}
	public Action ActionFromString(String s){
		Action action = new Action(ActionType.valueOf(s));
		return action;
	}
	public Action ChooseBestAction(State s){
		Set<StateActionPair> keySet = QGraph.keySet();
		double BCVal = 0;
		double BSVal = 0;
		double BRVal = 0;
		double BDCVal = 0;
		double PVal = 0;
		double TVal = 0;
		double PDCVal = 0;
		for(StateActionPair sap: keySet){
			if(sap.equals(new StateActionPair(s,new Action(ActionType.BuildCity)))){
				BCVal = QGraph.get(sap);
			}
			if(sap.equals(new StateActionPair(s,new Action(ActionType.BuildDC)))){
				BDCVal = QGraph.get(sap);
			}
			if(sap.equals(new StateActionPair(s,new Action(ActionType.BuildRoad)))){
				BRVal = QGraph.get(sap);
			}
			if(sap.equals(new StateActionPair(s,new Action(ActionType.BuildSettlement)))){
				BSVal = QGraph.get(sap);
			}
			if(sap.equals(new StateActionPair(s,new Action(ActionType.Pass)))){
				PVal = QGraph.get(sap);
			}
			if(sap.equals(new StateActionPair(s,new Action(ActionType.Trade)))){
				TVal = QGraph.get(sap);
			}
			if(sap.equals(new StateActionPair(s,new Action(ActionType.PlayDC)))){
				PDCVal = QGraph.get(sap);
			}
		}
		double QMAX = 0;
		Action bestAction = new Action(ActionType.BuildCity);
		if(BCVal>QMAX){
			QMAX = BCVal;
			bestAction = new Action(ActionType.BuildCity);
		}
		if(BSVal>QMAX){
			QMAX = BSVal;
			bestAction = new Action(ActionType.BuildSettlement);
		}
		if(BRVal>QMAX){
			QMAX = BRVal;
			bestAction = new Action(ActionType.BuildRoad);
		}
		if(BDCVal>QMAX){
			QMAX = BDCVal;
			bestAction = new Action(ActionType.BuildDC);
		}
		if(PVal>QMAX){
			QMAX = PVal;
			bestAction = new Action(ActionType.Pass);
		}
		if(TVal>QMAX){
			QMAX = TVal;
			bestAction = new Action(ActionType.Trade);
		}
		if(PDCVal>QMAX){
			QMAX = PDCVal;
			bestAction = new Action(ActionType.PlayDC);
		}
		return bestAction;
	
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
