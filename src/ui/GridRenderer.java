package ui;

import config.Setup;
import config.themes.VisualType;
import core.Grid;
import core.Node;
import utilities.Renderable;

import javax.swing.*;
import java.awt.*;

/**
 * GridRenderer holds a grid object and draws onto itself when updated.
 *
 * @author RohanSomani
 * @name GridRenderer
 * @date 2025-12-16
 */
public class GridRenderer extends JPanel implements Renderable {

    private final Grid grid; //final indicates it should only be read from, not written to.
    // if i write into it from here i'm doing something wrong.

    /**
     * initialize GridRenderer; set basic properties.
     *
     * @param grid the grid to be read from.
     */
    public GridRenderer(Grid grid) {
        this.grid = grid;
        setBackground(Setup.getColor(VisualType.BACKGROUND));
        setFocusable(false);
        setPreferredSize(new Dimension(Setup.WINDOW_SIZE, Setup.WINDOW_SIZE));
        setOpaque(true);

    }



    /**
     * called when the ui needs to be updated, updates all child nodes as well.
     * @pre initialized, {@link Grid#nodes} should be able to be read from.
     * @post maze screen is redrawn with updates node states and walls.
     */
    @Override
    public void onUpdate() {
        SwingUtilities.invokeLater(this::repaint);
    }

    /**
     * get the JComponent of self, which is a JPanel.
     * @return this, essentially just passing JPanel self on so the static typing works. don't you just LOOOOVE java??
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * paint every node in the grid, calculate nodeSize dynamically.
     * @pre grid initialized, every node has some base state.
     * @post JPanel is drawn onto with the nodes and walls.
     * @param g the {@link Graphics} object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int minDimension = Math.min(getWidth(), getHeight());
        int nodeSize = minDimension / grid.gridSize;

        for (Node node : grid.getNodes()) {
            NodeDrawer.draw(g, node, nodeSize, false, false);
        }
    }

}