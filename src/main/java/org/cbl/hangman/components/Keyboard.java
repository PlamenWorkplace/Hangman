package org.cbl.hangman.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.*;

/**
 * Where the keyboard is displayed.
 */
public class Keyboard extends JPanel {

    private JButton[] firstColumn;
    private JButton[] secondColumn;
    private JButton[] thirdColumn;

    /**
     * Called once to set up the properties of JPanel.
     */
    public Keyboard() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    /**
     * Sets the keyboard before every new game.
     */
    public void setup(Game game) {
        removeAll();
        add(fillButtonRow(new char[]{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'}, game));
        add(fillButtonRow(new char[]{'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'}, game));
        add(fillButtonRow(new char[]{'Z', 'X', 'C', 'V', 'B', 'N', 'M'}, game));
    }

    /**
     * Disables keyboard buttons.
     */
    public void doDisable() {
        Arrays.stream(firstColumn).forEach(b -> {
            b.setContentAreaFilled(false);
            b.setEnabled(false);
        });

        Arrays.stream(secondColumn).forEach(b -> {
            b.setContentAreaFilled(false);
            b.setEnabled(false);
        });

        Arrays.stream(thirdColumn).forEach(b -> {
            b.setContentAreaFilled(false);
            b.setEnabled(false);
        });
    }

    /**
     * Fills the specified row of the keyboard with the corresponding letters.
     *
     * @param letterRow letters to add to the buttons
     * @return the newly created buttons
     */
    private JPanel fillButtonRow(char[] letterRow, Game game) {
        int i;
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setOpaque(false);
        JButton[] buttonRow = new JButton[letterRow.length];
        for (i = 0; i < letterRow.length; i++) {
            buttonRow[i] = new JButton(String.valueOf(letterRow[i]));
            buttonRow[i].setFont(new Font("Arial", Font.BOLD, 18));
            buttonRow[i].setForeground(Color.MAGENTA);
            buttonRow[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonRow[i].setMaximumSize(new Dimension(45, 45));
            buttonRow[i].setBackground(Color.YELLOW);
            buttonRow[i].addActionListener(e -> keyPressed(e, game));
            buttonPane.add(buttonRow[i]);
        }
        if (i == 10) {
            firstColumn = buttonRow;
        }
        if (i == 9) {
            secondColumn = buttonRow;
        }
        if (i == 7) {
            thirdColumn = buttonRow;
            buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
        }
        return buttonPane;
    }

    /**
     * Sets the behavior of button when presses.
     *
     * @param e click event
     * @param game object to modify
     */
    private void keyPressed(ActionEvent e, Game game) {
        JButton button = (JButton) e.getSource();
        button.setContentAreaFilled(false);
        button.setEnabled(false);
        button.setFont(new Font("Arial", Font.PLAIN, 18));

        char letter = button.getText().charAt(0);
        boolean isFound = false;

        // Searches for a matching letter in word
        for (int i = 0; i < game.getWord().getWordCharSeq().length; i++) {
            if (game.getWord().getWordCharSeq()[i] == letter) {
                isFound = true;
                game.getWord().getWordToGuess()[i].setText(String.valueOf(letter));
            }
        }

        if (!isFound) {
            handleMistake(game, button);

        } else {
            handleCorrect(game, button);
        }
    }

    private static void handleCorrect(Game game, JButton button) {
        button.setForeground(new Color(148, 206, 48));
        game.getTextLabel().setForeground(new Color(115, 160, 38));
        boolean isWordRevealed = true;

        for (JLabel toGuess : game.getWord().getWordToGuess()) {
            if (Objects.equals(toGuess.getText(), "_")) {
                isWordRevealed = false;
                break;
            }
        }

        if (isWordRevealed) {
            game.endGame(true);
        } else {
            game.getTextLabel().setToCorrect();
        }
    }

    private void handleMistake(Game game, JButton button) {
        button.setForeground(new Color(255, 91, 91));
        game.getTextLabel().setForeground(new Color(249, 85, 0));
        game.getDrawing().setIcon(game.getDrawing().getHangmenIterator().next());

        if (!game.getDrawing().getHangmenIterator().hasNext()) {
            game.endGame(false);
        } else {
            game.getTextLabel().setToWrong();
        }
    }

}
