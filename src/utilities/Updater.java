package utilities;

import config.Setup;

import java.util.ArrayList;

/** an Updater interface to be implemented by things updating<br>
 * think grid, levels, animations, etc.
 * @author RohanSomani
 * @name Updater
 * @date 2025-12-16
 * learned about interfaces from <a href="https://www.youtube.com/@BroCodez">Bro Code</a>
 */
public interface Updater {
    ArrayList<UpdateListener> listeners = new ArrayList<>();

    /**
     * add a listener to the object.
     * @pre added listener must implement {@link UpdateListener}
     * @post listener is added to list of listeners.
     * @param listener the listener to be added, implementing UpdateListener.
     */
    default void addListener(UpdateListener listener) {
        listeners.add(listener);
    }

    /** Notify every added listener that there has been an update.
     * @pre None.
     * @post every listener's {@code .onUpdate()} has been called.
     */
    default void notifyListeners() {
        for (UpdateListener listener : listeners) {
            listener.onUpdate(Setup.MAZE_FINISHED);
        }
    }
}