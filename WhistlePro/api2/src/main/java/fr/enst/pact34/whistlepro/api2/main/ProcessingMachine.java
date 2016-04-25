package fr.enst.pact34.whistlepro.api2.main;

/**
 * Created by mms on 25/04/16.
 */
public class ProcessingMachine extends ProcessingMachineBase {

    public ProcessingMachine(double Fs, String classifierData, int nbThread, TypePiste typePiste) {
        super(Fs, classifierData, nbThread);
        init(typePiste);
    }

    private Piste piste = null;

    @Override
    public void init(TypePiste typePiste) {
        switch (typePiste)
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
