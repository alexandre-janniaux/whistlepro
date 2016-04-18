package fr.enst.pact34.whistlepro.api2.main;

/**
 * Created by mms on 18/04/16.
 */
public interface ProcessingMachineEventListener {
    enum WorkEvent
    {
        OneWorkDone,
        AllWorkDone
    }

    void newWorkEvent(WorkEvent e);
}
