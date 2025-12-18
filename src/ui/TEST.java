package ui;
/*
 * Author: RohanSomani
 * Name: TEST
 * Date: 2025-12-17
 */

import utilities.Renderable;

import javax.swing.*;
import java.awt.*;

public class TEST extends JPanel implements Renderable {

    @Override
    public void initUI() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setOpaque(true);
    }

    @Override
    public void onUpdate() {
        repaint();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(50, 50, 500, 300);
    }
}