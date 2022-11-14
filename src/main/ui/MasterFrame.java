package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class MasterFrame {

    private JFrame frame;

    public MasterFrame(String title) {
        this.frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JButton("press"));
        panel.add(new JTextField(5));
        panel.add(new JLabel("gglabel"));
        panel.add(new JCheckBoxMenuItem("kkkk"));
//        panel.add(new JFileChooser());
        panel.add(new JMenu("menu"));
        panel.add(new JRadioButton());
        panel.add(new JSpinner());
        panel.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT));
        panel.add(new JTree());
        panel.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT));
        panel.add(new JTextArea("text 2000"));



        panel.add(new JButton("press2"));
        panel.add(new JTextField(5));
        panel.add(new JLabel("gglabel2"));
        frame.add(panel);
        frame.setVisible(true);
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        frame.pack();

    }


    //Display the window.

 //   JButton btn = new JButton("Change");
 //       btn.setActionCommand("myButton");
}
