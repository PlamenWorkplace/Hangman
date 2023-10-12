package org.cbl.components;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {

    public UI() throws HeadlessException {
        // Set frame
        setSize(650, 500);
        setTitle("Hangman");
        setLocation(320, 80);


        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("rope.png")
        ));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    public void setButtonListeners(Game game)
    {
        addWindowListener(game);
    }
}
