package ui;

import config.Setup;
import core.TimerController;
import ui.themes.VisualType;
import utilities.Renderable;

import javax.swing.*;

/**
 * The menu to the left of the maze, will include timer in the future.
 *
 * @author RohanSomani
 * @name LeftMenu
 * @date 2026-01-02
 */
public class LeftMenu extends JPanel implements Renderable {

//  public TimerController timer;

//  public JLabel timerLabel;

  /**
   * initialize a blank JPanel with a background.
   *
   * @param ignored the timer controller for the JLabel to be based on.
   */
  public LeftMenu(TimerController ignored) {
    setBackground(Setup.getColor(VisualType.BACKGROUND));
    JPanel container = new JPanel();
    container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    container.setOpaque(false);

/*
    timerLabel = new JLabel("ignore this please and thanks");
    timerLabel.setFont(Setup.MONOSPACE);
    timerLabel.setForeground(Setup.getColor(VisualType.WALL));

    this.timer = ignored;

    timer.addListener(code -> {
      this.updateLabel(code);
    });
    container.add(timerLabel);
*/

    add(container);
  }

  /*
  private void updateLabel(int code) {

    Color normal = Setup.getColor(VisualType.WALL);
    Color danger = Setup.getColor(VisualType.DANGER);

    timerLabel.setText(timer.toString());

    if (code == Setup.TIMER_FINISHED) {
      timerLabel.setForeground(danger);
      return;
    }


    float timeLeft = timer.getTimeLeft();

    if (timeLeft > 10f) {
      timerLabel.setForeground(normal);
      return;
    }

    timer.sineValue += timer.getDeltaTime() / 1000f;

    float t = (timer.sineValue % 1);
    if (t > 0.5f) t = 1f - t;
    t *= 2f;

    timerLabel.setForeground(ImageUtils.cubicEaseColor(normal, danger, t));
  }
   */

  /**
   * function to be called to repaint, can implement other code but mostly used to
   * call {@code this.repaint()}.
   *
   * @pre ready to be drawn.
   * @post update has taken place, everything that needed to be drawn is drawn.
   */
  @Override
  public void onUpdate() {
    setBackground(Setup.getColor(VisualType.BACKGROUND));
    repaint();
  }

  /**
   * get the underlying component of self.
   *
   * @return the component part of the implementing object.
   */
  @Override
  public JComponent getComponent() {
    return this;
  }
}
