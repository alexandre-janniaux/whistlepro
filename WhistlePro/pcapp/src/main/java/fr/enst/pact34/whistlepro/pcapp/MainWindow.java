package fr.enst.pact34.whistlepro.pcapp;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

class MainWindow extends JFrame {


    public static void main(String[] args) {
        System.out.println("Yoo");
        MainWindow window = new MainWindow();
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);



        CurveWidget curveView = new CurveWidget();
        curveView.setStepPoints(15, 0.5);
        curveView.addCurve(new CurveAdapterInterface() {
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
        window.add(curveView);
        window.pack();

        System.out.println("Yoo");
        window.setVisible(true);
        System.out.println("Yoo");
    }

}
