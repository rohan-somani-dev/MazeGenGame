package ui;

import config.Setup;
import ui.themes.VisualType;
import utilities.ImageUtils;
import utilities.Renderable;

import javax.swing.*;
import java.awt.*;

/**
 * @author RohanSomani
 * @name Button
 * @date 2026-01-02
 */
public class Button extends JButton implements Renderable {

    private final VisualType themeColorType;

    /**
     * initialize a button with a string to the icon and the color for it to be recolored to.
     *
     * @param iconPath the path to the icon to be set as the button label
     * @param visualType new color type for it to be changed to.
     */
    public Button(String iconPath, VisualType visualType) {
        this(new ImageIcon(iconPath), visualType);
    }


    /**
     * initialize a button with an icon as its label.
     *
     * @param icon the icon to be set as its label.
     */
    private Button(ImageIcon icon, VisualType visualType) {
        super(icon);
        this.themeColorType = visualType;

        setFocusable(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setAlignmentX(RIGHT_ALIGNMENT);

        Dimension d = new Dimension(96, 96);
        setMinimumSize(d);
        setPreferredSize(d);
        setMaximumSize(d);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void resetIcon() {
        int minDimension = Math.min(getWidth(), getHeight());

        Image scaled =
                ((ImageIcon) getIcon())
                        .getImage().
                        getScaledInstance(minDimension, minDimension, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaled));
        recolor();

    }

    /**
     * recolor the current icon.
     *
     * @post icon is recolored to match the (possibly) new themes wallColor
     *         todo maybe think about only calling when theme is updated? seems to not impact performance a whole lot.
     */
    public void recolor() {
        ImageIcon recolored = ImageUtils.recolorIcon((ImageIcon) getIcon(), Setup.getColor(this.themeColorType));
        setIcon(recolored);
    }

    /**
     * function to be called to repaint, can implement other code but mostly used to call {@code this.repaint()}.
     *
     * @pre ready to be drawn.
     * @post update has taken place, everything that needed to be drawn is drawn.
     */
    @Override
    public void onUpdate() {
        resetIcon();
    }

    /**
     * get the underlying component of self.
     *
     * @return the component part of the implementing object.
     */
    @Override
    public JComponent getComponent() {
        return this;
    }
}