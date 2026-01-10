package entities;

import core.Node;
import utilities.Updater;

/**
 * Basic player implementation.
 */
public class Player implements Updater {

    public Node position;

    /**
     * initialize the player
     *
     * @param start the start node
     * @pre start is not null
     * @post an initialized player with a node declared as its start
     */
    public Player(Node start) {
        this.position = start;
    }

    /**
     * the possible directions a player could move in, no diagonal.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}