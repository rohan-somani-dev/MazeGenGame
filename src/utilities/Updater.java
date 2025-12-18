package utilities;
/*
 * Author: RohanSomani
 * Name: Updater
 * Date: 2025-12-16
 * learned about interfaces from this dude:
 * https://www.youtube.com/@BroCodez
 */

import config.Setup;
import core.UpdateListener;

import java.util.ArrayList;

public interface Updater {
    ArrayList<UpdateListener> listeners = new ArrayList<>();

    default void addListener(UpdateListener listener) {
        listeners.add(listener);
    }

    default void notifyListeners() {
        for (UpdateListener listener : listeners) {
            listener.onUpdate(Setup.MAZE_FINISHED);
        }
    }
}