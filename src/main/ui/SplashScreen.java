package ui;

import javax.swing.*;

// Represents a splash screen that stays on screen for 5 seconds in the beginning of the app
public class SplashScreen {

    // constructs a splash screen in the start of app
    public SplashScreen(String filePath) {
        JWindow window = new JWindow();
        window.getContentPane().add(new JLabel("", new ImageIcon(filePath), SwingConstants.CENTER));
        window.setBounds(200, 100, 1200, 800);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }


}
