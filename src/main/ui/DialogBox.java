package ui;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class DialogBox extends JOptionPane {

    private JFrame box;
    private int answer;

    public DialogBox(Object[] options, String title, String message) {
        box = new JFrame();
        box.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        answer = JOptionPane.showOptionDialog(box, message, title,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    public int getAnswer() {
        return answer;
    }

    public void close() {
        box.dispatchEvent(new WindowEvent(box, WindowEvent.WINDOW_CLOSING));
    }
}
