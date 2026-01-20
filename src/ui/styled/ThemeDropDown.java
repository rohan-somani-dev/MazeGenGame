package ui.styled;

import config.Setup;
import ui.themes.Theme;
import ui.themes.VisualType;
import utilities.ComboBoxItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * a very hard theme drop down that shows pictures of all the available themes and updates the themes dynamically
 *
 * @author RohanSomani
 * @name ThemeDropDown
 * @date 2026-01-03
 */
public class ThemeDropDown extends JComboBox<ComboBoxItem> {
  // we don't need to parameterize the type of the class, because we know it will
  // always hold ComboBoxItems, so we can hard-code it as that.
  // writing it to show the images was a nightmare. it is 2am and i am dying.
  // https://shred.zone/cilla/page/336/setting-a-renderer-on-jcombobox.html
  // https://www.codejava.net/java-se/swing/create-custom-gui-for-jcombobox

  final HashMap<String, Theme> themeMap;

  /**
   *
   * @param reqUpdate the function to be run when the theme changes.
   */
  public ThemeDropDown(Runnable reqUpdate) {
    super();
    setFont(Setup.REGULAR);
    setBackground(Setup.getColor(VisualType.BACKGROUND));
    setForeground(Setup.getColor(VisualType.WALL));
    setBorder(BorderFactory.createLineBorder(Setup.getColor(VisualType.BACKGROUND).brighter(), 2));

    setMinimumSize(new Dimension(0, 40));
    setPreferredSize(new Dimension(400, 40));
    setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

    themeMap = Setup.getThemes();

    addActionListener((e) -> {
      ComboBoxItem curr = (ComboBoxItem) getSelectedItem();
      if (curr != null) {
        Setup.setTheme(curr.name);
      }
      reqUpdate.run();
    });

    initChoices();

    initRenderer();

    setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {

      @Override
      protected JButton createArrowButton() {
        JButton arrow = super.createArrowButton();
        arrow.setForeground(Setup.getColor(VisualType.WALL));
        arrow.setBackground(Setup.getColor(VisualType.BACKGROUND));
        arrow.setBorder(BorderFactory.createEmptyBorder());
        return arrow;
      }

      @Override
      public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        g.setColor(Setup.getColor(VisualType.BACKGROUND));
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
      }

    });

    initSelection();

  }

  private void initChoices() {
    DefaultComboBoxModel<ComboBoxItem> model = new DefaultComboBoxModel<>();
    for (String theme : themeMap.keySet()) {
      ComboBoxItem item = new ComboBoxItem(theme, "/themes/" + theme + ".png"); //FIXME
      model.addElement(item);
    }
    setModel(model);

  }

  private void initSelection() {

    String currentTheme = Setup.currentTheme.name;

    for (int i = 0; i < this.getItemCount(); i++) {
      ComboBoxItem item = getItemAt(i);
      if (item.name.equals(currentTheme)) {
        this.setSelectedIndex(i);
        return;
      }
    }
  }

  private void initRenderer() {
    setRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                    boolean cellHasFocus) {
        ComboBoxItem item = (ComboBoxItem) value;
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel(item.name);
        JLabel icon = new JLabel(item.icon);

        container.add(label, BorderLayout.WEST);
        container.add(icon, BorderLayout.EAST);

        Color background = Setup.getColor(VisualType.BACKGROUND);
        Color wall = Setup.getColor(VisualType.WALL);
        Color target = Setup.getColor(VisualType.TARGET);

        if (isSelected) {
          container.setBackground(target);
          label.setForeground(wall);
        } else {
          container.setBackground(background);
          label.setForeground(wall);
        }
        container.setOpaque(true);

        list.setSelectionBackground(background.darker());
        list.setSelectionForeground(wall);

        return container;
      }
    });
  }

  // BEFORE YOU WRITE ANYTHING ADD A DESCRIPTION!!
}
