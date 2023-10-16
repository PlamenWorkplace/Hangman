package org.cbl.components;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * User Interface and Controller
 */
public class Game extends JFrame {

    // Button Columns
    private JButton[] first;
    private JButton[] second;
    private JButton[] third;

    // Icons
    private Iterator<Icon> hangingMen;
    private ImageIcon warning;

    // Top components
    private JLabel hangman;
    private JLabel[] wordToGuess;
    private char[] wordCharSeq;

    // Label above keyboard
    private JLabel textLabel;

    // Labels
    private static final String[] START = {"TEST YOUR LUCK!", "MAKE YOUR FIRST GUESS!"};
    private static final String[] CORRECT = {"CORRECT!", "NICELY DONE!", "LUCKY YOU!"};
    private static final String[] WRONG = {"OOPS, SORRY!", "WRONG!", "TRY AGAIN!", "OUT OF LUCK?"};
    private static final String[] WON = {"YOU WON!", "VICTORY!"};
    private static final String[] LOST = {"HANGED!", "YOU LOST!"};

    public Game() throws HeadlessException, IOException {
        this.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                        Object[] options = {"Exit", "Cancel"};

                                        int quit = JOptionPane.showOptionDialog(null, "Your progress will be lost.",
                                                "Exit?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, warning, options, options[1]);

                                        if (quit == JOptionPane.OK_OPTION) {
                                            System.exit(0);
                                        }
                                    }
                                }
        );
        setIcons();
        setContentPane(new JLabel(new ImageIcon(
                new ImageIcon(Objects.requireNonNull(
                        getClass().getClassLoader().getResource("background.jpeg")
                )).getImage().getScaledInstance(1280, 720,  java.awt.Image.SCALE_SMOOTH)
        )));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setLook();
        setSize(650, 500);
        setTitle("Hangman");
        setLocation(320, 80);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("rope.png")
        ));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        newGame();
        setVisible(true);
    }

    /**
     * Sets up a new game
     * @throws IOException if no response from random word API
     */
    private void newGame() throws IOException {
        setTopComponents();
        setLabel();
        setKeyboard();
        setVisible(true);
    }

    /**
     * Sets the top components of the Game
     */
    private void setTopComponents() throws IOException {
        JPanel topComponents = new JPanel(new GridLayout(1, 2));
        topComponents.setOpaque(false);

        // Hangman
        hangman = new JLabel(hangingMen.next(), SwingConstants.CENTER);
        topComponents.add(hangman);

        // Word to guess
        wordCharSeq = ((String) new ObjectMapper().readValue(new URL("https://random-word-api.herokuapp.com/word"), ArrayList.class).get(0)).toUpperCase().toCharArray();
        JPanel wordPanel = new JPanel(new GridBagLayout());
        wordPanel.setOpaque(false);
        wordToGuess = new JLabel[wordCharSeq.length];
        for (int i = 0; i < wordCharSeq.length; i++) {
            wordToGuess[i] = new JLabel("_");
            wordToGuess[i].setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            wordToGuess[i].setForeground(new Color(145, 35, 243)); // Purple
            wordToGuess[i].setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
            wordPanel.add(wordToGuess[i]);
        }
        topComponents.add(wordPanel);

        add(topComponents);
    }

    /**
     * Sets the label that displays the status of the game
     */
    private void setLabel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(1000, 20));
        panel.setOpaque(false);
        textLabel = new JLabel(START[(int)(Math.random() * START.length)], SwingConstants.CENTER);
        textLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        textLabel.setForeground(new Color(249, 85, 0)); // Orange
        textLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(textLabel, BorderLayout.SOUTH);
        add(panel);
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
            buttonRow[i].setForeground(Color.MAGENTA);
            buttonRow[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonRow[i].setMaximumSize(new Dimension(45, 45));
            buttonRow[i].setBackground(Color.YELLOW);
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

        button.setContentAreaFilled(false);
        button.setEnabled(false);

        char letter = button.getText().charAt(0);
        boolean isFound = false;

        for (int i = 0; i < wordCharSeq.length; i++) {
            if (wordCharSeq[i] == letter) {
                isFound = true;
                // handle
                button.setForeground(new Color(148, 206, 48)); // Green
                wordToGuess[i].setIcon(null);
                wordToGuess[i].setText(String.valueOf(letter));

            }
        }

        if(!isFound) {
            button.setForeground(new Color(255, 91, 91));
            hangman.setIcon(hangingMen.next());
            if (!hangingMen.hasNext()) {
                textLabel.setText(LOST[(int)(Math.random() * LOST.length)]);
                gameLost();
                return;
            }
            textLabel.setText(WRONG[(int)(Math.random() * WRONG.length)]);
            textLabel.setForeground(new Color(249, 85, 0));
        } else {
            textLabel.setText(CORRECT[(int)(Math.random() * CORRECT.length)]);
            textLabel.setForeground(new Color(115, 160, 38));
        }

    }

    private void endGame() {

    }

    private void gameLost() {
        endGame();

        JDialog dialog = new JDialog(this, Dialog.ModalityType.DOCUMENT_MODAL);
        JLabel message = new JLabel("Sorry, you lost this game. Better luck next time!", SwingConstants.CENTER);
        message.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        JPanel buttons = setDialogButtons(dialog);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(20, 10));
        container.add(message, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);

        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
                                     @Override
                                     public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                         dialog.dispose();
                                         try {
                                             newGame();
                                         } catch (IOException e) {
                                             throw new RuntimeException(e);
                                         }
                                     }
                                 }
        );

        dialog.setTitle("Game Lost");
        dialog.add(container);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JPanel setDialogButtons(JDialog dialog) {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2,2,0));

        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        JButton playAgain = new JButton("Play Again");
        playAgain.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));


        exit.addActionListener((ActionEvent e) -> System.exit(0));
        playAgain.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            try {
                newGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        buttons.add(exit);
        buttons.add(playAgain);
        return buttons;
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
     * Saves the hangman images in variables at application startup.
     */
    private void setHangmenIcons() {
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

        hangingMen = hangmanIcons.iterator();
    }

    /**
     * Saves the images in variables at application startup.
     */
    private void setIcons()
    {
        setHangmenIcons();

        warning = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("warning.png")
        ));

    }

}
