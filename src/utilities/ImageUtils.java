package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author RohanSomani
 * @name ImageUtils
 * @date 2026-01-02
 */
public class ImageUtils {

    /**
     * recolor an icon using its alpha mask to prevent hard lines from antialiasing.
     *
     * @param icon     the icon to be recolored
     * @param newColor the new color for the icon to be set to
     * @return the recolored icon.
     */
    public static ImageIcon recolorIcon(ImageIcon icon, Color newColor) {
        Image image = icon.getImage();

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage original = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D originalGraphics = original.createGraphics();
        originalGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //without this the recolored image would have sharp lines.
        originalGraphics.drawImage(image, 0, 0, null);
        originalGraphics.dispose();

        BufferedImage recolored = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = recolored.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(newColor);
        g2.fillRect(0, 0, width, height);

        g2.setComposite(AlphaComposite.DstAtop);
        g2.drawImage(original, 0, 0, null);

        g2.dispose();

        return new ImageIcon(recolored);

    }

}