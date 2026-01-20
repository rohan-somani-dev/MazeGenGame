package ui;

import config.Setup;
import ui.styled.StyledTextButton;
import ui.themes.VisualType;

import javax.swing.*;
import java.awt.*;

//TODO set it to box layout to have padding around the edges.

/**
 * A help dialog class to be used when the (?) icon is clicked.
 */
public class HelpDialog extends JDialog {

  /**
   * pop up a help dialog, with an okay button. using {@link Setup#HELP_MESSAGE}
   *
   * @param owner the parent window.
   * @param title the title of the help window.
   */
  public HelpDialog(Window owner, String title) {
    super(owner, title);
    setModal(true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JPanel content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    content.setBackground(Setup.getColor(VisualType.BACKGROUND));

    content.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Setup.getColor(VisualType.BACKGROUND).brighter(), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));

    setContentPane(content);

    JLabel message = new JLabel(Setup.HELP_MESSAGE);
    message.setFont(Setup.REGULAR);
    message.setBackground(Setup.getColor(VisualType.BACKGROUND));
    message.setForeground(Setup.getColor(VisualType.WALL));
    message.setOpaque(true);
    message.setAlignmentX(Component.LEFT_ALIGNMENT);
    content.add(message);

    content.add(Box.createVerticalStrut(20));

    JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    buttonHolder.setOpaque(false);

    JButton okButton = new StyledTextButton("THANKS!");
    okButton.setOpaque(true);
    okButton.addActionListener(e -> dispose());
    buttonHolder.add(okButton);

    buttonHolder.setAlignmentX(Component.LEFT_ALIGNMENT);

    content.add(buttonHolder);

    setLocationRelativeTo(null);
    setResizable(false);
    setUndecorated(true);
    pack();
    setVisible(true);
  }

}
