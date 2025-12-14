
/*
 * Author: RohanSomani
 * Name: Config
 * Date: 2025-12-10
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Map;

public class Setup {

    //keybindings
    public static final String START_PATH = "SPACE";

    //generic colors
    public static final Color WALL_COLOR = new Color(0xFFFFFF);
    public static final Color TARGET_COLOR = new Color(0xCC758C);
    public static final Color BACKGROUND_COLOR = new Color(0x3C1620);
    public static final Color DEBUG_COLOR = new Color(255, 0, 0);

    //maze colors
    public static final Color VISITED_COLOR = new Color(0x02182B);
    public static final Color START_COLOR = new Color(0x6266C2);
    public static final Color END_COLOR = new Color(0xA673B6);

    //a* colors
    public static final Color PATH_COLOR = new Color(0xFF8787);
    public static final Color PATH_LOOKING_COLOR = new Color(0xFFB4B4);

    public static final int GRID_SIZE = 10;
    public static final int WINDOW_SIZE = 1000;
    public static final int FUNCTION_SUCCESS = 0;
    public static final int INTERRUPTED_ERROR = 1;

    //player colors
    public static final Color PLAYER_COLOR = new Color(0xFFFFFF);

    public static int mazeSleepTime = 0;
    public static int pathSleepTime = 0;
    public static int sleepTimeBetweenPathRetrace = 0;

    static final Map<Integer, Player.Direction> KEY_BINDINGS = Map.of(
            KeyEvent.VK_W, Player.Direction.UP,
            KeyEvent.VK_UP, Player.Direction.UP,

            KeyEvent.VK_A, Player.Direction.LEFT,
            KeyEvent.VK_LEFT, Player.Direction.LEFT,

            KeyEvent.VK_D, Player.Direction.RIGHT,
            KeyEvent.VK_RIGHT, Player.Direction.RIGHT,

            KeyEvent.VK_S, Player.Direction.DOWN,
            KeyEvent.VK_DOWN, Player.Direction.DOWN
    );

    private Setup() {
    }

    public static void handleError(Exception e) {
        e.printStackTrace();
    }
}