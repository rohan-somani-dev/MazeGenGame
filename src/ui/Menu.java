package ui;

import config.Setup;
import config.themes.VisualType;
import utilities.Renderable;

import javax.swing.*;

/**
 * @author RohanSomani
 * @name TempUI
 * @date 2026-01-02
 */
public class Menu extends JPanel implements Renderable {

    /**
     * initialize a padding for left and right, buttons and other options can be added to this in the future.
     */
    public Menu() {
        this.setBackground(Setup.getColor(VisualType.BACKGROUND));
    }

    /**
     * function to be called to repaint, can implement other code but mostly used to call {@code this.repaint()}.
     *
     * @pre ready to be drawn.
     * @post update has taken place, everything that needed to be drawn is drawn.
     */
    @Override
    public void onUpdate() {
        //do something
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