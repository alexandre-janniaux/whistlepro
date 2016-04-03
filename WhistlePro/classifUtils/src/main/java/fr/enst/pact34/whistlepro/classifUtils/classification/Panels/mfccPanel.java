package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by mms on 02/04/16.
 */
public class mfccPanel extends JPanel implements ActionListener{
    private JButton btnNewClass = new JButton("New class"),
            btnNewSample = new JButton("New sample"),
            btnLoadDB = new JButton("Load DB"),
            btnCalcMfccs = new JButton("Calculate MFCCs"),
            btnSaveDB = new JButton("Save DB");

    private textAreaFileChooser dbFileName= new textAreaFileChooser("Db file :");

    MfccDbTree mfccDB = new MfccDbTree();
    JLabel projectName = new JLabel("DataBase : ");

    String[] panels = {"main","wait"};
    JPanel mainPanel = new JPanel();
    JPanel waitPanel = new JPanel();

    CardLayout cl = new CardLayout();
    public mfccPanel()
    {
        super();
        //main panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel(),BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(DBTreePanel()),BorderLayout.CENTER);
        mainPanel.add(bottomPanel(),BorderLayout.SOUTH);

        //wait panel
        waitPanel.setLayout(new BorderLayout());
        waitPanel.add(new JLabel("Working..."),BorderLayout.CENTER);

        //this
        this.setLayout(cl);
        this.add(mainPanel,panels[0]);
        this.add(waitPanel,panels[1]);

        cl.show(this,panels[0]);
    }

    private JPanel topPanel()
    {
        JPanel panel = new JPanel();

        GridLayout gl = new GridLayout();
        gl.setColumns(1);
        gl.setRows(1);
        gl.setHgap(5);
        gl.setVgap(5);
        panel.setLayout(gl);
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(projectName);
        return  panel;
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

        btnNewClass.addActionListener(this);
        btnSaveDB.addActionListener(this);
        btnNewSample.addActionListener(this);
        btnLoadDB.addActionListener(this);
        btnCalcMfccs.addActionListener(this);
        dbFileName.setText("learnData/untitled.db");

        panel.add(btnNewClass);
        panel.add(btnNewSample);
        panel.add(btnCalcMfccs);
        panel.add(btnLoadDB);
        panel.add(dbFileName);
        panel.add(btnSaveDB);

        gl.setRows(6);

        return panel;
    }

    private JPanel DBTreePanel()
    {
        JPanel panel = new JPanel();

        BorderLayout gl = new BorderLayout();
        panel.setLayout(gl);

        mfccDB.setRootVisible(true);

        panel.add(mfccDB);

        return panel;
    }

    private JFileChooser fileSelector = new JFileChooser(new File("."));
    @Override
    public void actionPerformed(ActionEvent e) {
        Object sender = e.getSource();
        if (sender == btnNewClass)
        {
            String ret = JOptionPane.showInputDialog(null, "Class name : ");
            if(ret != null) {
                if (mfccDB.addClass(ret) == false) {
                    JOptionPane.showMessageDialog(null, "Already existing...");
                }
            }
        }
        else if(sender == btnNewSample)
        {
            String[] classes= mfccDB.getClassesList();
            if(classes.length > 0) {
                String classe = (String) JOptionPane.showInputDialog
                        (null, "New sample", "Which class ?",
                                JOptionPane.QUESTION_MESSAGE, null, mfccDB.getClassesList(), null);

                if (classe != null) {
                    fileSelector.setMultiSelectionEnabled(true);
                    fileSelector.setFileFilter(new FileNameExtensionFilter("Wav files","wav","WAV"));
                    fileSelector.showOpenDialog(null);

                    if (fileSelector.getSelectedFile() != null) {
                        for (File file : fileSelector.getSelectedFiles()
                                ) {
                            mfccDB.addSample(classe, file.getAbsolutePath().replace("\\","/"));
                        }
                    }

                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"You need to create at least one class.");
            }


        }
        else if(sender == btnCalcMfccs)
        {
            final JPanel par = this;
            cl.show(this,panels[1]);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mfccDB.calculateMfccs();
                    cl.show(par,panels[0]);
                }
            }).start();
        }
        else if(sender == btnSaveDB)
        {
            final JPanel par = this;
            cl.show(this,panels[1]);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mfccDB.saveToFile(dbFileName.getText());
                    cl.show(par,panels[0]);
                }
            }).start();
        }
        else if(sender == btnLoadDB)
        {
            JFileChooser fileSelectorDB = new JFileChooser(new File("."));
            fileSelectorDB.showOpenDialog(null);
            mfccDB.loadFromFile(fileSelectorDB.getSelectedFile().getAbsolutePath());
        }
    }

}
