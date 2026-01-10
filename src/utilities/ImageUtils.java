package utilities;

import config.Setup;
import ui.themes.VisualType;

import javax.imageio.ImageIO;
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
     * @param image    the image to be recolored
     * @param newColor the new color for the icon to be set to
     * @return the recolored icon.
     */
    public static BufferedImage recolorImage(BufferedImage image, Color newColor) {

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage recolored = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = Setup.prepareGraphics(recolored.createGraphics());

        g2.setColor(newColor);
        g2.fillRect(0, 0, width, height);

        g2.setComposite(AlphaComposite.DstAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return recolored;
    }

    @SuppressWarnings("ExtractMethodRecommender")
    public static BufferedImage tint(BufferedImage image, Color tintColor) {
//        BufferedImage tinted = new BufferedImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage tinted = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int current = image.getRGB(x, y);

                /*
                 ints in java are 32 bits (4 bytes). colors returned from get rgb return a 32 bit int
                 AAAAAAAA RRRRRRRR GGGGGGGG BBBBBBBB
                 the first bit shift (>>24) moves the first byte to the least significant position,
                 allowing us to mask it with 0xFF removing any bytes preceding it, isolating the channel
                 which is in this case alpha.
                 repeat for all other shifts with 16, 8,
                 and finally 0, because the blue channel is already in the least significant bit so no shifting is needed.
                 note that we still mask, however, because otherwise int b would be the whole 32 bits which is not what we want.
                */

                int a = (current >> 24) & 0xFF; //most significant byte
                int r = (current >> 16) & 0xFF;
                int g = (current >> 8) & 0xFF;
                int b = current & 0xFF; // least significant byte

                int newR = (r * tintColor.getRed()) / 255;
                int newG = (g * tintColor.getGreen()) / 255;
                int newB = (b * tintColor.getBlue()) / 255;

                /*
                 finally, after doing the recalculations
                 we can shift them back int to the new 32 bit int the same way we got them out.
                 notice we're using bitwise | (or) instead of & (and) so that the channels get added
                 rather than masking each other.
                */

                int out =
                        getColorInt(a, newR, newG, newB);
                tinted.setRGB(x, y, out);

                //tldr: fancy bit shifting, image multiplication, leave us with a tinted image.
                //also, i had to google this, but i hope this explanation was an indication of my understanding of the code.
            }
        }

        return tinted;
    }

    private static int getColorInt(int alpha, int newR, int newG, int newB) {
        return (alpha << 24) |
                (newR << 16) |
                (newG << 8) |
                newB;
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

        Graphics2D g = Setup.prepareGraphics(image.createGraphics());

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