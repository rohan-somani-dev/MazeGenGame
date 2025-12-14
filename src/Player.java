public class Player {

    static final String movementKeys = "WASDLEFTRIGHTUPDOWN";

    Node position;

    public Player(Node start) {
        this.position = start;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}