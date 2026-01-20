package utilities;

/**
 * an Updater interface to be implemented by things updating<br>
 * think grid, levels, animations, etc.
 *
 * @author RohanSomani
 * @name Updater
 * @date 2025-12-16
 * learned about interfaces from <a href="https://www.youtube.com/@BroCodez">Bro Code</a>
 */
public interface Updater {

  /**
   * add a listener to the object.
   *
   * @param listener the listener to be added, implementing UpdateListener.
   * @pre added listener must implement {@link UpdateListener}
   * @post listener is added to list of listeners.
   */
  void addListener(UpdateListener listener);

  /**
   * Notify every added listener that there has been an update.
   *
   * @pre None.
   * @post every listener's {@code .onUpdate()} has been called.
   */
  void notifyListeners();

}