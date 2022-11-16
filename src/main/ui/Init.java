package ui;

import model.Master;
import persistence.JsonReader;

import javax.swing.*;

import java.awt.event.WindowEvent;
import java.io.IOException;

import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class Init {

    private static final String PATH = "./data/";
    private static final String INIT_TITLE = "Start Finance App";

    private Master master;

    public Init() throws IOException {
        Object[] options = {"Yes", "No", "Quit"};
        String title = INIT_TITLE;
        String message = "Are you an existing user?";
        DialogBox box = new DialogBox(options, title, message);
        int n = box.getAnswer();
        switch (n) {
            case YES_OPTION:
                String username = JOptionPane.showInputDialog("Please enter your username");
                master = runLoader(username);
                break;
            case NO_OPTION:
                String newUsername = JOptionPane.showInputDialog("Please enter a username");
                master = new Master(newUsername);
                break;
            default:
                box.close();
                break;
        }
    }

    // MODIFIES: this, jsonReader
    // EFFECT: Loads the json file, with user-given name, from the data folder if exists, otherwise inform the user.
    private Master runLoader(String username) throws IOException {
        JsonReader jsonReader = new JsonReader(PATH + username + ".json");
        Master master = jsonReader.load();
        System.out.println("Master file for " + master.getUser() + " was loaded successfully from " + PATH);
        return master;
    }

    public Master getMaster() {
        return master;
    }

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
}
