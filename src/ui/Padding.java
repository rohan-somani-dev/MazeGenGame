package ui;

import config.Setup;
import ui.themes.VisualType;
import utilities.Renderable;

import javax.swing.*;

/**
 * @author RohanSomani
 * @name Padding
 * @date 2026-01-02
 */
public class Padding extends JPanel implements Renderable {

    /**
     * initialize a blank JPanel with a background.
     */
    public Padding() {
        setBackground(Setup.getColor(VisualType.BACKGROUND));
    }

    /**
     * function to be called to repaint, can implement other code but mostly used to call {@code this.repaint()}.
     *
     * @pre ready to be drawn.
     * @post update has taken place, everything that needed to be drawn is drawn.
     */
    @Override
    public void onUpdate() {
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