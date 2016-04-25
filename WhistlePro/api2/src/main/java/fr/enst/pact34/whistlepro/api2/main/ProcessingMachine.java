package fr.enst.pact34.whistlepro.api2.main;

/**
 * Created by mms on 25/04/16.
 */
public class ProcessingMachine extends ProcessingMachineBase {

    public ProcessingMachine(double Fs, String classifierData, int nbThread) {
        super(Fs, classifierData, nbThread);
    }

    @Override
    public void init(TypeRec typeRec) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void correct() {

    }

    @Override
    public Piste getPiste() {
        return null;
    }
}
