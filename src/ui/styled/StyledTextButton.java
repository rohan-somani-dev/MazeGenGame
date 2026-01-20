package ui.styled;

import config.Setup;
import ui.themes.VisualType;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A button with text on it that is very visually pleasing!!
 *
 * @author RohanSomani
 * @name StyledTextButton
 * @date 2026-01-14
 */
public class StyledTextButton extends JButton {

  /**
   * initialize a button, with the given text.
   *
   * @param text the text displayed on the button.
   */
  public StyledTextButton(String text) {
    super(text);

    setBorderPainted(true);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setOpaque(true);

    setBackground(Setup.getColor(VisualType.BACKGROUND));
    setForeground(Setup.getColor(VisualType.WALL));

    setFont(Setup.REGULAR);

    Color normalBG = Setup.getColor(VisualType.BACKGROUND);
    Color hoverBG = normalBG.brighter(); // TODO add theme colors?
    Color pressedBG = normalBG.darker();

    CompoundBorder unpressedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED, hoverBG, pressedBG),
            BorderFactory.createEmptyBorder(10, 15, 10, 15));
    CompoundBorder pressedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.LOWERED, normalBG, pressedBG.darker()),
            BorderFactory.createEmptyBorder(10, 15, 10, 15));

    setBorder(unpressedBorder);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        setBackground(pressedBG);
        setBorder(pressedBorder);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        setBackground(normalBG);
        setBorder(unpressedBorder);
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        setBackground(hoverBG);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        setBackground(normalBG);
      }
    });
  }

}
