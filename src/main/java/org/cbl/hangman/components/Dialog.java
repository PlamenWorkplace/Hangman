package org.cbl.hangman.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.util.TimerTask;
import javax.swing.*;

/**
 * Used to display winning or losing dialog window upon finishing the game.
 */
public class Dialog extends TimerTask {

    private final Game game;
    private final String message;

    /**
     * Called once to set up class variables.
     */
    public Dialog(Game game, String message) {
        this.game = game;
        this.message = message;
    }

    /**
     * Function scheduled to execute after class initialization.
     */
    @Override
    public void run() {
        JDialog dialog = new JDialog(game, java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        JLabel text = new JLabel(message, SwingConstants.CENTER);
        text.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        JPanel buttons = setButtons(dialog);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(20, 10));
        container.add(text, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);

        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dialog.dispose();
                try {
                    game.newGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        dialog.add(container);
        dialog.pack();
        dialog.setLocationRelativeTo(game);
        dialog.setVisible(true);
    }

    private JPanel setButtons(JDialog dialog) {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 2, 0));

        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        JButton playAgain = new JButton("Play Again");
        playAgain.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));


        exit.addActionListener((ActionEvent e) -> System.exit(0));
        playAgain.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            try {
                game.newGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttons.add(exit);
        buttons.add(playAgain);
        return buttons;
    }
}
