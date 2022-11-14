package ui;

import model.Master;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class InitGUI {

    private static final String PATH = "./data/";
    private Master master;
    private final Scanner input;

    public InitGUI() {
        input = new Scanner(System.in);
        startGui();
    }

    private void startGui() {
        init();
        //createFrame();
        //createMasterPane();
        
    }

    private void init() {
        Object[] options = {"Yes", "No", "Quit"};
        String title = "Start Finance Manager";
        String message = "Are you an existing user?";
        DialogBox box = new DialogBox(options, title, message);
        int n = box.getAnswer();
        switch (n) {
            case YES_OPTION:
                String username = JOptionPane.showInputDialog("Please enter your username");
                runLoader(username);
                break;
            case NO_OPTION:
                String newUsername = JOptionPane.showInputDialog("Please enter a username");
                master = new Master(newUsername);
                break;
            default:
                box.close();
                break;
        }
        new RunApp(master);
    }


    // MODIFIES: this, jsonReader
    // EFFECT: Loads the json file, with user-given name, from the data folder if exists, otherwise inform the user.
    private boolean runLoader(String username) {
        try {
            JsonReader jsonReader = new JsonReader(PATH + username + ".json");
            master = jsonReader.load();
            System.out.println("Master file for " + master.getUser() + " was loaded successfully from " + PATH);
        } catch (IOException e) {
            System.out.println("Given user name does not exist!");
            return false;
        }
        return true;
    }

}
