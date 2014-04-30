package game.players;

import java.util.ArrayList;

public class Policy {
	public ArrayList<LearningTuple> learningTuples;
	public float Reward;
	
	public Policy(ArrayList<LearningTuple> learningTuples, float Reward){
		this.learningTuples = learningTuples;
		this.Reward = Reward;
	}
}
