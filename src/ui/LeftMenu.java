package ui;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Setup;
import core.TimerController;
import ui.themes.VisualType;
import utilities.ImageUtils;
import utilities.Renderable;
import utilities.SoundPlayer;
import utilities.SoundType;

/**
 * @author RohanSomani
 * @name LeftMenu
 * @date 2026-01-02
 */
public class LeftMenu extends JPanel implements Renderable {

  public TimerController timer;

  public JLabel timerLabel;

  /**
   * initialize a blank JPanel with a background.
   *
   * @param timerController the timer controller for the jlabel to be based on.
   */
  public LeftMenu(TimerController timerController) {
    setBackground(Setup.getColor(VisualType.BACKGROUND));
    JPanel container = new JPanel();
    container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    container.setOpaque(false);

    timerLabel = new JLabel("NO TIMER... YET");
    timerLabel.setFont(Setup.MONOSPACE);
    timerLabel.setForeground(Setup.getColor(VisualType.WALL));

    this.timer = timerController;

    timer.addListener(code -> {
      this.updateLabel(code);
    });
    container.add(timerLabel);

    add(container);
  }

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
