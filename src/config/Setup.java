package config;

import config.themes.Theme;
import config.themes.ThemeColor;
import config.themes.ThemeHolder;
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

/**
 * Constants, setup fields, and maps.
 *
 * @author RohanSomani
 * @name Config
 * @date 2025-12-10
 *                         TODO: add themes
 */
@SuppressWarnings("unused")
public class Setup {

    //    LISTENER CODES
//    TODO: ADD CODES TO A MAP!
    public static final int MAZE_FINISHED = 0b0100;
    public static final int PATH_FINISHED = 0b1000;
    public static final int ALL = 0b1111;

    //TODO: make the switch from background to visited back to background make sense.
    public static final Theme theme = ThemeHolder.PASTEL;

    //    player settings
    public static final int PLAYER_SHRINK = 10;
    public static final Map<Integer, Player.Direction> KEY_BINDINGS = new HashMap<>();

    public static final Map<CellState, Color> COLORS = new HashMap<>();

    public static final BufferedImage SMILE;

    //constants
    public static final int UP = 0b0001;
    public static final int RIGHT = 0b0010;
    public static final int DOWN = 0b0100;
    public static final int LEFT = 0b1000;
    public static final int GRID_SIZE = 10;
    public static final int WINDOW_SIZE = 1000;
    public static final int FUNCTION_SUCCESS = 0;
    public static final int INTERRUPTED_ERROR = 1;

    public static int mazeSleepTime = 10;
    @SuppressWarnings("CanBeFinal") //temp
    public static int pathSleepTime = 10;
    @SuppressWarnings("CanBeFinal") //temp
    public static int sleepTimeBetweenPathRetrace = 10;

    static {

        KEY_BINDINGS.put(KeyEvent.VK_W, Player.Direction.UP);
        KEY_BINDINGS.put(KeyEvent.VK_UP, Player.Direction.UP);

        KEY_BINDINGS.put(KeyEvent.VK_A, Player.Direction.LEFT);
        KEY_BINDINGS.put(KeyEvent.VK_LEFT, Player.Direction.LEFT);

        KEY_BINDINGS.put(KeyEvent.VK_D, Player.Direction.RIGHT);
        KEY_BINDINGS.put(KeyEvent.VK_RIGHT, Player.Direction.RIGHT);

        KEY_BINDINGS.put(KeyEvent.VK_S, Player.Direction.DOWN);
        KEY_BINDINGS.put(KeyEvent.VK_DOWN, Player.Direction.DOWN);

        //TODO: this map seems overkill. maybe can do away with it?
        COLORS.put(CellState.PLAYER, getColor(ThemeColor.PLAYER));
        COLORS.put(CellState.PATH, getColor(ThemeColor.PATH));
        COLORS.put(CellState.END, getColor(ThemeColor.END));
        COLORS.put(CellState.START, getColor(ThemeColor.START));
        COLORS.put(CellState.TARGET, getColor(ThemeColor.TARGET));
        COLORS.put(CellState.VISITED, getColor(ThemeColor.VISITED));
        COLORS.put(CellState.BACKGROUND, getColor(ThemeColor.BACKGROUND));
        COLORS.put(CellState.DEBUG, getColor(ThemeColor.DEBUG));

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
     *
     * @param e the exception to be handled
     * @pre None
     * @post log the exception.
     */
    public static void handleError(Exception e) {
        //TODO add logging
        e.printStackTrace();
    }

    /**
     * Get the color of a type of element
     * @pre theme is initialized, AND HAS ALL VALUES SET
     * @post a nonnull color is returned, defaulting to pure white.
     * @param c the type of element to get the color of.
     * @return the obtained color from the current theme.
     */
    public static Color getColor(ThemeColor c) {
        return theme.get(c);
    }
}