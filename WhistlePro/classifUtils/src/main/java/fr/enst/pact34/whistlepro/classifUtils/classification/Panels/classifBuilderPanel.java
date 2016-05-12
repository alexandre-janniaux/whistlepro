package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;

/**
 * Created by mms on 02/04/16.
 */
public class classifBuilderPanel extends JPanel implements ActionListener{
    private JButton btnStartLearn = new JButton("Learn"),
    btnLoadDb = new JButton("Load DB");

    private textAreaFileChooser dbFileName= new textAreaFileChooser("Db file :");
    private textAreaFileChooser classifierFileName= new textAreaFileChooser("Classifier file :");
    private JFormattedTextField nbClassifierFiel = new JFormattedTextField(NumberFormat.getNumberInstance());

    private MfccDbTree dbTree = new MfccDbTree();

    String[] panels = {"main","wait"};
    JPanel mainPanel = new JPanel();
    JPanel waitPanel = new JPanel();

    CardLayout cl = new CardLayout();
    public classifBuilderPanel()
    {
        super();
        //main panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel(),BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(centerPanel()),BorderLayout.CENTER);
        mainPanel.add(bottomPanel(),BorderLayout.SOUTH);

        //wait panel
        waitPanel.setLayout(new BorderLayout());
        waitPanel.add(new JLabel("Working..."),BorderLayout.CENTER);

        //this
        this.setLayout(cl);
        this.add(mainPanel,panels[0]);
        this.add(waitPanel,panels[1]);

        cl.show(this,panels[0]);

        dbFileName.setEnabled(false);
    }

    private JPanel centerPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(dbTree,BorderLayout.CENTER);
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
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        btnLoadDb.addActionListener(this);
        dbFileName.setText("learnData/untitled.db");

        panel.add(dbFileName);
        panel.add(btnLoadDb);

        gl.setRows(2);
        return panel;
    }

    private JPanel bottomPanel()
    {
        JPanel panel = new JPanel();

        GridLayout gl = new GridLayout();
        gl.setColumns(1);
        gl.setHgap(5);
        gl.setVgap(5);
        panel.setLayout(gl);
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        btnStartLearn.addActionListener(this);
        classifierFileName.setText("learnData/untitled.msc");


        JPanel classNb = new JPanel();
        classNb.setLayout(new BorderLayout());
        classNb.add(new JLabel("Number of weak classifier : "), BorderLayout.WEST);
        classNb.add(nbClassifierFiel,BorderLayout.CENTER);
        nbClassifierFiel.setText("50");

        panel.add(classNb);
        panel.add(classifierFileName);
        panel.add(btnStartLearn);

        gl.setRows(3);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sender = e.getSource();
        if (sender == btnStartLearn) {
            final String dbFile = dbFileName.getText();
            if(new File(dbFile).exists())
            {
                final int nb_c = Integer.parseInt(nbClassifierFiel.getText());
                if(nb_c>0) {
                    final JPanel par = this;
                    cl.show(this,panels[1]);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ClassifierLearner.buildClassifier(dbFile, nb_c,classifierFileName.getText());
                            cl.show(par,panels[0]);
                        }
                    }).start();
                }
                else JOptionPane.showMessageDialog(null, "You should define a number of weak classifier.");
            }
            else JOptionPane.showMessageDialog(null, "DB file does not exist.");
        }
        else if(sender == btnLoadDb)
        {
            String dbFile = dbFileName.getText();
            if(new File(dbFile).exists()) {
                dbTree.loadFromFile(dbFile);
            }
            else JOptionPane.showMessageDialog(null, "DB file does not exist.");
        }
    }

}
