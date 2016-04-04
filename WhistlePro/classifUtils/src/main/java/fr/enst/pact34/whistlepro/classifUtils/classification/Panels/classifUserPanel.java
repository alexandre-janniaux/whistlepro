package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api.common.FileOperator;

/**
 * Created by mms on 02/04/16.
 */
public class classifUserPanel extends JPanel implements ActionListener{
    private JButton  btnLoadClassifier = new JButton("Load classifier");
    private JLabel classifierLoaded = new JLabel("No classifier loaded.");

    private MfccDbTree dbTree = new MfccDbTree();

    JPanel mainPanel = new JPanel();

    public classifUserPanel()
    {
        super();
        //main panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel(), BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(centerPanel()), BorderLayout.CENTER);
        mainPanel.add(bottomPanel(), BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.add(mainPanel,BorderLayout.CENTER);
    }

    private JPanel centerPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        return panel;
    }

    private JPanel topPanel()
    {
        JPanel panel = new JPanel();

        GridLayout gl = new GridLayout();
        gl.setColumns(1);
        gl.setHgap(5);
        gl.setVgap(5);
        panel.setLayout(gl);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnLoadClassifier.addActionListener(this);

        panel.add(btnLoadClassifier);
        panel.add(classifierLoaded);

        gl.setRows(2);
        return panel;
    }

    private JPanel bottomPanel()
    {
        JPanel panel = new JPanel();

        return panel;
    }

    MultipleStrongClassifiers classifier  = null;
    JFileChooser classifierSelector = new JFileChooser(new File("."));
    @Override
    public void actionPerformed(ActionEvent e) {
        Object sender = e.getSource();
        if(sender == btnLoadClassifier)
        {
            classifierSelector.showOpenDialog(null);
            File file = classifierSelector.getSelectedFile();
            if(file.exists())
            {
                classifier = new MultipleStrongClassifiers.Builder().fromString(FileOperator.getDataFromFile(file.getAbsolutePath())).build();
                if(classifier==null)
                    JOptionPane.showMessageDialog(null,"Bad data...");
                else {
                    classifierLoaded.setText("Loaded classifier : " + file.getAbsolutePath());
                    JOptionPane.showMessageDialog(null, "File loaded !");
                }
            }
            else JOptionPane.showMessageDialog(null,"File does not exist...");
        }
    }

}
