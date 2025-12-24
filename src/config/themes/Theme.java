package config.themes;

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

    private final EnumMap<ThemeColor, Color> colors; // thanks! https://www.baeldung.com/java-enum-map

    /**
     * Initialize theme with given colors.
     * @param colors the color map to be used, recommended to be from using <code>this.getMap()</code>
     */
    public Theme(EnumMap<ThemeColor, Color> colors){
        this.colors = colors;
    }

    /**
     * get the color of a given element type.
     * @pre this.colors is initialized.
     * @post color is returned, defaulting to pure white.
     * @param themeColor the type of element to get the color of.
     * @return a nonnull color from themes.
     */
    public Color get(ThemeColor themeColor){
        return colors.getOrDefault(themeColor, new Color(0xffffff));
    }

    /**
     * Get a <ThemeColor, Color> formatted map, based on entries.
     * @pre none, static method.
     * @post a returned map that can be used to initialize a theme.
     * @param entries an array formatted [ThemeColor, Color, ThemeColor, Color, ...], to the length of theme colors.
     * @return theme colors map inputtable into Theme constructor.
     */
    public static EnumMap<ThemeColor, Color> getMap(Object[] entries){
        EnumMap<ThemeColor, Color> out = new EnumMap<>(ThemeColor.class);
        for (int i = 0; i < entries.length; i+=2){
            out.put((ThemeColor) entries[i], (Color) entries[i+1]);
        }
        return out;
    }

}
