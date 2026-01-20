package ui.themes;

import config.Setup;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Create a boilerplate theme class to be expanded.
 *
 * @author RohanSomani
 * @name Theme
 * @date 2025-12-20
 */
public class Theme {
  public final String name;
  private final EnumMap<VisualType, Color> colors = new EnumMap<>(VisualType.class); // thanks!
  // https://www.baeldung.com/java-enum-map

  /**
   * Initialize theme from image file.
   *
   * @param file the image file to be read from as a theme. MUST HAVE WIDTH
   *             SETUP.BAR_SIZE OR WILL FAIL.
   * @param name the name of the theme.
   */
  public Theme(URL file, String name) {
    this.name = name;

    BufferedImage image = null;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      Setup.handleError(e);
    }

    if (image == null) {
      return;
    }

    VisualType[] entries = VisualType.values();
    for (int i = 0; i < entries.length; i++) {
      VisualType entry = entries[i];
      int x = i * Setup.BAR_SIZE;
      Color c = new Color(image.getRGB(x, 0));

      colors.put(entry, c);
    }

  }

  /**
   * get the color of a given element type.
   *
   * @param visualType the type of element to get the color of.
   * @return a nonnull color from themes.
   *
   * @pre this.colors is initialized.
   * @post color is returned, defaulting to pure white.
   */
  public Color get(VisualType visualType) {
    return colors.getOrDefault(visualType, new Color(0xffffff));
  }

  /**
   * print the current theme. debug method.
   */
  @SuppressWarnings("unused")
  public void print() {

    ArrayList<String> colorList = new ArrayList<>();
    for (VisualType vt : colors.keySet()) {
      String curr = Integer.toHexString(colors.get(vt).getRGB());

      colorList.add(curr.substring(2));
    }
  }

}
