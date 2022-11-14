package ui;

import model.Master;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RunApp {

    private Master master;

    public RunApp(Master master) {
        this.master = master;
        boolean editButton = false;
        boolean viewButton = false;
        boolean saveButton = false;
        createMasterFrame();
        if (editButton) {
            runEditor();
        }
        if (viewButton) {
            runViewer();
        }
        if (saveButton) {
            runSaver(master.getUser());
        }
    }

    private void createMasterFrame() {
        MasterFrame masterFrame = new MasterFrame("Finance Manager");
    }

    private void runViewer() {

    }

    private void runEditor() {

    }

    private void runSaver(String user) {
    }
}
