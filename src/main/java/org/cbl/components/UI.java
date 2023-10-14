package org.cbl.components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class UI extends JFrame {

    // Used for button pane layout
    private JPanel buttonPane;

    // Button Columns
    private JButton[] first;
    private JButton[] second;
    private JButton[] third;

    // Button Letters
    private final String[] firstRow = {"Q","W","E","R","T","Y","U","I","O","P"};
    private final String[] secondRow = {"A","S","D","F","G","H","J","K","L"};
    private final String[] thirdRow = {"Z","X","C","V","B","N","M"};

    public UI() throws HeadlessException {
        setLook();

        setIcons();
        setSize(650, 500);
        setTitle("Hangman");
        setLocation(320, 80);
        setKeyboard();
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("rope.png")
        ));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

//        pack();
        setVisible(true);
    }

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
     * Sets button listeners to all buttons
     * @param game Game object used for listening of any action
     */
    public void setButtonListeners(Game game)
    {
        addWindowListener(game);
    }

    /**
     * Sets the keyboard before every new game.
     */
    private void setKeyboard() {
        JPanel keyboard = new JPanel();
        keyboard.setLayout(new BoxLayout(keyboard, BoxLayout.PAGE_AXIS));

        first = fillButtonRow(firstRow);
        keyboard.add(buttonPane);

        second = fillButtonRow(secondRow);
        keyboard.add(buttonPane);

        third = fillButtonRow(thirdRow);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
        keyboard.add(buttonPane);

        add(keyboard, BorderLayout.SOUTH);
    }

    /**
     * Fills the specified row of the keyboard with the corresponding letters
     * @param letterRow letters to add to the buttons
     * @return the newly created buttons
     */
    private JButton[] fillButtonRow(String[] letterRow) {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        JButton[] buttonRow = new JButton[letterRow.length];
        for(int i = 0; i < letterRow.length; ++i)
        {
            buttonRow[i] = new JButton(letterRow[i]);
            buttonRow[i].setFont(new Font("Serif", Font.BOLD, 20));
            buttonRow[i].setForeground(Color.MAGENTA);
            buttonRow[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonRow[i].setMaximumSize(new Dimension(40, 40));
            buttonRow[i].setBackground(Color.YELLOW);
            buttonPane.add(buttonRow[i]);
        }

        return buttonRow;
    }

    /**
     * Saves the images in variables at application startup.
     */
    public void setIcons()
    {
        ImageIcon icon;

//        icon = new ImageIcon(Objects.requireNonNull(
//                getClass().getClassLoader().getResource("defaultButton.png"))
//        );
//        defaultButton = new ImageIcon(icon.getImage().getScaledInstance(
//                40, 40,  java.awt.Image.SCALE_SMOOTH)
//        );
//
//        icon = new ImageIcon(Objects.requireNonNull(
//                getClass().getClassLoader().getResource("correctButton.png"))
//        );
//        correctButton = new ImageIcon(icon.getImage().getScaledInstance(
//                40, 40,  java.awt.Image.SCALE_SMOOTH)
//        );
//
//        icon = new ImageIcon(Objects.requireNonNull(
//                getClass().getClassLoader().getResource("wrongButton.png"))
//        );
//        wrongButton = new ImageIcon(icon.getImage().getScaledInstance(
//                40, 40,  java.awt.Image.SCALE_SMOOTH)
//        );

    }

}
