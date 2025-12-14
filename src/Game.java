
/*
 * Author: RohanSomani
 * Name: Game
 * Date: 2025-12-10
 */

import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game {

    static JFrame frame;
    static Grid grid;

    static boolean mazeFinished;
    static Player player;



    public static void main(String[] args) {

        grid = new Grid(Setup.GRID_SIZE);
        start();
    }

    static void start() {
        setFrame();
//        grid.testingInterruptedException = true;
        Thread mazeThread = new Thread(() -> {
            Game.handleMazeGen();
            Game.onFinished();
        });
        mazeThread.start();
    }

    private static void onFinished() {
        mazeFinished = true;
        player = new Player(grid.start);
    }

    static void handleMazeGen() {
        int result = grid.genMaze();
        if (result == Setup.INTERRUPTED_ERROR) {
            System.out.println("UNABLE TO ANIMATE. DEFAULTING TO NO ANIMATION");
            Setup.mazeSleepTime = 0;
            handleMazeGen();
        } else if (result != 0) {
            System.out.println("error in maze gen");
        }
    }



    static void handleKeyPress(KeyEvent e) {
        if (!mazeFinished) return;
        int key = e.getKeyCode();

        Player.Direction move = Setup.KEY_BINDINGS.get(key);
        if (move != null){
            if (grid.movePlayer(player, move)){
                grid.repaint();
            }
            return;
        }

        if (key == KeyEvent.VK_SPACE){
            new Thread(grid::GreedyBFS).start();
        }

    }

    static void setFrame() {
//        init frame
        frame = new JFrame("MazeGen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid.setPreferredSize(new Dimension(Setup.WINDOW_SIZE, Setup.WINDOW_SIZE));

//        TODO: CLEANUP
        grid.setFocusable(false);
        frame.add(grid);
        frame.setFocusable(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}