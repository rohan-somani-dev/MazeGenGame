package utilities;

/**
 * A listener for updates, should implement {@link utilities.Updater}
 */
public interface UpdateListener {

    /**
     * override this to actually implement it.
     *
     * @param code code from {@link config.Setup} to be used
     */
    void onUpdate(int code);
}
