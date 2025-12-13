
/*
 * Author: RohanSomani
 * Name: Node
 * Date: 2025-12-10
 */

import java.awt.*;

public class Node implements Comparable<Node>{

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
     make sure not to add a wall twice.
    */

    boolean mazeVisited; //for maze gen;
    boolean isTarget;
    boolean isEnd;
    boolean isStart;

    int indexX;
    int indexY;

    boolean debug;
    
    boolean pathVisited; 

    Node target; 
    
    public Node(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
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
        Color fillColor = mazeVisited ? Setup.VISITED_COLOR : null;
        if (isTarget) fillColor = Setup.TARGET_COLOR;
        if (debug) fillColor = Setup.DEBUG_COLOR;
        if (isStart) fillColor = Setup.START_COLOR;
        if (isEnd) fillColor = Setup.END_COLOR;
        g.setColor(fillColor);

        int x = indexX * size;
        int y = indexY * size;

//        fill back
        if (mazeVisited || isTarget) {
            g.fillRect(x, y, size, size);
        }

//        draw walls
        g.setColor(Setup.WALL_COLOR);
        if ((walls & UP) != 0 || indexY == 0) g.drawLine(x, y, x + size, y);
        if ((walls & LEFT) != 0 || indexX == 0) g.drawLine(x, y, x, y + size);
        if ((walls & DOWN) != 0 || isLastCol) g.drawLine(x, y + size - 1, x + size - 1, y + size -  1);
        if ((walls & RIGHT) != 0 || isLastRow) g.drawLine(x + size - 1, y, x + size - 1, y + size - 1);


    }
    

    public int getManhattanDistance(Node other){
    	return Math.abs(other.indexX - indexX) + Math.abs(other.indexY + indexY);
    }

	@Override
	public int compareTo(Node other) {
		int ourDist = this.getManhattanDistance(target);
		int theirDist = other.getManhattanDistance(target);
		return Integer.compare(-ourDist, -theirDist);
	}

}