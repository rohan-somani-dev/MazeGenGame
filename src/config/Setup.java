package config;

import config.themes.Theme;
import config.themes.VisualType;
import config.themes.ThemeHolder;
import entities.Player;

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
 *
 */
@SuppressWarnings("unused")
public class Setup {

    //    LISTENER CODES
//    TODO: ADD CODES TO A MAP!
    public static final int MAZE_UPDATE = 0b0101010;
    public static final int ALL = 0b1111;

    public static final Theme theme = ThemeHolder.DARK;

    //    player settings
    public static final int PLAYER_SHRINK = 5;
    public static final Map<Integer, Player.Direction> KEY_BINDINGS = new HashMap<>();


    public static final BufferedImage SMILE;

    //constants
    public static final int UP = 0b0001;
    public static final int RIGHT = 0b0010;
    public static final int DOWN = 0b0100;
    public static final int LEFT = 0b1000;



    public static final int GRID_SIZE = 20;
    public static final int MAZE_MIN_SIZE = 500;
    public static final int MAZE_SIZE = 1300;
    public static final int PADDING = 150;
    public static final int WINDOW_WIDTH = MAZE_SIZE + PADDING*2;


    public static final int FUNCTION_SUCCESS = 0;
    public static final int INTERRUPTED_ERROR = 1;

    public static int mazeSleepTime = 0;
    @SuppressWarnings("CanBeFinal") //temp
    public static int pathSleepTime = 3;
    public static final int STEPS_PER_REDRAW = 1;
    @SuppressWarnings("CanBeFinal") //temp
    public static int sleepTimeBetweenPathRetrace = 1000;

    static {

        KEY_BINDINGS.put(KeyEvent.VK_W, Player.Direction.UP);
        KEY_BINDINGS.put(KeyEvent.VK_UP, Player.Direction.UP);

        KEY_BINDINGS.put(KeyEvent.VK_A, Player.Direction.LEFT);
        KEY_BINDINGS.put(KeyEvent.VK_LEFT, Player.Direction.LEFT);

        KEY_BINDINGS.put(KeyEvent.VK_D, Player.Direction.RIGHT);
        KEY_BINDINGS.put(KeyEvent.VK_RIGHT, Player.Direction.RIGHT);

        KEY_BINDINGS.put(KeyEvent.VK_S, Player.Direction.DOWN);
        KEY_BINDINGS.put(KeyEvent.VK_DOWN, Player.Direction.DOWN);

        BufferedImage img = null;
        try {
            File f = new File("resources/smile.png");
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
    public static Color getColor(VisualType c) {
        return theme.get(c);
    }
}