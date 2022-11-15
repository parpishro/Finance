package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MasterFrame implements ActionListener {

    private JFrame frame;
    private JLabel label;
    private JTextField field;

    public MasterFrame(String title) {
        this.frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton btn = new JButton("press");
        panel.add(btn);
        btn.setActionCommand("myButton");
        btn.addActionListener(this);
        field = new JTextField(5);
        panel.add(field);
        label = new JLabel("gglabel");
        panel.add(label);
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

    //This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            label.setText(field.getText());
        }
    }


    //Display the window.

 //   JButton btn = new JButton("Change");
 //       btn.setActionCommand("myButton");
}
