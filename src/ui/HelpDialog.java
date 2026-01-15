package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import config.Setup;
import ui.styled.StyledTextButton;
import ui.themes.VisualType;

//TODO set it to box layout to have padding around the edges. 
public class HelpDialog extends JDialog {

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
    okButton.addActionListener(e -> {
      dispose();
    });
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
