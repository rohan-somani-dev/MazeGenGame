
/*
 * Author: RohanSomani
 * Name: Game
 * Date: 2025-12-10
 */

import javax.swing.*;
import java.awt.*;

public class Game {

    static JFrame frame;
    static Grid grid;

    public static void main(String[] args) {

        grid = new Grid(Setup.GRID_SIZE);
        start();
    }

    static void start(){
        setFrame();      	
//        grid.testingInterruptedException = true; 
    	new Thread(Game::handleMazeGen).start();
    }

    static void handleMazeGen(){
    	int result = grid.genMaze();
    	if (result == Setup.INTERRUPTED_ERROR){
    		System.out.println("UNABLE TO ANIMATE. DEFAULTING TO NO ANIMATION");
    		Setup.sleepTime = 0;
    		handleMazeGen();
    	} else if (result != 0){
    		System.out.println("error in maze gen");
    	} 
    }
    
    static void setFrame() {
//        init frame
        frame = new JFrame("MazeGen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid.setPreferredSize(new Dimension(Setup.WINDOW_SIZE, Setup.WINDOW_SIZE));
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}