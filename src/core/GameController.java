package core;

import config.Setup;
import entities.Player;
import ui.UIController;
import utilities.UpdateListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A game controller that implements {@link Grid} and {@link Player} to start and run the game.
 * UI controlled by {@link UIController}
 *
 * @author RohanSomani
 * @name core.Game
 * @date 2025-12-10
 */
public class GameController implements UpdateListener {

    private final UIController UI;
    private final Grid grid;

    private boolean mazeFinished;
    private Player player;

    /**
     * initialize the controller
     *
     * @pre none. should always be called first.
     * @post grid is initialized, ui is drawn
     */
    public GameController() {

        grid = new Grid();
        grid.addListener(this);
        UI = new UIController(grid);

        setupInput();
    }

    /**
     * setup listening inputs to call a function.
     *
     * @pre ui exists, and is able to add a key adapter to it
     * @post inputs now get rerouted to the method {@link GameController#handleKeyPress(KeyEvent)}
     */
    private void setupInput() {
        UI.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    /**
     * start the maze generation in a new thread
     *
     * @pre grid exists in context, and can generate a maze
     * @post calls the maze generation algorithm and then calls {@link GameController#onFinished()}
     */
    void startMazeGen() {
        Thread mazeThread = new Thread(() -> {
            handleMazeGen();
            onFinished();
        });
        mazeThread.start();
    }

    /**
     * Called when the maze generation is finished.
     *
     * @pre maze generation finished.
     * @post initializes player, sets boolean {@link GameController#mazeFinished}, politely asks the ui to update.
     */
    private void onFinished() {
        mazeFinished = true;
        player = new Player(grid.start);
        grid.initPlayer(player);
        UI.update(Setup.MAZE_UPDATE);
    }

    /**
     * Start the maze generation, handle any errors with animation
     * if errors, defaults to no animation and prints to console.
     *
     * @pre grid, exists, and can be referenced.
     * @post maze started generating.
     */
    void handleMazeGen() {
        int result = grid.genMaze();
        if (result == Setup.INTERRUPTED_ERROR) {
            System.out.println("UNABLE TO ANIMATE. DEFAULTING TO NO ANIMATION");
            Setup.mazeSleepTime = 0;
            handleMazeGen();
        } else if (result != 0) {
            Setup.handleError(new InterruptedException("Maze Gen error"));
        }
    }

    /**
     * handles the input. checks if the input is in list of player movements ({@link Setup#KEY_BINDINGS}) and if not
     * it will check if the path should start generating.
     *
     * @param keyEvent the key event.
     * @pre grid has a player, and can be called upon.
     * @post player is moved, or path generation started, or nothing.
     */
    void handleKeyPress(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();

        if (key == KeyEvent.VK_Q) {
            UI.exit();
        }

        if (!mazeFinished) return;

        Player.Direction move = Setup.KEY_BINDINGS.getOrDefault(key, null);
        if (move != null) {
            if (grid.movePlayer(player, move)) {
                UI.update();
            }
        }

        if (key == KeyEvent.VK_SPACE) {
//            TODO: set start to player pos, make the path generate from there; allow player to regen path as much as they want
            new Thread(grid::GreedyBFS).start();
        }

    }

    /**
     * @param code the code to be updated from {@link Setup};
     * @pre UI is ready to be updated.
     * @post ui gets updated or queued to update with SwingUtilities.
     */
    @Override
    public void onUpdate(int code) {
        UI.update(code);
    }

}
