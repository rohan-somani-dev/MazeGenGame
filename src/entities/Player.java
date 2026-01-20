package entities;

import config.Setup;
import core.Node;
import utilities.UpdateListener;
import utilities.Updater;

import java.util.ArrayList;

/**
 * Basic player implementation.
 */
public class Player implements Updater {

  private final ArrayList<UpdateListener> listeners = new ArrayList<>();
  public Node position;

  /**
   * initialize the player
   *
   * @param start the start node
   * @pre start is not null
   * @post an initialized player with a node declared as its start
   */
  public Player(Node start) {
    this.position = start;
  }

  /**
   * add a listener to the object.
   *
   * @param listener the listener to be added, implementing UpdateListener.
   * @pre added listener must implement {@link UpdateListener}
   * @post listener is added to list of listeners.
   */
  @Override
  public void addListener(UpdateListener listener) {
    listeners.add(listener);
  }

  /**
   * Notify every added listener that there has been an update.
   *
   * @pre None.
   * @post every listener's {@code .onUpdate()} has been called.
   */
  @Override
  public void notifyListeners() {
    for (UpdateListener listener : listeners) {
      listener.onUpdate(Setup.ALL);
    }
  }

  /**
   * the possible directions a player could move in, no diagonal.
   */
  public enum Direction {
    UP, DOWN, LEFT, RIGHT
  }

}