package org.cbl.components;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Timer;

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
    private final JLabel hangman;
    private final JPanel wordPanel;
    private JLabel[] wordToGuess;
    private char[] wordCharSeq;

    // Label above keyboard
    private final JLabel textLabel;

    // Keyboard
    private final JPanel keyboard;

    // Labels
    private static final String[] START = {"TEST YOUR LUCK!", "MAKE YOUR FIRST GUESS!"};
    private static final String[] CORRECT = {"CORRECT!", "NICELY DONE!", "LUCKY YOU!"};
    private static final String[] WRONG = {"OOPS, SORRY!", "WRONG!", "TRY AGAIN!", "OUT OF LUCK?"};
    private static final String[] WON = {"YOU WON!", "VICTORY!"};
    private static final String[] LOST = {"HANGED!", "YOU LOST!", "LOSER!"};

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

        // Adds top components panel
        JPanel topComponents = new JPanel(new GridLayout(1, 2));
        topComponents.setOpaque(false);
        add(topComponents);

        // Adds the word to top panel
        wordPanel = new JPanel(new GridBagLayout());
        wordPanel.setOpaque(false);
        topComponents.add(wordPanel);

        // Adds hangman to top panel
        hangman = new JLabel();
        hangman.setHorizontalAlignment(SwingConstants.CENTER);
        topComponents.add(hangman);

        // Adds label above keyboard
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(1000, 20));
        panel.setOpaque(false);
        textLabel = new JLabel();
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(textLabel, BorderLayout.SOUTH);
        add(panel);

        // Adds keyboard
        keyboard = new JPanel();
        keyboard.setOpaque(false);
        keyboard.setLayout(new BoxLayout(keyboard, BoxLayout.PAGE_AXIS));
        add(keyboard);

        newGame();
        setVisible(true);
    }

    /**
     * Sets up a new game
     * @throws IOException if no response from random word API
     */
    public void newGame() throws IOException {
        setTopComponents();
        setLabel();
        setKeyboard();
    }

    /**
     * Sets the top components of the Game
     */
    private void setTopComponents() throws IOException {
        // Sets up word to guess
        wordPanel.removeAll();
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
            wordPanel.add(wordToGuess[i]);
        }

        // Sets up hangman drawing
        setHangmenIcons();
        hangman.setIcon(hangingMen.next());
    }

    /**
     * Sets the label that displays the status of the game
     */
    private void setLabel() {
        textLabel.setText(START[(int)(Math.random() * START.length)]);
        textLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        textLabel.setForeground(new Color(249, 85, 0)); // Orange
    }

    /**
     * Sets the keyboard before every new game.
     */
    private void setKeyboard() {
        keyboard.removeAll();
        keyboard.add(fillButtonRow(new char[]{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'}));
        keyboard.add(fillButtonRow(new char[]{'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'}));
        keyboard.add(fillButtonRow(new char[]{'Z', 'X', 'C', 'V', 'B', 'N', 'M'}));
    }

    /**
     * Fills the specified row of the keyboard with the corresponding letters
     * @param letterRow letters to add to the buttons
     * @return the newly created buttons
     */
    private JPanel fillButtonRow(char[] letterRow) {
        int i;
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setOpaque(false);
        JButton[] buttonRow = new JButton[letterRow.length];
        for (i = 0; i < letterRow.length; i++)
        {
            buttonRow[i] = new JButton(String.valueOf(letterRow[i]));
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
        button.setFont(new Font("Arial", Font.PLAIN, 18));

        char letter = button.getText().charAt(0);
        boolean isFound = false;

        // Searches for a matching letter in word
        for (int i = 0; i < wordCharSeq.length; i++) {
            if (wordCharSeq[i] == letter) {
                isFound = true;
                wordToGuess[i].setText(String.valueOf(letter));
            }
        }

        if(!isFound) {
            button.setForeground(new Color(255, 91, 91));
            textLabel.setForeground(new Color(249, 85, 0));
            hangman.setIcon(hangingMen.next());

            if (!hangingMen.hasNext()) {
                endGame(false);
            }
            else {
                textLabel.setText(WRONG[(int)(Math.random() * WRONG.length)]);
            }

        } else {
            button.setForeground(new Color(148, 206, 48));
            textLabel.setForeground(new Color(115, 160, 38));
            boolean isWordRevealed = true;

            for (JLabel toGuess : wordToGuess) {
                if (Objects.equals(toGuess.getText(), "_")) {
                    isWordRevealed = false;
                    break;
                }
            }

            if (isWordRevealed) {
                endGame(true);
            } else {
                textLabel.setText(CORRECT[(int)(Math.random() * CORRECT.length)]);
            }


        }

    }

    /**
     * Ends the game in a way depending on if the game is won or lost;
     * @param isWon specifies if the game is won or lost.
     */
    private void endGame(boolean isWon) {
        textLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        disableKeyboard();
        if (isWon) {
            textLabel.setText(WON[(int)(Math.random() * WON.length)]);
            new Timer().schedule(new Dialog(this, "Congratulations, you won!"), 1500);
        } else {
            textLabel.setText(LOST[(int)(Math.random() * LOST.length)]);
            new Timer().schedule(new Dialog(
                    this,
                    (String.format("The word was %s. Try again?", new String(wordCharSeq)))
            ), 1500);
        }
    }

    private void disableKeyboard() {
        Arrays.stream(first).forEach(b -> {
            b.setContentAreaFilled(false);
            b.setEnabled(false);
        });

        Arrays.stream(second).forEach(b -> {
            b.setContentAreaFilled(false);
            b.setEnabled(false);
        });

        Arrays.stream(third).forEach(b -> {
            b.setContentAreaFilled(false);
            b.setEnabled(false);
        });
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
//        setHangmenIcons();

        warning = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("warning.png")
        ));

    }

}
