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
    private TypePiste typePiste = null;
    private PisteBuilder pisteBuilder = new PisteBuilder();

    @Override
    public void init(TypePiste typePiste) {
        this.typePiste = typePiste;
        super.setupFor(typePiste);
        piste = null;
    }

    @Override
    public void startRecProcessing() {
        super.startProcessing();
        piste = null;
    }

    @Override
    public void stopRecProcessing() {
        super.stopProcessing();
        waitEnd();
        pisteBuilder.clearOldData();
        pisteBuilder.addAttackTimes(super.getAttacksList());
        switch (typePiste)
        {
            case Melodie:
                pisteBuilder.addFrequencies(super.getFrequenciesList());
                break;
            case Percussions:
                pisteBuilder.addPercus(super.getClassifList());
                break;
        }
        piste = pisteBuilder.buildPiste(typePiste);
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
        instruGenerator.addInstru(type, r, m);
    }


    public void setPercuCorrespondance(String recoStr, Percu.Type typeAssocie)
    {
        pisteBuilder.setPercuCorrespondance(recoStr, typeAssocie);
    }
}
