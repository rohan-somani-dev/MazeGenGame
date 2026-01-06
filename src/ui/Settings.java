package ui;

import config.Setup;
import ui.styled.ThemeDropDown;
import ui.themes.VisualType;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Set;

//TODO split settings into ui and logic

/**
 * @author RohanSomani
 * @name Settings
 * @date 2026-01-02
 */
public class Settings extends JDialog {
    //BEFORE YOU WRITE ANYTHING ADD A DESCRIPTION!!

    final Window parent;

    final Runnable requestUpdate;

    /**
     * initialize a settings dialog with a dropdown for theme selection and other options (?)
     * TODO fix styling cuz wtf is going on
     * @param parent the parent of the dialog for it to be anchored to.
     * @param requestUpdate the function to call when an update has been made.
     */
    public Settings(Window parent, Runnable requestUpdate) {
        super(parent, "Settings");

        this.parent = parent;
        this.requestUpdate = requestUpdate;
        setupDialog();
        ThemeDropDown themeChooser = new ThemeDropDown(requestUpdate);
        add(themeChooser);
    }


    private void setupDialog() {
        setAlwaysOnTop(true);
        setResizable(true);
        setPreferredSize(new Dimension(400, 400));
        setLocationRelativeTo(parent);
        setBackground(Setup.getColor(VisualType.BACKGROUND));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

}