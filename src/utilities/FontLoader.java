package utilities;

import config.Setup;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * A simple utility class to load fonts from the resources folder
 *
 * @author RohanSomani
 * @date Jan 14, 2026
 * @name FontLoader
 */
public class FontLoader {

  private FontLoader() {
    throw new UnsupportedOperationException("DON'T INSTANTIATE FONTLOADER");
  }

  /**
   * load the fonts from resources into the graphics environment.
   *
   * @post graphics environment has access to resource fonts as the font family
   * name.
   */
  public static void loadFonts() {
    URL italic = FontLoader.class.getResource("/fonts/NunitoSans-Italic.ttf");
    URL reg = FontLoader.class.getResource("/fonts/NunitoSans-Regular.ttf");
    URL mono = FontLoader.class.getResource("/fonts/Lilex.ttf");

    if (italic != null) {
      loadFont(italic);
    }
    if (reg != null) {
      loadFont(reg);
    }
    if (mono != null) {
      loadFont(mono);
    }
  }

  /**
   * load a specific font from file.
   *
   * @param path the font URL to be loaded from.
   * @post the graphics environment has access to the font.
   */
  public static void loadFont(URL path) {
    try {
      Font font = Font.createFont(Font.TRUETYPE_FONT, path.openStream());
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(font);
    } catch (IOException | FontFormatException e) {
      Setup.handleError(e);
    }

  }

}
