package fr.enst.pact34.whistlepro.api2.main;

/**
 * Created by mms on 25/04/16.
 */
public class ProcessingMachine extends ProcessingMachineBase {

    public ProcessingMachine(double Fs, String classifierData, int nbThread, TypeRec typeRec) {
        super(Fs, classifierData, nbThread);
        init(typeRec);
    }

    private Piste piste = null;

    @Override
    public void init(TypeRec typeRec) {
        switch (typeRec)
        {
            case Melodie:
                piste = new PisteMelodie();
                break;
            case Percussions:
                piste = new PistePercu();
                break;
        }
    }

    @Override
    public void setTitle(String title) {
        piste.setTitle(title);
    }

    @Override
    public void correct() {
        // TODO use correction module here to correct song
    }

    @Override
    public Piste getPiste() {
        return piste;
    }
}
