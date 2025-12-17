public class Player {

    Node position;

    public Player(Node start) {
        this.position = start;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}