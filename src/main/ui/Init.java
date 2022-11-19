package ui;

import model.Master;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;


// represent the initiation of the app either loading user information or creating a new user
public class Init {

    private static final String PATH = "./data/";
    private static final String INIT_TITLE = "Finance Manager";
    private static final Icon ICON = new ImageIcon("./data/moneyIcon.png");

    private Master master;



    // MODIFIES: this
    // EFFECT: opens a pop-up box to determine whether user is existing or not to load its master or create a new master
    public Init() throws IOException {

        new SplashScreen("./data/coins.jpg");

        Object[] options = {"Yes", "No", "Quit"};
        String message = "Are you an existing user?";
        DialogBox box = new DialogBox(options, INIT_TITLE, message);
        int n = box.getAnswer();
        switch (n) {
            case YES_OPTION:
                String username = (String) JOptionPane.showInputDialog(null,
                        "Please enter your username",INIT_TITLE, JOptionPane.INFORMATION_MESSAGE, ICON,
                        null, null);
                master = runLoader(username);
                break;
            case NO_OPTION:
                String newUsername = (String) JOptionPane.showInputDialog(null,
                        "Please enter a username",INIT_TITLE, JOptionPane.INFORMATION_MESSAGE, ICON,
                        null, null);
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


    // Getters:

    public Master getMaster() {
        return master;
    }


    // Inner Class:

    // represents a pop-up dialog box with yes/no/cancel option
    public static class DialogBox extends JOptionPane {

        private final JFrame box;
        private final int answer;


        // MODIFIES: this
        // EFFECT: constructs a dialog box with given title and message
        public DialogBox(Object[] options, String title, String message) {
            box = new JFrame();
            box.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            answer = JOptionPane.showOptionDialog(box, message, title,
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    ICON, options, options[0]);
        }


        // EFFECT: closes the dialog-box upon request
        public void close() {
            box.dispatchEvent(new WindowEvent(box, WindowEvent.WINDOW_CLOSING));
        }


        // Getters

        public int getAnswer() {
            return answer;
        }
    }


}
