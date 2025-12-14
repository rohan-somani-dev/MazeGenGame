
/*
 * Author: RohanSomani
 * Name: Node
 * Date: 2025-12-10
 */

import java.awt.*;

public class Node implements Comparable<Node> {

    static final int UP = 0b0001;
    static final int RIGHT = 0b0010;
    static final int DOWN = 0b0100;
    static final int LEFT = 0b1000;
    int walls = 0b1111; // one bit per wall
    /*
     to remove a wall do &= ~value
     to add a wall do + value;
     example:
     walls &= ~UP removes the top wall
     walls += UP adds the top wall
     make sure not to add a wall twice will lead to overflow.
    */

    int indexX;
    int indexY;

//    maze gen states
    boolean mazeVisited;
    boolean isTarget;
    Node target;

// identities
    boolean isEnd;
    boolean isStart;

//debug
    boolean debug;

//path states
    boolean pathVisited;
    boolean onPath;
    Node parent;

//occupancy
    public boolean isPlayer;

    public Node(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }

    static boolean canWalk(Node a, Node b) {
        int deltaX = a.indexX - b.indexX;
        int deltaY = a.indexY - b.indexY;
        if (deltaX == -1 && (a.checkWall(RIGHT) || b.checkWall(LEFT))) return false;
        if (deltaX == 1 && (a.checkWall(LEFT) || b.checkWall(RIGHT))) return false;
        if (deltaY == -1 && (a.checkWall(DOWN) || b.checkWall(UP))) return false;
        return deltaY != 1 || (!a.checkWall(UP) && !b.checkWall(DOWN));
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
//        TODO: CLEAN UP IFS. ADD ENUM? OR CLASS?

        Color fillColor = mazeVisited ? Setup.VISITED_COLOR : Setup.BACKGROUND_COLOR;
        if (isTarget) fillColor = Setup.TARGET_COLOR;
        if (debug) fillColor = Setup.DEBUG_COLOR;
        if (isStart) fillColor = Setup.START_COLOR;
        if (isEnd) fillColor = Setup.END_COLOR;
        if (pathVisited) fillColor = Setup.PATH_LOOKING_COLOR;
        if (onPath) fillColor = Setup.PATH_COLOR;
        if (isPlayer) fillColor = Setup.PLAYER_COLOR;
        g.setColor(fillColor);

        int x = indexX * size;
        int y = indexY * size;

//        fill back
            g.fillRect(x, y, size, size);

//        draw walls
        g.setColor(Setup.WALL_COLOR);
        if (checkWall(UP) || indexY == 0) g.drawLine(x, y, x + size, y);
        if (checkWall(LEFT) || indexX == 0) g.drawLine(x, y, x, y + size);
        if (checkWall(DOWN) || isLastCol) g.drawLine(x, y + size - 1, x + size - 1, y + size - 1);
        if (checkWall(RIGHT) || isLastRow) g.drawLine(x + size - 1, y, x + size - 1, y + size - 1);

    }

    public int getManhattanDistance(Node other) {
        return Math.abs(other.indexX - indexX) + Math.abs(other.indexY + indexY);
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

    public void clearFlags() {
        mazeVisited = false;
        isTarget = false;
        pathVisited = false;
        onPath = false;
        isEnd = false;
        isStart = false;
    }
}