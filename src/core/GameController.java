package core;

import config.Setup;
import entities.Player;
import ui.UIController;
import utilities.ImageUtils;
import utilities.UpdateListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 * A game controller that implements {@link Grid} and {@link Player} to start
 * and run the game.
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
    grid.startMazeGen();
    onFinished();
    
  }

  /**
   * setup listening inputs to call a function.
   *
   * @pre ui exists, and is able to add a key adapter to it
   * @post inputs now get rerouted to the method
   *       {@link GameController#handleKeyPress(KeyEvent)}
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
   * Called when the maze generation is finished.
   *
   * @pre maze generation finished.
   * @post initializes player, sets boolean {@link GameController#mazeFinished},
   *       politely asks the ui to update.
   */
  private void onFinished() {
    mazeFinished = true;
    player = new Player(grid.start);
    grid.initPlayer(player);
    UI.update(Setup.MAZE_UPDATE);
  }

  /**
   * handles the input. checks if the input is in list of player movements
   * ({@link Setup#KEY_BINDINGS}) and if not
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
    } else if (key == KeyEvent.VK_ESCAPE | key == KeyEvent.VK_F) {
      UI.toggleFullscreen();
    } else if (KeyEvent.VK_0 <= key && key <= KeyEvent.VK_9) {

      Setup.setTheme((key - '0') - 1); // this works because the KeyEvent.NUM is equivelant to the ascii values.
      UI.update();
    } else if (key == KeyEvent.VK_HOME) {

      System.out.println("SAVE A THEME?");
      new Thread(() -> {
      
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the theme file to be written to: ");
        ImageUtils.writeTheme(input, input.nextLine().trim());
        input.close();
      
      }).start();
    }

    if (!mazeFinished)
      return;

    Player.Direction move = Setup.KEY_BINDINGS.getOrDefault(key, null);
    if (move != null) {
      if (grid.movePlayer(move)) {
        UI.update(Setup.MAZE_UPDATE);
      }
    }

    if (key == KeyEvent.VK_SPACE) {
      grid.pathStart = player.position;
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
