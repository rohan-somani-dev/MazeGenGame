
/*
 * Author: RohanSomani
 * Name: Config
 * Date: 2025-12-10
 */

import java.awt.*;

public class Setup {

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

    public static final int GRID_SIZE = 50;
    public static final int WINDOW_SIZE = 800;
    public static final int FUNCTION_SUCCESS = 0;
    public static final int INTERRUPTED_ERROR = 1;

    public static int sleepTime = 5;
    public static int pathSleepTime = 5;

    private Setup() {
    }

    public static void handleError(Exception e) {
        e.printStackTrace();
    }
}