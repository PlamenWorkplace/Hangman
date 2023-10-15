package org.cbl.components;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static javax.swing.SwingConstants.CENTER;

/**
 * User Interface
 */
public class UI extends JFrame {

    // Button Columns
    private JButton[] first;
    private JButton[] second;
    private JButton[] third;

    // Icons
    private Icon transparent;
    private Icon hangman1;
    private Icon hangman2;
    private Icon hangman3;
    private Icon hangman4;
    private Icon hangman5;
    private Icon hangman6;
    private Icon hangman7;
    private Icon hangman8;
    private Icon hangman9;
    private Icon hangman10;
    private Icon underscore;

    // Top components
    private JLabel hangman;
    private JLabel[] wordToGuess;
    private char[] wordCharSeq;

    public UI() throws HeadlessException, IOException {
        setIcons();
        ImageIcon background = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("background.jpeg")));
        setContentPane(new JLabel(new ImageIcon(background.getImage().getScaledInstance(
                1280, 720,  java.awt.Image.SCALE_SMOOTH))));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setLook();
        setSize(650, 500);
        setTitle("Hangman");
        setLocation(320, 80);
        setTopComponents();
        setKeyboard();
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("rope.png")
        ));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//        pack();
        setVisible(true);
    }

    /**
     * Sets button listeners to all buttons
     * @param game Game object used for listening of any action
     */
    public void setButtonListeners(Game game)
    {
        addWindowListener(game);
    }

    /**
     * Sets the top components of the UI
     */
    private void setTopComponents() throws IOException {
        JPanel topComponents = new JPanel(new GridLayout());
        topComponents.setOpaque(false);

        // Hangman
        hangman = new JLabel(hangman10, CENTER);
        topComponents.add(hangman);

        // Word to guess
        wordCharSeq = ((String) new ObjectMapper().readValue(new URL("https://random-word-api.herokuapp.com/word"), ArrayList.class).get(0)).toCharArray();
        JPanel wordPanel = new JPanel(new GridBagLayout());
        wordPanel.setOpaque(false);
        wordToGuess = new JLabel[wordCharSeq.length];
        for (int i = 0; i < wordCharSeq.length; i++) {
            wordToGuess[i] = new JLabel(String.valueOf(wordCharSeq[i]));
            wordToGuess[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            wordToGuess[i].setForeground(Color.DARK_GRAY);
            wordToGuess[i].setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
//            wordToGuess[i].setIcon(underscore);
            wordPanel.add(wordToGuess[i]);
        }
        topComponents.add(wordPanel);

        add(topComponents);
    }

    /**
     * Sets look and feel
     */
    private void setLook()
    {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) { }
    }

    /**
     * Sets the keyboard before every new game.
     */
    private void setKeyboard() {
        JPanel keyboard = new JPanel();
        keyboard.setOpaque(false);
        keyboard.setLayout(new BoxLayout(keyboard, BoxLayout.PAGE_AXIS));

        keyboard.add(fillButtonRow(new String[]{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"}));
        keyboard.add(fillButtonRow(new String[]{"A", "S", "D", "F", "G", "H", "J", "K", "L"}));
        keyboard.add(fillButtonRow(new String[]{"Z", "X", "C", "V", "B", "N", "M"}));

        add(keyboard);
    }

    /**
     * Fills the specified row of the keyboard with the corresponding letters
     * @param letterRow letters to add to the buttons
     * @return the newly created buttons
     */
    private JPanel fillButtonRow(String[] letterRow) {
        int i;
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setOpaque(false);
        JButton[] buttonRow = new JButton[letterRow.length];
        for(i = 0; i < letterRow.length; ++i)
        {
            buttonRow[i] = new JButton(letterRow[i]);
            buttonRow[i].setFont(new Font("Arial", Font.BOLD, 18));
            buttonRow[i].setForeground(Color.GRAY);
            buttonRow[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonRow[i].setMaximumSize(new Dimension(40, 40));
            buttonRow[i].addActionListener(this::keyPressed);
            buttonPane.add(buttonRow[i]);
        }
        if (i == 10) {
            first = buttonRow;
        }
        if (i == 9) {
            second = buttonRow;
        }
        if (i == 7) {
            third = buttonRow;
            buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
        }
        return buttonPane;
    }

    private void keyPressed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        char letter = button.getText().toLowerCase().charAt(0);
        boolean isFound = false;

        for (int i = 0; i < wordCharSeq.length; i++) {
            if (wordCharSeq[i] == letter) {
                isFound = true;
                // handle
                button.setForeground(Color.BLUE);
                wordToGuess[i].setVisible(true);

            }
        }

        if(!isFound) {
            button.setForeground(Color.RED);

        }
        button.setContentAreaFilled(false);
        button.setEnabled(false);



    }

    /**
     * Saves the images in variables at application startup.
     */
    private void setIcons()
    {
        ImageIcon icon;

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman1.png"))
        );
        hangman1 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman2.png"))
        );
        hangman2 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman3.png"))
        );
        hangman3 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman4.png"))
        );
        hangman4 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman5.png"))
        );
        hangman5 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman6.png"))
        );
        hangman6 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman7.png"))
        );
        hangman7 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman8.png"))
        );
        hangman8 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman9.png"))
        );
        hangman9 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("hangman10.png"))
        );
        hangman10 = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("transparent.png"))
        );
        transparent = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

        icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("underscore.png"))
        );
        underscore = new ImageIcon(icon.getImage().getScaledInstance(
                500, 250,  java.awt.Image.SCALE_SMOOTH)
        );

    }

}
