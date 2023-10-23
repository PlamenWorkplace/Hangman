package org.cbl.hangman.components;

import java.awt.*;
import javax.swing.*;

/**
 * Where the text label is displayed.
 */
public class Text extends JLabel {

    private static final String[] START = {"TEST YOUR LUCK!", "MAKE YOUR FIRST GUESS!"};
    private static final String[] CORRECT = {"CORRECT!", "NICELY DONE!", "LUCKY YOU!"};
    private static final String[] WRONG = {"OOPS, SORRY!", "WRONG!", "TRY AGAIN!", "OUT OF LUCK?"};
    private static final String[] WON = {"YOU WON!", "VICTORY!"};
    private static final String[] LOST = {"HANGED!", "YOU LOST!", "LOSER!"};

    /**
     * Called once to set up the properties of JLabel.
     */
    public Text() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    }

    /**
     * Sets the label that displays the status of the game.
     */
    public void setup() {
        setText(START[(int) (Math.random() * START.length)]);
        setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        setForeground(new Color(249, 85, 0)); // Orange
    }

    public void setToWon() {
        setText(WON[(int) (Math.random() * WON.length)]);
    }

    public void setToLost() {
        setText(LOST[(int) (Math.random() * LOST.length)]);
    }

    public void setToCorrect() {
        setText(CORRECT[(int) (Math.random() * CORRECT.length)]);
    }

    public void setToWrong() {
        setText(WRONG[(int) (Math.random() * WRONG.length)]);
    }
}
