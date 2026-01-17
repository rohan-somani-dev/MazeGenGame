package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import config.Setup;

/**
 * @author RohanSomani
 * @date 2026-01-17
 * @name ComboBoxItem
 */
public class ComboBoxItem {

  public String name;
  public ImageIcon icon;

  public ComboBoxItem(String name, String path) {
    this.name = name;
    File file = new File(path);
    BufferedImage original; 
    try {
      original = ImageIO.read(file);
    } catch (IOException e) {
      Setup.handleError(e);
      return;
    }

    BufferedImage resized = ImageUtils.resizeImage(original, 100, 40);
    this.icon = new ImageIcon(resized); 
  }

}
