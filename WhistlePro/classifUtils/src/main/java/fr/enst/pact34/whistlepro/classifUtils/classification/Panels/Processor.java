package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;
import fr.enst.pact34.whistlepro.api2.main.ProcessorEventListener;

/**
 * Created by mms on 01/03/16.
 */
public class Processor implements AudioDataListener, ProcessorEventListener {

    private boolean continuee =true;
    double Fs ;
    UserInterface ui = null;
    ProcessingMachine pm;

    public Processor(double fs, UserInterface ui,String classifierData)
    {
        this.ui = ui;
        Fs= fs;
        pm = new ProcessingMachine(fs,classifierData,2, Piste.TypePiste.Percussions);
        pm.setEventLister(this);
        pm.startRecProcessing();
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
