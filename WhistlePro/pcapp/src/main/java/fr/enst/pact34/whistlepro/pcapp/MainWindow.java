package fr.enst.pact34.whistlepro.pcapp;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;

class MainWindow extends JFrame {


    public static void main(String[] args) {
        System.out.println("Yoo");
        MainWindow window = new MainWindow();
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);

        /*

        CurveWidget curveView = new CurveWidget();
        curveView.setStepPoints(50, 0.01);
  /*      curveView.addCurve(new CurveAdapterInterface() {
            @Override
            public boolean isFinite() {
                return false;
            }

            @Override
            public double getValue(int i) {
                double x = i-getNbPoints()/2;
                return x*x;
            }

            @Override
            public double getAbscisse(int i) {
                return i-getNbPoints()/2;
            }

            @Override
            public int getNbPoints() {
                return 50;
            }

            @Override
            public int getMaxAmplitude() {
                return 10;
            }
        });

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile() ;

        if(file != null) {
            SignalExtended sigTest = new SignalExtended();
            try {
                sigTest.loadFromWavFile(file.getAbsolutePath());
                curveView.addCurve(sigTest);
            } catch (IOException e) {
                System.out.println("Pb de lecture fichier");
                e.printStackTrace();
            } catch (WavFileException e) {
                System.out.println("Pb de lecture fichier");
                e.printStackTrace();
            }
        }
        else
            System.out.println("Pas de fichier choisi");
=======

        curveView.scroll(15.,0.);
>>>>>>> Stashed changes

        window.add(curveView);
        */

        //conteneur principal
        JPanel mainPannel = new JPanel();
        mainPannel.setLayout(new BorderLayout());

        JButton btnOpen = new JButton("open");
        mainPannel.add(btnOpen, BorderLayout.NORTH);

        JPanel dataViewer = new DataViewer();
        mainPannel.add(dataViewer, BorderLayout.CENTER);

        mainPannel.setBorder(new EmptyBorder(5, 5, 5, 5));

        //ajout du contenu a la fenetre
        window.setContentPane(mainPannel);
        window.pack();

        System.out.println("Yoo");
        window.setVisible(true);
        System.out.println("Yoo");
    }

}
