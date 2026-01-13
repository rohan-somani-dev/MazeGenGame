package ui.styled;

import config.Setup;
import ui.themes.Theme;

import javax.swing.*;

import java.awt.Dimension;
import java.util.HashMap;

/**
 * @author RohanSomani
 * @name ThemeDropDown
 * @date 2026-01-03
 */
public class ThemeDropDown extends JComboBox<String> {
  // we know that themes are always going to be passed as a collection (set, arr,
  // arraylist, etc.) of strings,
  // so we don't need to parameterize the type of the class, and just hardcode it
  // as string. not good for maintenance.
  // if i wanted to change it to hold themes instead, it's a whole other can of
  // worms.

  HashMap<String, Theme> themeMap;

  @SuppressWarnings("CanBeFinal")
  Runnable reqUpdate;

  /**
   *
   */
  public ThemeDropDown(Runnable reqUpdate) {
    super();

    themeMap = Setup.getThemes();
    this.reqUpdate = reqUpdate;

    addActionListener((e) -> {
      Setup.setTheme((String) getSelectedItem());
      reqUpdate.run();
    });

    initChoices();

    setMinimumSize(new Dimension(0, 40));
    setPreferredSize(new Dimension(400, 40));
    setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
  }

  private void initChoices() {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    for (String theme : themeMap.keySet()) {
      model.addElement(theme);
    }
    setModel(model);
  }

  // BEFORE YOU WRITE ANYTHING ADD A DESCRIPTION!!
}
