package org.cbl.hangman.components;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import javax.swing.*;

/**
 * Controller Class.
 */
public class Game extends JFrame {

    private final Word word;
    private final Drawing drawing;
    private final Text textLabel;
    private final Keyboard keyboard;

    /**
     * Initializes JComponents of the UI.
     *
     * @throws IOException in case of no response from random word API.
     */
    public Game() throws IOException {
        setEnvironment();

        // Adds top components panel
        JPanel topComponents = new JPanel(new GridLayout(1, 2));
        topComponents.setOpaque(false);
        add(topComponents);

        // Adds the word to top panel
        word = new Word(new GridBagLayout());
        topComponents.add(word);

        // Adds hangman to top panel
        drawing = new Drawing();
        topComponents.add(drawing);

        // Adds text label above keyboard
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(1000, 20));
        panel.setOpaque(false);
        textLabel = new Text();
        panel.add(textLabel, BorderLayout.SOUTH);
        add(panel);

        // Adds keyboard
        keyboard = new Keyboard();
        add(keyboard);

        newGame();
        setVisible(true);
    }

    /**
     * Sets up a new game.
     *
     * @throws IOException if no response from random word API
     */
    public void newGame() throws IOException {
        word.setup();
        drawing.setup();
        textLabel.setup();
        keyboard.setup(this);
    }

    /**
     * Ends the game in a way depending on if the game is won or lost.
     *
     * @param isWon specifies if the game is won or lost.
     */
    public void endGame(boolean isWon) {
        textLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        keyboard.doDisable();
        if (isWon) {
            textLabel.setToWon();
            new Timer().schedule(new Dialog(this, "Congratulations, you won!"), 1500);
        } else {
            textLabel.setToLost();
            new Timer().schedule(new Dialog(this, (String.format(
                    "The word was %s. Try again?",
                    new String(word.getWordCharSeq())
            ))
            ), 1500);
        }
    }

    /**
     * Sets the layout of the game itself.
     *
     */
    private void setEnvironment() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Game.this.setWindowClosing();
            }
        });
        /* https://wallpaperset.com/w/full/5/6/5/241724.jpg */
        setContentPane(new JLabel(new ImageIcon(
                new ImageIcon(Objects.requireNonNull(
                        getClass().getClassLoader().getResource("background.jpeg")
                )).getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH)
        )));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setLook();
        setSize(650, 500);
        setTitle("Hangman");
        setLocation(320, 80);
        /* https://cdn3.iconfinder.com/data/icons/brain-games/1042/Hangman-Game.png */
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("rope.png")
        ));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Sets the behavior of the window closing event.
     *
     */
    private void setWindowClosing() {
        Object[] options = {"Exit", "Cancel"};

        /* https://www.vecteezy.com/free-png/blob */
        int quit = JOptionPane.showOptionDialog(
                null,
                "Your progress will be lost.",
                "Exit?",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon(
                        new ImageIcon(Objects.requireNonNull(
                                getClass().getClassLoader().getResource("warning.png")))
                                .getImage().getScaledInstance(50, 50,  Image.SCALE_SMOOTH)),
                options,
                options[1]);

        if (quit == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    private void setLook() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public Text getTextLabel() {
        return textLabel;
    }

    public Word getWord() {
        return word;
    }

}
