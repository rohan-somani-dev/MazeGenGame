package config;

import entities.Player;
import utilities.CellState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/** Constants, setup fields, and maps.
 * @author RohanSomani
 * @name Config
 * @date 2025-12-10
 * TODO: add themes
 */
@SuppressWarnings("unused")
public class Setup {

    //    LISTENER CODES
//    TODO: ADD CODES TO A MAP!
    public static final int MAZE_FINISHED = 0b0100;
    public static final int PATH_FINISHED = 0b1000;
    public static final int ALL = 0b1111;
    //    generic colors
    public static final Color WALL_COLOR = new Color(0xFFFFFF);
    public static final Color TARGET_COLOR = new Color(0xCC758C);
    public static final Color BACKGROUND_COLOR = new Color(0x3C1620);
    public static final Color DEBUG_COLOR = new Color(255, 0, 0);
    //    maze colors
    public static final Color VISITED_COLOR = new Color(0x02182B);
    public static final Color START_COLOR = new Color(0x6266C2);
    public static final Color END_COLOR = new Color(0xA673B6);
    //    pathfind colors
    public static final Color PATH_COLOR = new Color(0x824343);
    public static final Color PATH_LOOKING_COLOR = new Color(0xFFB4B4);
    public static final int GRID_SIZE = 10;
    public static final int WINDOW_SIZE = 1000;
    public static final int FUNCTION_SUCCESS = 0;
    public static final int INTERRUPTED_ERROR = 1;
    //    player settings
    public static final Color PLAYER_COLOR = new Color(0xF5B2B2);
    public static final int PLAYER_SHRINK = 10;
    public static final Map<Integer, Player.Direction> KEY_BINDINGS = new HashMap<>();

    public static final Map<CellState, Color> COLORS = new HashMap<>();

    public static final BufferedImage SMILE;

    public static final int UP = 0b0001;
    public static final int RIGHT = 0b0010;
    public static final int DOWN = 0b0100;
    public static final int LEFT = 0b1000;


    public static int mazeSleepTime = 20;
    public static final int pathSleepTime = 20;
    public static final int sleepTimeBetweenPathRetrace = 200;

    static {

        KEY_BINDINGS.put(KeyEvent.VK_W, Player.Direction.UP);
        KEY_BINDINGS.put(KeyEvent.VK_UP, Player.Direction.UP);

        KEY_BINDINGS.put(KeyEvent.VK_A, Player.Direction.LEFT);
        KEY_BINDINGS.put(KeyEvent.VK_LEFT, Player.Direction.LEFT);

        KEY_BINDINGS.put(KeyEvent.VK_D, Player.Direction.RIGHT);
        KEY_BINDINGS.put(KeyEvent.VK_RIGHT, Player.Direction.RIGHT);

        KEY_BINDINGS.put(KeyEvent.VK_S, Player.Direction.DOWN);
        KEY_BINDINGS.put(KeyEvent.VK_DOWN, Player.Direction.DOWN);

        COLORS.put(CellState.PLAYER, Setup.PLAYER_COLOR);
        COLORS.put(CellState.PATH, Setup.PATH_COLOR);
        COLORS.put(CellState.PATH_LOOKING, Setup.PATH_LOOKING_COLOR);
        COLORS.put(CellState.END, Setup.END_COLOR);
        COLORS.put(CellState.START, Setup.START_COLOR);
        COLORS.put(CellState.DEBUG, Setup.DEBUG_COLOR);
        COLORS.put(CellState.TARGET, Setup.TARGET_COLOR);
        COLORS.put(CellState.VISITED, Setup.VISITED_COLOR);
        COLORS.put(CellState.BACKGROUND, Setup.BACKGROUND_COLOR);

        BufferedImage img = null;
        try {
            File f = new File("../resources/smile.png");
            img = ImageIO.read(f);
        } catch (IOException e) {
            Setup.handleError(e);
        }
        SMILE = img;
    }

    private Setup() {
    }

    /**
     * handle error
     * @pre None
     * @post log the exception.
     * @param e the exception to be handled
     */
    public static void handleError(Exception e) {
        //TODO add logging
         e.printStackTrace();
    }
}