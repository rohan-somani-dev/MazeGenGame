package core;
/*
 * Author: RohanSomani
 * Name: core.Game
 * Date: 2025-12-10
 */

import config.Setup;
import entities.Player;
import ui.UIController;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController implements UpdateListener {

    private static UIController UI;
    Grid grid;

    boolean mazeFinished;
    Player player;

    public GameController() {

        grid = new Grid();
        grid.addListener(this);
        UI = new UIController(grid);

        System.out.println(System.getProperty("java.version"));

//        FIXME: not updating with path? ??
        setupInput();
    }

    private void setupInput() {
        UI.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    void start() {
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
        UI.update(Setup.MAZE_FINISHED);
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
                UI.update();
            }
            return;
        }

        if (key == KeyEvent.VK_SPACE) {
//            TODO: set start to player pos, make the path generate from there; allow player to regen path as much as they want
            new Thread(grid::GreedyBFS).start();
        }

    }

    @Override
    public void onUpdate(int code) {
        UI.update(code);
    }

}
