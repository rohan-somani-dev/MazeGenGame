package utilities;

import config.Setup;
import ui.themes.VisualType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

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

    /**
     * a helper method to help create themes from the console.
     *
     * @param input    scanner to be used.
     * @param fileName the filename inside resources/themes/ to be used. omitting png
     */
    @SuppressWarnings("unused")
    public static void writeTheme(Scanner input, String fileName) {
        String pathToWriteTo = "resources/themes/" + fileName + ".png";
        int numItems = VisualType.values().length;

        int[] colors = new int[numItems];

        for (int i = 0; i < numItems; i++) {
            System.out.printf("enter theme color #%x: ", (i + 1));
            String color = input.nextLine().replace("0x", "").replace("#", "");
            colors[i] = Integer.parseInt(color, 16);
        }

        System.out.println(Arrays.toString(colors));

        int width = numItems * Setup.BAR_SIZE;

        outputPhoto(pathToWriteTo, colors, width);
    }

    private static void outputPhoto(String filePath, int[] colors, int width) {

        File outputFile = new File(filePath);
        System.out.println(outputFile);

        int height = Setup.BAR_SIZE * 4;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();

        for (int i = 0; i < colors.length; i++) {
            int x = i * Setup.BAR_SIZE;
            g.setColor(new Color(colors[i]));
            g.fillRect(x, 0, Setup.BAR_SIZE, height);
        }

        g.dispose();

        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("I CAME, I SAW, I WROTE");
        } catch (IOException e) {
            Setup.handleError(e);
        }
        System.out.println(filePath);
        ProcessBuilder builder = new ProcessBuilder("explorer.exe", outputFile.getAbsolutePath());

        try {
            builder.start();
        } catch (Exception e) {
            Setup.handleError(e);
        }

    }

}