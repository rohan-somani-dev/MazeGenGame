package entities;

import core.Node;
import utilities.Updater;

public class Player implements Updater {

    public Node position;

    public Player(Node start) {
        this.position = start;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}