package ui.styled;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import config.Setup;
import ui.themes.VisualType;

/**
 * @author RohanSomani
 * @name StyledTextButton
 * @date 2026-01-14
 */
public class StyledTextButton extends JButton {

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
      public void mouseEntered(MouseEvent e) {
        setBackground(hoverBG);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        setBackground(normalBG);
      }

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
    });
  }

}
