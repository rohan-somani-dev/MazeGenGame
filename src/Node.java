
/*
 * Author: RohanSomani
 * Name: Node
 * Date: 2025-12-10
 */

import helpers.CellState;

import java.awt.*;
import java.util.Map;

public class Node implements Comparable<Node> {

    static final int UP = 0b0001;
    static final int RIGHT = 0b0010;
    static final int DOWN = 0b0100;
    static final int LEFT = 0b1000;
    int walls = 0b1111; // one bit per wall
    int indexX;
    /*
     to remove a wall do &= ~value
     to add a wall do + value;
     example:
     walls &= ~UP removes the top wall
     walls += UP adds the top wall
     make sure not to add a wall twice will lead to overflow.
    */
    int indexY;
    //    maze gen states
    boolean mazeVisited;
    Node target;
    //path states
    boolean pathVisited;
    boolean onPath;
    Node parent;
    Map<CellState, Color> COLORS = Map.of(
            CellState.PLAYER, Setup.PLAYER_COLOR,
            CellState.PATH, Setup.PATH_COLOR,
            CellState.PATH_LOOKING, Setup.PATH_LOOKING_COLOR,
            CellState.END, Setup.END_COLOR,
            CellState.START, Setup.START_COLOR,
            CellState.DEBUG, Setup.DEBUG_COLOR,
            CellState.TARGET, Setup.TARGET_COLOR,
            CellState.VISITED, Setup.VISITED_COLOR,
            CellState.BACKGROUND, Setup.BACKGROUND_COLOR
    );

    private CellState overlayState;
    private CellState baseState;

    public Node(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.setBaseState(CellState.BACKGROUND);
    }

    static boolean canWalk(Node a, Node b) {
        int deltaX = a.indexX - b.indexX;
        int deltaY = a.indexY - b.indexY;
        if (deltaX == -1 && (a.checkWall(RIGHT) || b.checkWall(LEFT))) return false;
        if (deltaX == 1 && (a.checkWall(LEFT) || b.checkWall(RIGHT))) return false;
        if (deltaY == -1 && (a.checkWall(DOWN) || b.checkWall(UP))) return false;
        return deltaY != 1 || (!a.checkWall(UP) && !b.checkWall(DOWN));
    }

    public CellState getBaseState() {
        return baseState;
    }

    public void setBaseState(CellState newBaseState) {
        this.baseState = newBaseState;
    }

    public void setOverlayState(CellState newState) {
        this.overlayState = newState;
    }

    public CellState getState() {
        if (overlayState != null) return overlayState;
        return baseState;
    }

    /**
     * Draws the grid with each node having a wall.
     *
     * @param g         Graphics
     * @param size      size of the cell
     * @param isLastRow is it the last row in the array?
     * @param isLastCol is it the last column in the array?
     */
    public void draw(Graphics g, int size, boolean isLastRow, boolean isLastCol) {
        CellState currState = getState();
        g.setColor(COLORS.get(currState));

        int x = indexX * size;
        int y = indexY * size;

        if (currState == CellState.PLAYER) {
            drawPlayer(x, y, size, g);
        } else {
            g.fillRect(x, y, size, size);
        }

//        draw walls
        g.setColor(Setup.WALL_COLOR);
        if (checkWall(UP) || indexY == 0) g.drawLine(x, y, x + size, y);
        if (checkWall(LEFT) || indexX == 0) g.drawLine(x, y, x, y + size);
        if (checkWall(DOWN) || isLastCol) g.drawLine(x, y + size - 1, x + size - 1, y + size - 1);
        if (checkWall(RIGHT) || isLastRow) g.drawLine(x + size - 1, y, x + size - 1, y + size - 1);

    }

    private void drawPlayer(int x, int y, int size, Graphics g) {
//        draw background
        g.setColor(COLORS.get(baseState));
        g.fillRect(x, y, size, size);

        int newSize = size - Setup.PLAYER_SHRINK * 2;

//        draw player
        g.setColor(COLORS.get(CellState.PLAYER));
        g.fillRect(x + Setup.PLAYER_SHRINK, y + Setup.PLAYER_SHRINK, newSize, newSize);

//        draw little face :)
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.SrcOver); //allow transparency

        g2.drawImage(Setup.SMILE, x + Setup.PLAYER_SHRINK, y + Setup.PLAYER_SHRINK, newSize, newSize, null);

        g2.dispose();

    }

    public int getManhattanDistance(Node other) {
        return Math.abs(other.indexX - indexX) + Math.abs(other.indexY - indexY);
    }

    @Override
    public int compareTo(Node other) {
        int ourDist = this.getManhattanDistance(target);
        int theirDist = other.getManhattanDistance(target);
        return Integer.compare(-ourDist, -theirDist);
    }

    boolean checkWall(int wall) {
        return (walls & wall) != 0;
    }

    public void clearOverlay() {
        this.setOverlayState(null);
    }

}