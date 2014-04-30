package game.players;

import java.io.Serializable;

public class StateActionPair {
	public State s;
	public Action a;
	
	public StateActionPair(State s,Action a){
		this.s = s;
		this.a = a;
	}
	public String ToString(){
		return s.ToString() + ":" + a.ToString();
		
	}
}
