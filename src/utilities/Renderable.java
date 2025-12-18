package utilities;

import javax.swing.*;

public interface Renderable {

    void initUI();

    void onUpdate();

    JComponent getComponent();
}
