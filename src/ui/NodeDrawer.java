package ui;

import config.Setup;
import config.themes.VisualType;
import core.Node;
import utilities.CellState;

import java.awt.*;

/**
 * a class to draw nodes
 *
 * @author RohanSomani
 * @name NodeRenderer
 * @date 2025-12-16
 */
public class NodeDrawer {

    /**
     * draw the node.
     *
     * @param g         the graphics object to be drawn on.
     * @param n         the node to be used to get state, position and such.
     * @param size      the size of the node to be drawn.
     * @param isLastRow self-explanatory.
     * @param isLastCol see above.
     * @pre JPanel must be able to be drawn on, position must be on screen.
     * @post Node is drawn based on given nodes state.
     */
    public static void draw(Graphics g, Node n, int size, boolean isLastRow, boolean isLastCol) {
        CellState state = n.getState();
        Color stateColor = Setup.getColor(state.getVisualType());
        g.setColor(stateColor);

        int x = n.indexX * size;
        int y = n.indexY * size;

        if (state == CellState.PLAYER) {
            drawPlayer(n, x, y, size, g);
        } else {
            g.fillRect(x, y, size, size);
        }

        g.setColor(Setup.getColor(VisualType.WALL));
        if (n.checkWall(Setup.UP) || n.indexY == 0) g.drawLine(x, y, x + size, y);
        if (n.checkWall(Setup.LEFT) || n.indexX == 0) g.drawLine(x, y, x, y + size);
        if (n.checkWall(Setup.DOWN) || isLastCol) g.drawLine(x, y + size - 1, x + size - 1, y + size - 1);
        if (n.checkWall(Setup.RIGHT) || isLastRow) g.drawLine(x + size - 1, y, x + size - 1, y + size - 1);

    }

    private static void drawPlayer(Node n, int x, int y, int size, Graphics g) {
//        update background
        CellState baseState = n.getBaseState();
        Color baseColor = Setup.getColor(baseState.getVisualType());
        g.setColor(baseColor);
        g.fillRect(x, y, size, size);

        int newSize = size - Setup.PLAYER_SHRINK * 2;

//        update player
        g.setColor(Setup.getColor(VisualType.PLAYER));
        g.fillRect(x + Setup.PLAYER_SHRINK, y + Setup.PLAYER_SHRINK, newSize, newSize);

//        update little face :)
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.SrcOver); //allow transparency

        g2.drawImage(Setup.SMILE, x + Setup.PLAYER_SHRINK, y + Setup.PLAYER_SHRINK, newSize, newSize, null);

        g2.dispose();

    }

}