package core;
/*
 * Author: RohanSomani
 * Name: core.Game
 * Date: 2025-12-10
 */

import config.Setup;
import entities.Player;
import ui.GridRenderer;
import ui.PlayerRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game implements UpdateListener {

    JFrame frame;
    Grid grid;

    boolean mazeFinished;
    Player player;

    GridRenderer gridRenderer;
    PlayerRenderer playerRenderer;

    public void main(String[] args) {

        grid = new Grid(Setup.GRID_SIZE);
        grid.addListener(this);
        setupRenderers();
    }

    private void setupRenderers() {
        gridRenderer = new GridRenderer(grid);
        start();
        gridRenderer.initUI();
    }

    void start() {
        setFrame();
        Thread mazeThread = new Thread(() -> {
            handleMazeGen();
            onFinished();
        });
        mazeThread.start();
    }

    private void onFinished() {
        mazeFinished = true;
        player = new Player(grid.start);
        grid.initPlayer(player);
        gridRenderer.repaint();
    }

    void handleMazeGen() {
        int result = grid.genMaze();
        if (result == Setup.INTERRUPTED_ERROR) {
            System.out.println("UNABLE TO ANIMATE. DEFAULTING TO NO ANIMATION");
            Setup.mazeSleepTime = 0;
            handleMazeGen();
        } else if (result != 0) {
            System.out.println("error in maze gen");
        }
    }

    void handleKeyPress(KeyEvent e) {
        if (!mazeFinished) return;
        int key = e.getKeyCode();

        Player.Direction move = Setup.KEY_BINDINGS.get(key);
        if (move != null) {
            if (grid.movePlayer(player, move)) {
                gridRenderer.repaint();
            }
            return;
        }

        if (key == KeyEvent.VK_SPACE) {
//            TODO: set start to player pos, make the path generate from there; allow player to regen path as much as they want
            new Thread(grid::GreedyBFS).start();
        }

    }

    void setFrame() {
//        init frame
        frame = new JFrame("MazeGen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gridRenderer.setPreferredSize(new Dimension(Setup.WINDOW_SIZE, Setup.WINDOW_SIZE));

//        TODO: CLEANUP
        gridRenderer.setFocusable(false);
        frame.add(gridRenderer);
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


    @Override
    public void onGameUpdated() {
        gridRenderer.repaint();
        gridRenderer.test();
    }
}
