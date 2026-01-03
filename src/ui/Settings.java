package ui;

import config.Setup;
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

    /**
     * initialize a settings dialog with a dropdown for theme selection and other options (?)
     * TODO fix styling cuz wtf is going on
     * @param parent the parent of the dialog for it to be anchored to.
     * @param requestUpdate the function to call when an update has been made.
     */
    public Settings(Window parent, Runnable requestUpdate) {
        super(parent, "Settings");

        this.parent = parent;
        setupDialog();
        Setup.regenThemes(false);
        Set<String> themes = Setup.getThemeNames();
        String[] names = new String[themes.size()];
        themes.toArray(names);
        if (themes.isEmpty()) {
            Setup.handleError(
                    new FileNotFoundException("No theme files!")
            );
            dispose();
            return;
        }
        JComboBox<String> themeChooser = new JComboBox<>(names);
        System.out.println(Setup.currentTheme.name);
        themeChooser.setSelectedItem(Setup.currentTheme.name);

        themeChooser.addActionListener((e) -> {
                    Setup.setTheme((String) themeChooser.getSelectedItem());
                    requestUpdate.run();
                }
        );
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