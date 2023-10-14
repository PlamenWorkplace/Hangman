package org.cbl.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends JFrame {

    public UI() throws HeadlessException {
        // Set frame
        setSize(650, 500);
        setTitle("Hangman");
        setLocation(320, 80);


        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("rope.png")
        ));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 9, 10, 10)); // 3 rows, 9 columns with spacing

        // Create 26 buttons and add them to the panel
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(String.valueOf(c));
            
            //What the buttons will do when clicked
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle button click here
                    button.setVisible(false); // An example of the button disappearing when clicked
                }
            });
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setButtonListeners(Game game)
    {
        addWindowListener(game);
    }
}
