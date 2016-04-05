package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api.common.FileOperator;

/**
 * Created by mms on 02/04/16.
 */
public class classifUserPanel extends JPanel implements ActionListener, UserInterface {
    private JButton  btnLoadClassifier = new JButton("Load classifier");
    private JButton  btnRec = new JButton("Start Recording...");
    private JLabel classifierLoaded = new JLabel("No classifier loaded.");
    private JLabel recoText = new JLabel("--");

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

        panel.add(recoText,BorderLayout.CENTER);

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
        btnRec.addActionListener(this);

        panel.add(btnLoadClassifier);
        panel.add(classifierLoaded);
        panel.add(btnRec);

        gl.setRows(3);
        return panel;
    }

    private JPanel bottomPanel()
    {
        JPanel panel = new JPanel();

        return panel;
    }

    MultipleStrongClassifiers classifier  = null;
    JFileChooser classifierSelector = new JFileChooser(new File("."));
    JavaSoundRecorder recorder = new JavaSoundRecorder(0.020);
    Timer timer = new Timer();

    double[] dataRec = null;

    TimerTask recTask = new TimerTask() {
        @Override
        public void run() {

            while(recorder.available())
            {
                dataRec  = recorder.getData();
                if(dataRec  == null)
                    continue;

                processor.pushData(dataRec);
            }


            if(recorder.isRecording())
                timer.schedule(this,10);
        }
    };
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
        else if(sender == btnRec)
        {
            if(recorder.isRecording()==false) {

                File file = new File(classifierLoaded.getText().replace("Loaded classifier : " ,""));
                if (file.exists()) {

                    recorder.start();
                    processor = new Processor(recorder.getSampleRate(), this, FileOperator.getDataFromFile(file.getAbsolutePath()));


                    processingThread = new Thread(processor);
                    processingThread.start();

                    timer.schedule(recTask, 10);
                }
                else JOptionPane.showMessageDialog(null,"File does not exist...");
            }
            else
            {
                recorder.stop();
                processingThread = null;
            }
        }
    }

    Thread processingThread = null;
    Processor processor = null;


    @Override
    public void showText(String text) {

    }
}
