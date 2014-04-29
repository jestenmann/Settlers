package game.players;

import game.components.DevelopmentCard;
import game.components.Edge;
import game.components.Resource;

import java.awt.Color;
import java.util.Collections;

public class HumanPlayer extends Player {

	public HumanPlayer(Color color) {
		super(color);
		type = "HumanPlayer";
	}
	
}
