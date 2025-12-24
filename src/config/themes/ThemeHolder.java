package config.themes;

import java.awt.*;

/**
 * @author RohanSomani
 * @name ThemeHolder
 * @date 2025-12-20
 */
@SuppressWarnings("unused")
public class ThemeHolder {

    public static final Theme DARK;
    public static final Theme LIGHT;

    static { //this runs once when ThemeHolder is first loaded (currently in Setup.theme's initialization).
        Object[] darkTheme = new Object[]{
                ThemeColor.WALL, new Color(0xFFFFFF),
                ThemeColor.TARGET, new Color(0xCC758C),
                ThemeColor.BACKGROUND, new Color(0x3C1620),
                ThemeColor.VISITED, new Color(0x02182B),
                ThemeColor.START, new Color(0x6266C2),
                ThemeColor.END, new Color(0xA673B6),
                ThemeColor.PATH, new Color(0x824343),
                ThemeColor.PLAYER, new Color(0xF5B2B2),
                ThemeColor.DEBUG, Color.RED};
        DARK = new Theme(Theme.getMap(darkTheme));

        //TODO: maybe untie light and dark, light lowkey ugly rn.
        Object[] lightTheme = new Object[darkTheme.length];
        for (int i = 0; i < darkTheme.length; i++){
            if (darkTheme[i] instanceof Color) {
                Color c = (Color) darkTheme[i];
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                lightTheme[i] = new Color(255 - r, 255 - g, 255 - b);

            } else {
                lightTheme[i] = darkTheme[i];
            }
        }
        LIGHT = new Theme(Theme.getMap(lightTheme));

    }




    public static final Theme PASTEL = new Theme(
            Theme.getMap(
                    new Object[]{
                            ThemeColor.WALL, new Color(0xF7F7F7),
                            ThemeColor.TARGET, new Color(0xF2A7C6),
                            ThemeColor.BACKGROUND, new Color(0xD6E4F0),
                            ThemeColor.VISITED, new Color(0xD6E4F0),
                            ThemeColor.START, new Color(0xB8C0FF),
                            ThemeColor.END, new Color(0xDDBDD5),
                            ThemeColor.PATH, new Color(0xE6B8B8),
                            ThemeColor.PLAYER, new Color(0xF6C1CC),
                            ThemeColor.DEBUG, new Color(0xFF00000)
                    }
            )
    );

    public static final Theme PINK = new Theme(
            Theme.getMap(
                    new Object[]{
                            ThemeColor.WALL, new Color(0xF6D6DD),
                            ThemeColor.TARGET, new Color(0xE89AB3),
                            ThemeColor.BACKGROUND, new Color(0xFBF2F5),
                            ThemeColor.VISITED, new Color(0xC9A3B6),
                            ThemeColor.START, new Color(0xC04DD8),
                            ThemeColor.END, new Color(0x8E3AAE),
                            ThemeColor.PATH, new Color(0xE5A7B6),
                            ThemeColor.PLAYER, new Color(0xFFF6FA).darker(),
                            ThemeColor.DEBUG, new Color(0xD32F2F)
                    }
            )
    );

    private ThemeHolder() {
    } //prevent instantiation

}