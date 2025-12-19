package ui;


import config.Setup;
import core.Grid;
import utilities.Renderable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/** a UI controller for all components, very expandable.
 * @author RohanSomani
 * @name UIController
 * @date 2025-12-17
 */
public class UIController extends JFrame {

    private final List<Renderable> elements = new ArrayList<>();

    /**
     * Initialize the window, with a title and basic grid.
     * @param grid grid to be used for initialization
     * @param title the title of the window.
     */
    public UIController(Grid grid, String title) {
        super(title);
        GridRenderer gr = new GridRenderer(grid);
        elements.add(gr);
        setup();
    }

    /**
     * initialize window, with title "Maze Game"
     * @param grid the grid to be used for initialization.
     */
    public UIController(Grid grid) {
        this(grid, "Maze Game");
    }

    /**
     * Set basic JFrame properties and all renderable elements to it.
     * @pre initialized ui controller, elements list is not empty.
     * @post JFrame properties initialized, all elements in JFrame, formatted JFrame.
     */
    private void setup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        for (Renderable r : elements) {
            add(r.getComponent()); //weird little work around.
            System.out.println(r);
        }
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        update();
    }

    /**
     * Calls update() with everything being refreshed.
     * @pre setup() has been called, JFrame initialized.
     * @post everything refreshes its rendering.
     */
    public void update() {
        update(Setup.ALL);
    }

    /**
     * Update everything with different specifications.
     * @pre setup() has been called, JFrame initialized.
     * @post elements refreshed depending on the code
     * @param code the code from {@link config.Setup} to be used.
     */
    @SuppressWarnings("unused")
    public void update(int code) {
        for (Renderable r : elements) {
            r.onUpdate();
        }
    }

}