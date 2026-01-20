package core;

import config.Setup;
import utilities.UpdateListener;
import utilities.Updater;

import javax.swing.*;
import java.util.ArrayList;

/*
************************************************************************************************************************
                                     PLEASE IGNORE THIS FILE FOR NOW :)
************************************************************************************************************************
 */

/**
 * A simple timer controller
 *
 * @author RohanSomani
 * @name TimerController
 * @date 2026-01-18
 */
@SuppressWarnings("MissingJavadoc")
public class TimerController implements Updater {

  private final static int TIMER_INTERVAL = 16; // millis
  private final ArrayList<UpdateListener> listeners = new ArrayList<>();
  public float sineValue = 0f;
  private long timeLeft = 1000; // in millis
  private Timer timer;
  private long prevTime;
  private long deltaTime;
  private boolean finished = true;

  public void startTimer(int timeToFire) {
    stopTimer();
    timeLeft = 1000L * timeToFire;
    timer = new Timer(TIMER_INTERVAL, e -> {
      long now = System.nanoTime();
      deltaTime = (now - prevTime) / 1_000_000L;
      prevTime = now;

      long next = timeLeft - deltaTime;
      if (next <= 0) {
        timeLeft = 0;
        finished = true;
        timer.stop();
        notifyListeners();
        return;
      }
      timeLeft = next;
      finished = false;
      notifyListeners();
    });
    finished = false;
    prevTime = System.nanoTime();
    timer.start();
  }

  public float getTimeLeft() {
    return this.timeLeft / 1000f;
  }

  public float getDeltaTime() {
    return this.deltaTime;
  }

  public void stopTimer() {
    if (timer == null)
      return;
    timer.stop();
    timeLeft = 0;
    finished = true;
    notifyListeners();
  }

  @SuppressWarnings("unused")
  public void restartTimer(int newTime) {
    startTimer(newTime);
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
    int code = timeLeft > 0 ? Setup.TIMER_TICK : Setup.TIMER_FINISHED;
    for (UpdateListener listener : listeners)
      listener.onUpdate(code);
  }

  @Override
  public String toString() {
    float seconds = this.timeLeft / 1000f;
    return String.format("%03.2f", seconds);
  }
}
