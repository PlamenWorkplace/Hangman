package org.cbl.hangman.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.swing.*;

/**
 * Where the hangman pictures are displayed.
 */
public class Drawing extends JLabel {

    private Iterator<Icon> hangmenIterator;

    public Drawing() {
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Called to set up the new drawing iterator.
     */
    public void setup() {
        List<Icon> hangmanIcons = new ArrayList<>();
        ImageIcon icon;

        for (int i = 0; i <= 10; i++) {
            icon = new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("hangman" + i + ".png"))
            );
            hangmanIcons.add(new ImageIcon(icon.getImage().getScaledInstance(
                    220, 220,  java.awt.Image.SCALE_SMOOTH))
            );
        }
        hangmenIterator = hangmanIcons.iterator();

        setIcon(hangmenIterator.next());
    }

    public Iterator<Icon> getHangmenIterator() {
        return hangmenIterator;
    }

}
