package ui;

import config.Setup;
import core.Grid;
import core.Node;
import ui.themes.VisualType;
import utilities.CellState;
import utilities.ImageUtils;
import utilities.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.util.ArrayList;
import java.util.Queue;

/**
 * GridRenderer holds a grid object and draws onto itself when updated.
 *
 * @author RohanSomani
 * @name GridRenderer
 * @date 2025-12-16
 */
public class GridRenderer extends JPanel implements Renderable {

  private final Grid grid; // final indicates it should only be read from, not written to.
  // if i write into it from here i'm doing something wrong2.

  /**
   * initialize GridRenderer; set basic properties.
   *
   * @param grid the grid to be read from.
   */
  public GridRenderer(Grid grid) {
    this.grid = grid;
    setBackground(Setup.getColor(VisualType.BACKGROUND));
    setFocusable(false);
    setOpaque(true);

  }

  /**
   * called when the ui needs to be updated, updates all child nodes as well.
   *
   * @pre initialized, {@link Grid#nodes} should be able to be read from.
   * @post maze screen is redrawn with updates node states and walls.
   */
  @Override
  public void onUpdate() {
    SwingUtilities.invokeLater(this::repaint);
  }

  /**
   * get the JComponent of self, which is a JPanel.
   *
   * @return this, essentially just passing JPanel self on so the static typing
   *         works. don't you just LOOOOVE java??
   */
  @Override
  public JComponent getComponent() {
    return this;
  }

  /**
   * paint every node in the grid, calculate nodeSize dynamically.
   *
   * @param g the {@link Graphics} object to protect
   * @pre grid initialized, every node has some base state.
   * @post JPanel is drawn onto with the nodes and walls.
   */
  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = Setup.prepareGraphics(g);
    setBackground(Setup.getColor(VisualType.BACKGROUND));
    super.paintComponent(g2);

    int width = getWidth();
    int height = getHeight();

    int minDimension = Math.min(width, height);

    int xOffset = (width - minDimension) / 2;
    int yOffset = (height - minDimension) / 2;

    // to center panel
    Graphics2D center = Setup.prepareGraphics(g2);
    center.translate(xOffset, yOffset);

    int nodeSize = minDimension / grid.gridSize;

    for (Node node : grid.getNodes()) {
      NodeDrawer.draw(center, node, nodeSize, false, false);
    }

    ArrayList<Node> path = grid.getPath();
    if (path != null)
      drawPath(center, path, nodeSize);

    if (grid.player != null) {
      drawPlayer(center, nodeSize);
    }

    // The ordering here is important. Background, then path, then player.

    center.dispose();
  }

  private void drawPlayer(Graphics2D g2, int size) {
    Node n = grid.player.position;
    int x = n.indexX * size;
    int y = n.indexY * size;

    int newSize = (int) (size * Setup.PLAYER_SHRINK);
    int offset = (size - newSize) / 2;
    int playerX = x + offset;
    int playerY = y + offset;

    // update player
    g2.setColor(Setup.getColor(VisualType.TARGET));
    g2.fillRoundRect(
        playerX,
        playerY,
        newSize,
        newSize,
        Setup.PLAYER_ARC,
        Setup.PLAYER_ARC);
    g2.setColor(Setup.getColor(VisualType.PLAYER));
    g2.fillRoundRect(playerX, playerY, newSize, newSize, Setup.PLAYER_ARC, Setup.PLAYER_ARC);

    // update little eyes :)
    g2.setComposite(AlphaComposite.SrcOver); // allow transparency

    Image smileRecolored = ImageUtils.tint(Setup.SMILE, Setup.getColor(VisualType.WALL).brighter());

    int newImageSize = (int) (size * Setup.IMAGE_SCALE);
    int imageOffset = (size - newImageSize) / 2;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    smileRecolored = smileRecolored.getScaledInstance(newImageSize, newImageSize, Image.SCALE_SMOOTH);
    g2.drawImage(
        smileRecolored,
        x + imageOffset,
        y + imageOffset,
        newImageSize,
        newImageSize,
        null);

    g2.dispose();

  }

  private void drawPath(Graphics2D g2, ArrayList<Node> path, int size) {

    int length = path.size();
    float hueInc = 2f / length;
    float h = 0f, s = 0.5f, v = 1f;
    Color c;
    int prevX = -1;
    int prevY = -1;
    for (Node n : path) {
      c = Color.getHSBColor(h, s, v);
      int x = n.indexX * size;
      int y = n.indexY * size;
      g2.setColor(c);
      h += hueInc;

      if (prevX != -1 && prevY != -1) {
        g2.setStroke(new BasicStroke(Setup.PATH_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2.drawLine(x + size / 2, y + size / 2, prevX + size / 2, prevY + size / 2);
      }

      prevX = x;
      prevY = y;
    } 

  }

  @Override
  public Dimension getPreferredSize() {
    int size = Math.min(getParent().getWidth(), getParent().getHeight());
    return new Dimension(size, size);
  }

}
