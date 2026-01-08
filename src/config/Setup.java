package config;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;

import entities.Player;
import ui.themes.Theme;
import ui.themes.VisualType;

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
  public static final int MAZE_UPDATE = 0b0101010;
  public static final int ALL = 0b1111;
  public static final int THEME_NOT_FOUND_ERROR = -1;
  public static final int PLAYER_ARC = 25;

  @SuppressWarnings("CanBeFinal")
  public static HashMap<String, Theme> themes = new HashMap<>();
  public static Theme currentTheme;
  public static Theme defaultTheme;

  // player settings
  public static final int PLAYER_SHRINK = 7;
  public static final Map<Integer, Player.Direction> KEY_BINDINGS = new HashMap<>();

  public static final BufferedImage SMILE;

  // constants
  public static final int UP = 0b0001;
  public static final int RIGHT = 0b0010;
  public static final int DOWN = 0b0100;
  public static final int LEFT = 0b1000;

  public static final int GRID_SIZE = 20;
  public static final int MAZE_MIN_SIZE = 500;
  public static final int MAZE_SIZE = 1300;
  public static final int PADDING = 150;
  public static final int WINDOW_WIDTH = MAZE_SIZE + PADDING * 2;

  public static final int FUNCTION_SUCCESS = 0;
  public static final int INTERRUPTED_ERROR = 1;
  public static final int BAR_SIZE = 16;

  public static int mazeSleepTime = 0;
  @SuppressWarnings("CanBeFinal") // temp
  public static int pathSleepTime = 3;
  public static final int STEPS_PER_REDRAW = 1;
  @SuppressWarnings("CanBeFinal") // temp
  public static int sleepTimeBetweenPathRetrace = 5;

  public static final File themeDir = new File("resources/themes");

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
      File f = new File("resources/icons/player.png");
      img = ImageIO.read(f);
    } catch (IOException e) {
      Setup.handleError(e);
    }
    SMILE = img;

    initThemes();
    readFromSave("saves/save1.txt");
    System.out.println(currentTheme.name);
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
    e.printStackTrace();
  }

  /**
   * initialize themes. equivalent to {@code regenThemes(true)}
   *
   * @post themes hashmap is initialized based on the population in
   *       resources/themes
   */
  public static void initThemes() {
    regenThemes(true);
  }

  /**
   * regenerate themes, essentially checking for updates inside resources/themes.
   *
   * @param initialize whether to set the current theme to default.
   * @post themes hashmap populated with {@code <Name, Theme>} from each .png
   *       inside themes
   */
  public static void regenThemes(boolean initialize) {
    File[] themeFiles = getThemeFiles();
    if (themeFiles == null) {
      Setup.handleError(new FileNotFoundException("NO THEME FILES!"));
      return;
    }
    for (File f : themeFiles) {
      Theme t = new Theme(f);
      themes.put(t.name, t);
    }
    if (!initialize)
      return;
    currentTheme = themes.get("default");
    defaultTheme = currentTheme;
    if (currentTheme == null) {
      Setup.handleError(
          new Exception("NO DEFAULT THEME FOUND."));
    }
  }

  /**
   * Get the color of a type of element
   * 
   * @pre theme is initialized, AND HAS ALL VALUES SET
   * @post a nonnull color is returned, defaulting to pure white.
   * @param c the type of element to get the color of.
   * @return the obtained color from the current theme.
   */
  public static Color getColor(VisualType c) {
    return currentTheme.get(c);
  }

  public static HashMap<String, Theme> getThemes() {
    regenThemes(false);
    return themes;
  }

  /**
   * return a list of the current selectable themes.
   *
   * @return returns a {@code String[]} object containing all the themes, or null
   *         if it cannot find themes.
   */
  public static File[] getThemeFiles() {
    ArrayList<String> themeNames = new ArrayList<>();
    if (!themeDir.isDirectory()) {
      System.out.println("IT'S NOT A DIRECTORY");
      return null;
    }

    return themeDir.listFiles(file -> file.getName().endsWith(".png"));
  }

  /**
   * @return a set of all the names of the current themes.
   */
  public static Set<String> getThemeNames() {
    return themes.keySet();
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

  public static Graphics2D prepareGraphics(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    return g2;
  }

  public static void readFromSave(String path) {
    File saveFile = new File(path);
    String themeName = "";
    try (Scanner input = new Scanner(saveFile)) {
      themeName = input.nextLine();
    } catch (FileNotFoundException e) {
      Setup.handleError(e);
    }
    System.out.println(themeName);
    Setup.setTheme(themeName);

  }

  public static void save() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("saves/save1.txt"))) {
      bw.write(currentTheme.name);
    } catch (IOException e) {
      Setup.handleError(e);
    }
  }
}
