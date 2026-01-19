package ui;

import config.Setup;
import core.Grid;
import core.TimerController;
import utilities.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * a UI controller for all components, very expandable.
 *
 * @author RohanSomani
 * @name UIController
 * @date 2025-12-17
 */
public class UIController extends JFrame {

    final Renderable menuLeft;
    final GridRenderer gridRenderer;
    final Renderable menuRight;

    boolean fullscreen = true;

    /**
     * Initialize the window, with a title and basic grid.
     *
     * @param grid  grid to be used for initialization
     * @param title the title of the window.
     */
    public UIController(Grid grid, String title) {
        super(title);
        gridRenderer = new GridRenderer(grid);


        menuLeft = new LeftMenu(grid.timerController);
        menuRight = new Menu(this);

        setup();
    }

    /**
     * initialize window, with title "Maze Game"
     *
     * @param grid the grid to be used for initialization.
     */
    public UIController(Grid grid) {
        this(grid, "Maze Game");
    }

    /**
     * close the application and end process thanks to {@code JFrame.EXIT_ON_CLOSE}
     *
     * @pre JFrame initialized.
     * @post window and process are terminated, no more code can be run.
     */
    public void exit() {
        // TODO get rid of all threads by keeping a central thread manager, maybe a
        // class or just in GameController.
        this.dispose();
    }

    /**
     * Set basic JFrame properties and all renderable elements to it.
     *
     * @pre initialized ui controller, elements list is not empty.
     * @post JFrame properties initialized, all elements in JFrame, formatted
     *         JFrame.
     */
    private void setup() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent w) {
                exit();
            }
        });

        setLayout(new GridBagLayout());

        GridBagConstraints cLeft = new GridBagConstraints();
        cLeft.gridx = 0;
        cLeft.gridy = 0;
        cLeft.weightx = 1;
        cLeft.weighty = 1;
        cLeft.fill = GridBagConstraints.BOTH;
        this.add(menuLeft.getComponent(), cLeft);

        GridBagConstraints cCenter = new GridBagConstraints();
        cCenter.gridx = 1;
        cCenter.gridy = 0;
        cCenter.weightx = 2;
        cCenter.weighty = 1;
        cCenter.fill = GridBagConstraints.BOTH;
        this.add(gridRenderer.getComponent(), cCenter);

        GridBagConstraints cRight = new GridBagConstraints();
        cRight.gridx = 2;
        cRight.gridy = 0;
        cRight.weightx = 1;
        cRight.weighty = 1;
        cRight.fill = GridBagConstraints.BOTH;
        this.add(menuRight.getComponent(), cRight);

        setSize(new Dimension(Setup.WINDOW_WIDTH, Setup.MAZE_SIZE));
        setLocationRelativeTo(null);
        enterFullscreen();
        setVisible(true);
        setFocusable(true);

        setAlwaysOnTop(true);
        toFront();
        requestFocus();
        setAlwaysOnTop(false); //get it to appear on top on start.

        update();
    }

    public void toggleFullscreen() {
        if (fullscreen) exitFullscreen();
        else enterFullscreen();
        fullscreen = !fullscreen;
    }

    private void enterFullscreen() {
        dispose(); //you can't set the decoration on visible windows so this is necessary
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void exitFullscreen() {
        dispose(); //same as above
        setUndecorated(false);
        setExtendedState(JFrame.NORMAL);

        setSize(new Dimension(
                Setup.SCREEN_SIZE.width - 300,
                Setup.SCREEN_SIZE.height - 200
        ));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Calls update() with everything being refreshed.
     *
     * @pre setup() has been called, JFrame initialized.
     * @post everything refreshes its rendering.
     */
    public void update() {
        update(Setup.ALL);
    }

    /**
     * Update everything with different specifications.
     *
     * @param code the code from {@link config.Setup} to be used.
     * @pre setup() has been called, JFrame initialized.
     * @post elements refreshed depending on the code
     */
    public void update(int code) {
        switch (code) {
            case Setup.MAZE_UPDATE:
                gridRenderer.onUpdate();
                break;
            case Setup.ALL:
            default:
                menuLeft.onUpdate();
                gridRenderer.onUpdate();
                menuRight.onUpdate();
        }

    }

}
