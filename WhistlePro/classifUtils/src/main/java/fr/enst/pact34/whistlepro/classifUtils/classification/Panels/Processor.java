package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2D;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.common.transformers;
import fr.enst.pact34.whistlepro.api.stream.ClassificationStream;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachineEventListener;

/**
 * Created by mms on 01/03/16.
 */
public class Processor implements AudioDataListener, ProcessingMachineEventListener {

    private boolean continuee =true;
    double Fs ;
    UserInterface ui = null;
    ProcessingMachine pm;

    public Processor(double fs, UserInterface ui,String classifierData)
    {
        this.ui = ui;
        Fs= fs;
        pm = new ProcessingMachine(fs,classifierData,2);
        pm.setEventLister(this);
    }

    @Override
    public void pushData(double[] data) {

        pm.pushData(data);
    }

    @Override
    public void newWorkEvent(WorkEvent e) {
        ui.showText(pm.getLastReco());
    }
}
