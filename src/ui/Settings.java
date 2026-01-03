package ui;

import config.Setup;
import ui.themes.VisualType;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author RohanSomani
 * @name Settings
 * @date 2026-01-02
 */
public class Settings extends JDialog {
    //BEFORE YOU WRITE ANYTHING ADD A DESCRIPTION!!

    final Window parent;

    /**
     * initialize a settings dialog with a dropdown for theme selection and other options (?)
     *
     * @param parent the parent of the dialog for it to be anchored to.
     */
    public Settings(Window parent) {
        super(parent, "Settings");

        this.parent = parent;
        setupDialog();

        String[] themes = Setup.getThemes();
        if (themes == null) {
            Setup.handleError(
                    new FileNotFoundException("No theme files!")
            );
            dispose();
            return;
        }
        System.out.println(Arrays.toString(themes));
        JComboBox<String> themeChooser = new JComboBox<>(themes);
        add(themeChooser);

    }

    private void setupDialog() {
        setAlwaysOnTop(true);
        setResizable(false);

        setLocationRelativeTo(parent);
        setBackground(Setup.getColor(VisualType.BACKGROUND));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

}