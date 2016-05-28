package fr.enst.pact34.whistlepro.pcapp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;

import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.pcapp.adapters.AttackAdapter;
import fr.enst.pact34.whistlepro.pcapp.adapters.SignalAdapter;
import fr.enst.pact34.whistlepro.pcapp.adapters.SpectrogramAdapter;
import fr.enst.pact34.whistlepro.pcapp.interfaces.adapters.CurveAdapterInterface;
import fr.enst.pact34.whistlepro.pcapp.interfaces.adapters.SpectrogramAdapterInterface;
import fr.enst.pact34.whistlepro.pcapp.interfaces.proxies.CurveViewProxy;
import fr.enst.pact34.whistlepro.pcapp.interfaces.proxies.SpectrogramViewProxy;
import fr.enst.pact34.whistlepro.pcapp.interfaces.views.CurveView;
import fr.enst.pact34.whistlepro.pcapp.interfaces.views.SpectrogramView;

/**
 * Created by mms on 27/05/16.
 */
public class DataViewer extends JPanel {

    private SignalAdapter signal = new SignalAdapter();
    private HashMap<String, CurveView> signals = new HashMap<>();

    private SpectrogramAdapter adapter;

    public DataViewer()
    {
        try {
            signal.loadFromWavFile("data/a.wav");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WavFileException e) {
            e.printStackTrace();
        }

        GridLayout gl = new GridLayout();
        this.setLayout(gl);
        gl.setColumns(1);
        gl.setRows(3);


        adapter = SpectrogramAdapter.createFromSignal(signal,200, 100);
        addDataSet("Spectrogramme", adapter);

        addDataSet("Signal", signal);

        double[] buffer = new double[signal.length()];
        signal.fillArray(buffer);

        addDataSet("Attaque", AttackAdapter.createFromSignal(buffer));


    }

    public CurveViewProxy addDataSet(String name, CurveAdapterInterface curveData) {
        CurveView curve;
        if (signals.containsKey(name)) {
            curve = signals.get(name);
        }
        else {
            synchronized (signals) {
                curve = new CurveView();
                curve.setStepPoints(0.05, 400);

                JPanel pan = new JPanel();
                pan.setLayout(new BorderLayout());
                pan.add(new JLabel(name), BorderLayout.NORTH);
                pan.add(curve,BorderLayout.CENTER);
                this.add(pan);

                signals.put(name, curve);
            }
        }

        CurveViewProxy proxy = curve.addCurve(curveData);
        return proxy;
    }

    public SpectrogramViewProxy addDataSet(String name, SpectrogramAdapterInterface specData) {
        SpectrogramView view = new SpectrogramView();

        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.add(new JLabel(name), BorderLayout.NORTH);
        pan.add(view, BorderLayout.CENTER);
        this.add(pan);

        SpectrogramViewProxy proxy = view.setSpectrogram(specData);
        return proxy;
    }
}
