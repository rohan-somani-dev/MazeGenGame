package config.themes;

import java.awt.*;

/**
 * @author RohanSomani
 * @name ThemeHolder
 * @date 2025-12-20
 */
@SuppressWarnings("unused")
public class ThemeHolder {

    public static final Theme DARK = new Theme(
            VisualType.WALL, new Color(0xFFFFFF),
            VisualType.TARGET, new Color(0xCC758C),
            VisualType.BACKGROUND, new Color(0x3C1620),
            VisualType.VISITED, new Color(0x3C1620),
            VisualType.START, new Color(0x6266C2),
            VisualType.END, new Color(0xA673B6),
            VisualType.PATH, new Color(0x824343),
            VisualType.PLAYER, new Color(0xF5B2B2),
            VisualType.DEBUG, Color.RED
    );

    public static final Theme LIGHT = new Theme(
            VisualType.WALL, new Color(0x3C1620),
            VisualType.TARGET, new Color(0xC85C7A),
            VisualType.BACKGROUND, new Color(0xFFDAE2),
            VisualType.VISITED, new Color(0xFFDAE2),
            VisualType.START, new Color(0x6B6FD4),
            VisualType.END, new Color(0xB985C8),
            VisualType.PATH, new Color(0xC78C8C),
            VisualType.PLAYER, new Color(0x9E3B4F),
            VisualType.DEBUG, Color.RED
    );

    public static final Theme PASTEL = new Theme(
            VisualType.WALL, new Color(0xF7F7F7),
            VisualType.TARGET, new Color(0xF2A7C6),
            VisualType.BACKGROUND, new Color(0xD6E4F0),
            VisualType.VISITED, new Color(0xD6E4F0),
            VisualType.START, new Color(0xB8C0FF),
            VisualType.END, new Color(0xDDBDD5),
            VisualType.PATH, new Color(0xE6B8B8),
            VisualType.PLAYER, new Color(0xF6C1CC),
            VisualType.DEBUG, new Color(0xFF00000)
    );
    public static final Theme PINK = new Theme(
            VisualType.WALL, new Color(0xF6D6DD),
            VisualType.TARGET, new Color(0xE89AB3),
            VisualType.BACKGROUND, new Color(0xFBF2F5),
            VisualType.VISITED, new Color(0xFBF2F5),
            VisualType.START, new Color(0xC04DD8),
            VisualType.END, new Color(0x8E3AAE),
            VisualType.PATH, new Color(0xE5A7B6),
            VisualType.PLAYER, new Color(0xFFF6FA).darker(),
            VisualType.DEBUG, new Color(0xD32F2F)
    );

    private ThemeHolder() {
    } //prevent instantiation

}