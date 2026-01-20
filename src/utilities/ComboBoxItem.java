package utilities;

import config.Setup;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * a holder for combo box items, containing the name of the theme, and the path of the picture.
 *
 * @author RohanSomani
 * @date 2026-01-17
 * @name ComboBoxItem
 */
public class ComboBoxItem {

  public final String name;
  public ImageIcon icon;

  /**
   * A holder for ComboBoxItems, primarily used for themes.
   *
   * @param name the name of the item.
   * @param path the path of the image file to be used.
   */
  public ComboBoxItem(String name, String path) {
    this.name = name;
    BufferedImage original;
    try (InputStream is = getClass().getResourceAsStream(path)) { // fancy autocloseable
      if (is == null) {
        throw new IOException();
      }
      original = ImageIO.read(is);
    } catch (IOException e) {
      Setup.handleError(e);
      return;
    }

    BufferedImage resized = ImageUtils.resizeImage(original, 100, 40);
    this.icon = new ImageIcon(resized);
  }

}
