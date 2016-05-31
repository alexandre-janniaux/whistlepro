package fr.enst.pact34.whistlepro.api2.main;

import fr.enst.pact34.whistlepro.api2.Synthese.InstruGenerator;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.Synthese.PercuGenerator;
import fr.enst.pact34.whistlepro.api2.Synthese.Synthetiseur;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

/**
 * Created by mms on 25/04/16.
 */
public class ProcessingMachine extends ProcessingMachineBase {

    public ProcessingMachine(double Fs, String classifierData, int nbThread, Piste.TypePiste typePiste) {
        super(Fs, classifierData, nbThread);
        init(typePiste);
    }


    private Piste piste = null;
    private Piste.TypePiste typePiste = null;
    private PisteCreator pisteCreator = new PisteCreator();
    private boolean valid = false;

    @Override
    public void init(Piste.TypePiste typePiste) {
        this.typePiste = typePiste;
        super.setupFor(typePiste);
        piste = null;
        valid = false;
        title = "";
    }

    @Override
    public void startRecProcessing() {
        System.gc();
        piste = null;
        valid = false;
        super.startProcessing();
    }

    @Override
    public synchronized void stopRecProcessing() {
        super.stopProcessing();
        waitEnd();
        pisteCreator.clearOldData();
        pisteCreator.addAttackTimes(super.getAttacksList());
        switch (typePiste)
        {
            case Melodie:
                pisteCreator.addFrequencies(super.getFrequenciesList());
                break;
            case Percussions:
                pisteCreator.addPercus(super.getClassifList());
                break;
        }
        piste = pisteCreator.buildPiste(typePiste);
        piste.setTitle(title);
        valid = true;
//        if(piste==null) throw new RuntimeException("Error piste was not created.");
    }


    String title = "";
    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public void correct() {
        // TODO use correction module here to correct song
    }

    @Override
    public synchronized Piste getPiste() {
        if(valid == false) throw new RuntimeException("You should stop rec before.");
        return piste;
    }

    private double genFs = 16000;
    InstruGenerator instruGenerator = new InstruGenerator(genFs);
    PercuGenerator percuGenerator = new PercuGenerator(genFs);
    Synthetiseur synthe = new Synthetiseur(percuGenerator,instruGenerator,genFs);

    @Override
    public Signal synthetisePiste(Piste piste) {
        return synthe.synthetise(piste);
    }

    @Override
    public Signal synthetiseMorceau(Morceau morceau) {
        return synthe.synthetise(morceau);
    }

    public void addPercuData(Percu.Type type,Signal sig)
    {
        percuGenerator.addPercu(type,sig);
    }


    public void addInstruData(PisteMelodie.Instrument instrument,double r, double m)
    {
        instruGenerator.addInstru(instrument, r, m);
    }


    public void setPercuCorrespondance(String recoStr, Percu.Type typeAssocie)
    {
        pisteCreator.setPercuCorrespondance(recoStr, typeAssocie);
    }

    public void replaceInstruData(PisteMelodie.Instrument instru, double r, double m) {
        instruGenerator.replaceInstru(instru,r,m);
    }
}
