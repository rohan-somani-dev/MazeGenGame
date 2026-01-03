package ui.themes;

import config.Setup;

import java.awt.*;
import java.util.EnumMap;

/**
 * Create a boilerplate theme class to be expanded.
 *
 * @author RohanSomani
 * @name Theme
 * @date 2025-12-20
 */
public class Theme {

    private final EnumMap<VisualType, Color> colors = new EnumMap<>(VisualType.class); // thanks! https://www.baeldung.com/java-enum-map

    /**
     * Initialize theme with given colors.
     *
     * @param entries the key, value pair for each entry, formatted as {@code (ThemeColor, Color, ThemeColor, Color, ...)}, similar to {@code Map.of()} in future java versions
     */
    public Theme(Object... entries) {
        if (entries.length % 2 != 0) {
            Setup.handleError(new IllegalArgumentException("Themes must be inputted as pairs"));
        }
        try {
            for (int i = 0; i < entries.length; i += 2) {
                colors.put(
                        (VisualType) entries[i],
                        (Color) entries[i + 1]
                );
            }

        } catch (ClassCastException e) {
            Setup.handleError(e);
        }

    }

    /**
     * get the color of a given element type.
     *
     * @param visualType the type of element to get the color of.
     * @return a nonnull color from themes.
     * @pre this.colors is initialized.
     * @post color is returned, defaulting to pure white.
     */
    public Color get(VisualType visualType) {
        return colors.getOrDefault(visualType, new Color(0xffffff));
    }

}
