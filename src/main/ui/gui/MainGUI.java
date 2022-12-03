package ui.gui;

import java.io.IOException;

// starts the application
public class MainGUI {
    public static void main(String[] args) throws IOException {
        new FinAppGUI((new Init()).getMaster());
    }
}
