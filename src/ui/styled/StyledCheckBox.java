package ui.styled;

import config.Setup;
import ui.themes.VisualType;
import utilities.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * A pretty checkbox with icons for checked and unchecked
 *
 * @author RohanSomani
 * @date Jan 16, 2026
 * @name StyledCheckBox
 */
public class StyledCheckBox extends JPanel {

  private final JCheckBox checkBox;

  /**
   * initialize a styled checkbox, with the text to the right and the box to the right.
   *
   * @param text           the text to be displayed alongside the checkbox.
   * @param actionListener the function to be run when the checkbox's state changes.
   */
  public StyledCheckBox(String text, ActionListener actionListener) {
    setLayout(new BorderLayout());

    setBackground(Setup.getColor(VisualType.BACKGROUND));

    JLabel label = new JLabel(text);
    label.setFont(Setup.REGULAR);
    label.setForeground(Setup.getColor(VisualType.WALL));

    checkBox = new JCheckBox();
    checkBox.setBackground(Setup.getColor(VisualType.BACKGROUND));
    checkBox.setForeground(Setup.getColor(VisualType.WALL));
    checkBox.addActionListener(actionListener);

    try {
      initIcons();
    } catch (IOException e) {
      Setup.handleError(e);
    }

    add(label, BorderLayout.WEST);
    add(checkBox, BorderLayout.EAST);

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        checkBox.setSelected(!checkBox.isSelected());
      }
    });

    this.setMaximumSize(new Dimension(1000, 40));

  }

  /**
   * set up the icons of the checkbox.
   *
   * @throws IOException when the file cannot be found.
   */
  public void initIcons() throws IOException {
    ImageIcon selected = ImageUtils.setupIcon("checked", 40,
            Setup.getColor(VisualType.WALL));
    checkBox.setSelectedIcon(selected);

    ImageIcon unchecked = ImageUtils.setupIcon("unchecked", 40,
            Setup.getColor(VisualType.WALL));
    checkBox.setIcon(unchecked);
  }

  /**
   * @return if the checkbox is selected.
   */
  public boolean isSelected() {
    return checkBox.isSelected();
  }

  /**
   * set the current state of the checkbox to the given state.
   *
   * @param b whether the box is checked.
   */
  public void setSelected(boolean b) {
    checkBox.setSelected(b);
  }

}
