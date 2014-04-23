package game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;


public class GameRunner extends JFrame {
	
	//static GameEngine engine;
	
	public GameRunner () {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//set minimum size
		setMinimumSize(new Dimension(1000,600));
		setResizable(false);
		
	}

	public static void main(String[]args) {
		
		GameEngine engine = new GameEngine();
		
		/*for (int i = 0; i < engine.getBoard().corners.size(); i++) {
			System.out.println(engine.getBoard().corners.get(i).toString());
		}*/
		
	
		GameRunner frame = new GameRunner();
		
		
		frame.getContentPane().add(engine.getBoard(), BorderLayout.CENTER);
		
		//display the window.
		frame.setVisible(true);
		engine.run();

	}
	
}
