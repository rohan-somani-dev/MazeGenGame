package utilities;

import config.themes.VisualType;

/**
 * The different cell states that a Node can take.
 */
public enum CellState {
    PLAYER(VisualType.PLAYER),
    PATH(VisualType.PATH),
    END(VisualType.END),
    START(VisualType.START),
    TARGET(VisualType.TARGET),
    VISITED(VisualType.VISITED),
    BACKGROUND(VisualType.BACKGROUND),
    @SuppressWarnings("unused")
    DEBUG(VisualType.DEBUG);

    private final VisualType visualType;

    CellState(VisualType visualType){
        this.visualType = visualType;
    }

    /**
     * get the theme color of the enum.
     * @return the visualType associated with this enum.
     */
    public VisualType getVisualType(){
        return this.visualType; // again, thanks https://youtu.be/wq9SJb8VeyM
    }
}
