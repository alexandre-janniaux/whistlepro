package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by mms on 02/04/16.
 */
public class textAreaFileChooser extends JPanel implements ActionListener{
    JTextArea fileName = new JTextArea(".");
    JLabel label = new JLabel("");
    JButton btn = new JButton("Select file");
    JFileChooser dialogue = new JFileChooser(new File("."));

    public textAreaFileChooser(String label)
    {
        setLayout(new BorderLayout());
        add(fileName,BorderLayout.CENTER);
        btn.addActionListener(this);
        add(btn,BorderLayout.EAST);
        this.label.setText(label);
        add(this.label,BorderLayout.WEST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialogue.showOpenDialog(null);
        fileName.setText(dialogue.getSelectedFile().getAbsolutePath());
    }

    public String getText() {
        return fileName.getText();
    }

    public void setText(String s) {
        dialogue.setSelectedFile(new File(s));
        fileName.setText(s);
        fileName.setText(dialogue.getSelectedFile().getAbsolutePath());
    }
}
