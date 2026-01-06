package ui;

import config.Setup;
import ui.styled.StyledIconButton;
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
    final UIController controller;
    /**
     * initialize a padding for left and right, buttons and other options can be added to this in the future.
     * @param controller the UIController that will be asked to update.
     */
    public Menu(UIController controller) {
        this.controller = controller;
        setBackground(Setup.getColor(VisualType.BACKGROUND));

        setLayout(new BorderLayout());

        setFocusable(false);

        buttonHolder = new JPanel();
        buttonHolder.setOpaque(false);
        buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.Y_AXIS));

        buttonHolder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        StyledIconButton settingsButton = new StyledIconButton("resources/icons/gears.png", VisualType.WALL);
        settingsButton.addActionListener(e -> launchSettings());

        buttonHolder.add(settingsButton, BorderLayout.NORTH);
        buttonHolder.add(Box.createVerticalStrut(8));
        buttonHolder.add(new StyledIconButton("resources/icons/info.png", VisualType.WALL), BorderLayout.SOUTH);

        add(buttonHolder, BorderLayout.NORTH);
    }

    private void launchSettings() {
        new Settings(SwingUtilities.getWindowAncestor(this), this::updateController);
    }

    private void updateController() {
        controller.update();
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
    //BEFORE YOU WRITE ANYTHING ADD A DESCRIPTION!!
}