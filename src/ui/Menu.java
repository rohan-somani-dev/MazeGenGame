package ui;

import config.Setup;
import ui.themes.VisualType;
import utilities.Renderable;

import javax.swing.*;
import java.awt.*;

/**
 * @author RohanSomani
 * @name TempUI
 * @date 2026-01-02
 */
public class Menu extends JPanel implements Renderable {

    final JPanel buttonHolder;

    /**
     * initialize a padding for left and right, buttons and other options can be added to this in the future.
     */
    public Menu() {
        setBackground(Setup.getColor(VisualType.BACKGROUND));

        setLayout(new BorderLayout());

        setFocusable(false);

        buttonHolder = new JPanel();
        buttonHolder.setOpaque(false);
        buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.Y_AXIS));

        buttonHolder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Button settingsButton = new Button("resources/icons/gears.png", Setup.getColor(VisualType.WALL));
        settingsButton.addActionListener(e -> launchSettings());

        buttonHolder.add(settingsButton, BorderLayout.NORTH);
        buttonHolder.add(Box.createVerticalStrut(8));
        buttonHolder.add(new Button("resources/icons/info.png", Setup.getColor(VisualType.WALL)), BorderLayout.SOUTH);

        add(buttonHolder, BorderLayout.NORTH);
    }

    private void launchSettings() {
        System.out.println("LAUNCHING SETTINGS");
        new Settings(SwingUtilities.getWindowAncestor(this));
    }

    /**
     * function to be called to repaint, can implement other code but mostly used to call {@code this.repaint()}.
     *
     * @pre ready to be drawn.
     * @post update has taken place, everything that needed to be drawn is drawn.
     */
    @Override
    public void onUpdate() {
        Component[] components = buttonHolder.getComponents();
        for (Component component : components) {
            if (!(component instanceof Renderable)) {
                continue;
            }
            Renderable r = (Renderable) component;
            r.onUpdate();
        }
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
    //BEFORE YOU WRITE ANYTHING ADD A DESCRIPTION!!
}