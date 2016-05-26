package fr.enst.pact34.whistlepro.pcapp;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;

class MainWindow extends JFrame {


    public static void main(String[] args) {
        System.out.println("Yoo");
        MainWindow window = new MainWindow();
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);



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
            public int getAmplitude() {
                return 10;
            }
        });
*/
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

        window.add(curveView);
        window.pack();

        System.out.println("Yoo");
        window.setVisible(true);
        System.out.println("Yoo");
    }

}
