package core;
/*
 * Author: RohanSomani
 * Name: core.Node
 * Date: 2025-12-10
 */

import utilities.CellState;

import static config.Setup.*;

public class Node implements Comparable<Node> {

    public int indexX;
    public int indexY;

    int walls = 0b1111; // one bit per wall
    /*
     to remove a wall do &= ~value
     to add a wall do + value;
     example:
     walls &= ~UP removes the top wall
     walls += UP adds the top wall
     make sure not to add a wall twice will lead to overflow.
    */

    //    maze gen states
    boolean mazeVisited;
    Node target;

    //path states
    boolean pathVisited;
    boolean onPath;
    Node parent;

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

    public int getManhattanDistance(Node other) {
        return Math.abs(other.indexX - indexX) + Math.abs(other.indexY - indexY);
    }

    @Override
    public int compareTo(Node other) {
        int ourDist = this.getManhattanDistance(target);
        int theirDist = other.getManhattanDistance(target);
        return Integer.compare(-ourDist, -theirDist);
    }

    public boolean checkWall(int wall) {
        return (walls & wall) != 0;
    }

    public void clearOverlay() {
        this.setOverlayState(null);
    }

}