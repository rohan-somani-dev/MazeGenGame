package config;

import entities.Player;
import ui.themes.Theme;
import ui.themes.VisualType;
import utilities.FontLoader;
import utilities.HelpReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

  // LISTENER CODES
  // TODO: ADD CODES TO A MAP!
  public static final int MAZE_UPDATE = 0b000001;
  public static final int TIMER_TICK = 0b100000;
  public static final int TIMER_FINISHED = 0b01000000;
  public static final int ALL = 0b11111111;
  public static final int THEME_NOT_FOUND_ERROR = -1;
  public static final int PLAYER_ARC = 25;

  // player settings
  public static final float PLAYER_SHRINK = 0.9f;
  public static final HashMap<Integer, Player.Direction> KEY_BINDINGS = new HashMap<>();

  public static final HashMap<String, BufferedImage> ICONS = new HashMap<>();

  // constants
  public static final int UP = 0b0001;
  public static final int RIGHT = 0b0010;
  public static final int DOWN = 0b0100;
  public static final int LEFT = 0b1000;
  public static final int GRID_SIZE = 10;
  public static final int MAZE_SIZE = 1300;
  public static final int PADDING = 150;
  public static final int WINDOW_WIDTH = MAZE_SIZE + PADDING * 2;
  public static final int BAR_SIZE = 16;
  public static final Dimension SCREEN_SIZE;
  public static final float IMAGE_SCALE = 0.7f;
  public static final int PATH_WIDTH;
  public static final int mazeSleepTime = 0;
  // FONTS
  public static final Font REGULAR;
  public static final Font MONOSPACE;
  public static final HashMap<String, Theme> themes = new LinkedHashMap<>(); // linked hashmap maintains insertion order,
  public static String HELP_MESSAGE = "";
  // ensuring that getting a theme by index will be consistent
  public static Theme currentTheme;
  public static Theme defaultTheme;
  //SETTINGS
  public static boolean drawRainbowPath = false;
  public static boolean useAStar = true;

  static {

    KEY_BINDINGS.put(KeyEvent.VK_W, Player.Direction.UP);
    KEY_BINDINGS.put(KeyEvent.VK_UP, Player.Direction.UP);

    KEY_BINDINGS.put(KeyEvent.VK_A, Player.Direction.LEFT);
    KEY_BINDINGS.put(KeyEvent.VK_LEFT, Player.Direction.LEFT);

    KEY_BINDINGS.put(KeyEvent.VK_D, Player.Direction.RIGHT);
    KEY_BINDINGS.put(KeyEvent.VK_RIGHT, Player.Direction.RIGHT);

    KEY_BINDINGS.put(KeyEvent.VK_S, Player.Direction.DOWN);
    KEY_BINDINGS.put(KeyEvent.VK_DOWN, Player.Direction.DOWN);

    for (String s : new String[]{"player", "exit", "gears", "info", "checked", "unchecked"}) {
      try {
        URL path = Setup.class.getResource("/icons/" + s + ".png");
        if (path == null) continue;
        BufferedImage img = ImageIO.read(path);
        ICONS.put(s, img);
      } catch (IOException e) {
        Setup.handleError(e);
      }
    }

    initThemes();

    SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    PATH_WIDTH = Math.min(5, SCREEN_SIZE.width / 400);

    URL helpPath = Setup.class.getResource("/help.html");
    if (helpPath != null) {
      HELP_MESSAGE = HelpReader.readHelp(helpPath);
    }

    FontLoader.loadFonts();
    REGULAR = new Font("NunitoSans", Font.PLAIN, 18);
    MONOSPACE = new Font("Lilex", Font.PLAIN, 50);

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
    // TODO add logging
    // noinspection CallToPrintStackTrace
    e.printStackTrace();
  }

  /**
   * initialize themes
   *
   * @post themes hashmap populated with {@code <Name, Theme>} from each .png inside themes
   */
  public static void initThemes() {

    for (String s : new String[]{"default", "cyberpunk", "light", "pastel", "pink"}) {
      URL path = Setup.class.getResource("/themes/" + s + ".png");

      if (path == null) continue;

      Theme t = new Theme(path, s);
      themes.put(t.name, t);
    }
    currentTheme = themes.get("default");
    defaultTheme = currentTheme;
  }

  /**
   * Get the color of a type of element
   *
   * @param c the type of element to get the color of.
   * @return the obtained color from the current theme.
   *
   * @pre theme is initialized, AND HAS ALL VALUES SET
   * @post a nonnull color is returned, defaulting to pure white.
   */
  public static Color getColor(VisualType c) {
    return currentTheme.get(c);
  }

  /**
   * @return the current themes, as a map.
   */
  @SuppressWarnings("SameReturnValue")
  public static HashMap<String, Theme> getThemes() {
    return themes;
  }

  /**
   * @return an array of all the names of the current themes.
   */
  public static String[] getThemeNames() {
    String[] themeNames = new String[themes.size()];
    themes.keySet().toArray(themeNames);
    return themeNames;
  }

  /**
   * set the current theme to a theme name
   *
   * @param themeName the name of the theme to be changed to.
   * @pre themes map is populated with at least a default theme
   * @post theme is set to the param or default if not found.
   */
  public static void setTheme(String themeName) {
    currentTheme = themes.getOrDefault(themeName, defaultTheme);
  }

  /**
   * set the current theme to given theme from the index.
   *
   * @param index the index to be set to.
   */
  public static void setTheme(int index) {
    String[] themeNames = Setup.getThemeNames();
    if (index >= themeNames.length)
      return;
    Setup.setTheme(themeNames[index]);
  }

  /**
   * prepare a graphics object as a g2d object to be used. set rendering hints and such.
   *
   * @param g graphics object to be prepped
   * @return ready to use Graphics2D object.
   */
  public static Graphics2D prepareGraphics(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    return g2;
  }

}
