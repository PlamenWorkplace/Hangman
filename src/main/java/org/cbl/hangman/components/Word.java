package org.cbl.hangman.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The panel where word is displayed.
 */
public class Word extends JPanel {

    private JLabel[] wordToGuess;
    private char[] wordCharSeq;

    /**
     * Called once to set up the properties of JPanel.
     *
     * @param layout JPanel layout
     */
    public Word(LayoutManager layout) {
        super(layout);
        setOpaque(false);
    }

    /**
     * Called to set up the new word.
     *
     * @throws IOException in case of no response from random word API.
     */
    public void setup() throws IOException {
        removeAll();
        wordCharSeq = ((String) new ObjectMapper()
                .readValue(new URL("https://random-word-api.herokuapp.com/word"), ArrayList.class)
                .get(0))
                .toUpperCase()
                .toCharArray();
        wordToGuess = new JLabel[wordCharSeq.length];
        for (int i = 0; i < wordCharSeq.length; i++) {
            wordToGuess[i] = new JLabel("_");
            wordToGuess[i].setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            wordToGuess[i].setForeground(new Color(145, 35, 243)); // Purple
            wordToGuess[i].setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
            add(wordToGuess[i]);
        }
    }

    public JLabel[] getWordToGuess() {
        return wordToGuess;
    }

    public char[] getWordCharSeq() {
        return wordCharSeq;
    }
}
