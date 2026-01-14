package ui;

import config.Setup;
import core.Node;
import ui.themes.VisualType;
import utilities.CellState;
import utilities.ImageUtils;

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
  public static void draw(Graphics2D g, Node n, int size, boolean isLastRow, boolean isLastCol) {
    Graphics2D g2 = Setup.prepareGraphics(g);
    CellState state = n.getBaseState();
    Color stateColor = Setup.getColor(state.getVisualType());
    g2.setColor(stateColor);

    int x = n.indexX * size;
    int y = n.indexY * size;

    g2.fillRect(x, y, size, size);
    drawWalls(g2, n, size, isLastRow, isLastCol, x, y);

  }

  private static void drawWalls(Graphics2D g, Node n, int size, boolean isLastRow, boolean isLastCol, int x, int y) {
    g.setColor(Setup.getColor(VisualType.WALL));
    if (n.checkWall(Setup.UP) || n.indexY == 0)
      g.drawLine(x, y, x + size, y);
    if (n.checkWall(Setup.LEFT) || n.indexX == 0)
      g.drawLine(x, y, x, y + size);
    if (n.checkWall(Setup.DOWN) || isLastCol)
      g.drawLine(x, y + size - 1, x + size - 1, y + size - 1);
    if (n.checkWall(Setup.RIGHT) || isLastRow)
      g.drawLine(x + size - 1, y, x + size - 1, y + size - 1);
  }


}
