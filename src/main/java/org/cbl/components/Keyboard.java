package org.cbl.components;

import javax.swing.*;

public class Keyboard extends JPanel {

    // Button Columns
    private JButton[] firstColumn;
    private JButton[] secondColumn;
    private JButton[] thirdColumn;

    public Keyboard() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

}
