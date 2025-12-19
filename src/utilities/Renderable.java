package utilities;

import javax.swing.*;

/**
 * An interface telling the UI if a certain element can itself be drawn to the screen
 * think JPanel, JButton, etc.
 */
public interface Renderable {

    /**
     * function to be called to repaint, can implement other code but mostly used to call <code>this.repaint()</code>.
     * @pre ready to be drawn.
     * @post update has taken place, everything that needed to be drawn is drawn.
     */
    void onUpdate();

    /**
     * get the underlying component of self.
     * @return the component part of the implementing object.
     */
    JComponent getComponent();
}
