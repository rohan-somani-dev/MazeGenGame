package ui;
/*
 * Author: RohanSomani
 * Name: GridRenderer
 * Date: 2025-12-16
 */

import config.Setup;
import core.Grid;
import core.Node;
import utilities.Renderable;

import javax.swing.*;
import java.awt.*;

public class GridRenderer extends JPanel implements Renderable {

    private Grid grid;
    private int nodeSize;

    public GridRenderer(Grid grid) {
        this.grid = grid;
        setBackground(Setup.BACKGROUND_COLOR);
        setFocusable(false);
        setPreferredSize(new Dimension(Setup.WINDOW_SIZE, Setup.WINDOW_SIZE));
        setOpaque(true);

    }
    @Override //overriding from renderable
    public void initUI(){
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void onUpdate() {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int minDimension = Math.min(getWidth(), getHeight());
        nodeSize = minDimension / grid.gridSize;

        for (Node node : grid.getNodes()){
            NodeDrawer.draw(g, node, nodeSize, false, false);
        }
    }

}