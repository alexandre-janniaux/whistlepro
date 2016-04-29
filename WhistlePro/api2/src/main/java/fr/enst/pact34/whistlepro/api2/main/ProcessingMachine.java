package fr.enst.pact34.whistlepro.api2.main;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.InstruGenerator;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.Synthese.PercuGenerator;
import fr.enst.pact34.whistlepro.api2.Synthese.Synthetiseur;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

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
        super.setupFor(typePiste);
    }

    @Override
    public void startRecProcessing() {
        super.startProcessing();
    }
/*
    @Override
    public void setTitle(String title) {
        piste.setTitle(title);
    }
*/
    @Override
    public void correct() {
        // TODO use correction module here to correct song
    }

    @Override
    public Piste getPiste() {
        return piste;
    }

    private double genFs = 16000;
    InstruGenerator instruGenerator = new InstruGenerator(genFs);
    PercuGenerator percuGenerator = new PercuGenerator(genFs);
    Synthetiseur synthe = new Synthetiseur(percuGenerator,instruGenerator);

    @Override
    public Signal synthetisePiste(Piste piste) {
        return synthe.synthetise(piste);
    }

    public void addPercuData(Percu.Type type,Signal sig)
    {
        percuGenerator.addPercu(type,sig);
    }


    public void addInstruData(Instru.Type type,double r, double m)
    {
        instruGenerator.addInstru(type,r,m);
    }
}
