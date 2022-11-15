package ui;

import model.Master;
import persistence.JsonReader;

import javax.swing.*;

import java.io.IOException;

import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class Init {

    private static final String PATH = "./data/";
    private Master master;

    public Init() throws IOException {
        Object[] options = {"Yes", "No", "Quit"};
        String title = "Start Finance Manager";
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
}
