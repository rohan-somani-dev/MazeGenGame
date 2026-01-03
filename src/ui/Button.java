package ui;

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

    /**
     * initialize a button with a string to the icon and the color for it to be recolored to.
     *
     * @param iconPath the path to the icon to be set as the button label
     * @param newColor new color for it to be changed to.
     */
    public Button(String iconPath, Color newColor) {
        this(new ImageIcon(iconPath), newColor);
    }

    /**
     * initialize a button with an icon and a new color
     *
     * @param icon     the icon to be set as the button label and recolored
     * @param newColor the color for the icon to be recolored to
     */
    public Button(ImageIcon icon, Color newColor) {
        this(ImageUtils.recolorIcon(icon, newColor));
    }

    /**
     * initialize a button with an icon as its label.
     *
     * @param icon the icon to be set as its label.
     */
    public Button(ImageIcon icon) {
        super(icon);

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

    private void resetIcon(ImageIcon icon) {
        int minDimension = Math.min(getWidth(), getHeight());

        Image scaled = icon.getImage().getScaledInstance(minDimension, minDimension, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaled));

    }

    /**
     * function to be called to repaint, can implement other code but mostly used to call {@code this.repaint()}.
     *
     * @pre ready to be drawn.
     * @post update has taken place, everything that needed to be drawn is drawn.
     */
    @Override
    public void onUpdate() {
        resetIcon((ImageIcon) getIcon());
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