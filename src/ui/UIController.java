package ui;
/*
 * Author: RohanSomani
 * Name: UIController
 * Date: 2025-12-17
 */

import config.Setup;
import core.Grid;
import utilities.Renderable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UIController extends JFrame {

    private List<Renderable> elements = new ArrayList<>();

    public UIController(Grid grid, String title) {
        super(title);
        GridRenderer gr = new GridRenderer(grid);
        elements.add(gr);
        setup();
    }

    public UIController(Grid grid) {
        this(grid, "Maze Game");
    }

    private void setup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        for (Renderable r : elements) {
            r.initUI();
            add(r.getComponent()); //weird little work around.
            System.out.println(r);
        }
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        update();
    }

    public void update() {
        update(Setup.ALL);
    }

    public void update(int code) {
        for (Renderable r : elements) {
            r.onUpdate();
        }
    }

}