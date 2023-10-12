package org.cbl.components;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

/**
 * Main Controller Class
 */
public class Game implements MouseListener, ActionListener, WindowListener {

    private final UI gui;

    private boolean isPlaying = true;

    public Game() {
        this.gui = new UI();





        gui.setButtonListeners(this);
    }

    /**
     * The method handles the click of the exit button
     *
     * @param e the exit button click to be processed accordingly
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if (isPlaying) {

            ImageIcon warning = new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("warning.png")
            ));

            Object[] options = {"Exit", "Cancel"};

            int quit = JOptionPane.showOptionDialog(null, "Your progress will be lost.",
                    "Exit?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, warning, options, options[1]);

            if (quit == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
