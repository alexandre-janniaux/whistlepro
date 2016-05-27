package fr.enst.pact34.whistlepro.pcapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Created by mms on 27/05/16.
 */
public class DataViewer extends JPanel {

    CurveWidget signalCurvWidg = new CurveWidget();
    CurveWidget attackCurvWidg = new CurveWidget();

    public DataViewer()
    {
        signalCurvWidg.setStepPoints(5,5);
        attackCurvWidg.setStepPoints(5,5);
        GridLayout gl = new GridLayout();
        this.setLayout(gl);
        gl.setColumns(1);
        gl.setRows(2);

        JPanel panSig = new JPanel();
        panSig.setLayout(new BorderLayout());
        panSig.add(new JLabel("Signal"),BorderLayout.NORTH);
        panSig.add(signalCurvWidg,BorderLayout.CENTER);
        this.add(panSig);

        JPanel panAttck = new JPanel();
        panAttck.setLayout(new BorderLayout());
        panAttck.add(new JLabel("Attaque"),BorderLayout.NORTH);
        panAttck.add(attackCurvWidg,BorderLayout.CENTER);
        this.add(panAttck);


    }
}
