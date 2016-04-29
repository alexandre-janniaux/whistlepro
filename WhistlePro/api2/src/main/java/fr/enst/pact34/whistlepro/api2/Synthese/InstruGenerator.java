package fr.enst.pact34.whistlepro.api2.Synthese;

import java.util.Hashtable;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

/**
 * Created by mms on 28/04/16.
 */
public class InstruGenerator {
    private double Fs ;
    private Hashtable<Instru.Type,InstruGenElmt> instruGenElmts = new Hashtable<>();

    public InstruGenerator(double Fs)
    {
        this.Fs = Fs;
    }

    public double getSamplingFreq() {
        return Fs;
    }

    public Signal generate(Instru.Type type, double time) {
        InstruGenElmt e = instruGenElmts.get(type);
        if(e == null)
            throw new RuntimeException(type.name()+"'s data weren't added to the generator.");
        return e.generateTime(time);
    }

    public void addInstru(Instru.Type typePercussion, Signal donnees)
    {
        InstruGenElmt e = new InstruGenElmt(typePercussion,donnees);
        instruGenElmts.put(typePercussion,e);
    }

    private class InstruGenElmt {
        Instru.Type typeInstrument;
        Signal sound = null;

        public InstruGenElmt(Instru.Type typeInstrument, Signal donnees) {
            this.typeInstrument = typeInstrument;
            if (donnees == null)
                throw new RuntimeException("InstruGenerator cannot be created, no data. (" + typeInstrument.name() + ")");
            if (donnees.length() <= 0)
                throw new RuntimeException("InstruGenerator cannot be created, data empty. (" + typeInstrument.name() + ")");
            sound = donnees;
        }

        public Signal generateTime(double time) {
            int nbSamples = (int) (time * sound.getSamplingFrequency());
            Signal toRet = new Signal();
            toRet.setLength(nbSamples);

            for (int i = 0; i < nbSamples; i += sound.length()) {
                toRet.fromSignal(sound, 0, i, sound.length());
            }

            toRet.setSamplingFrequency(sound.getSamplingFrequency());
            return toRet;
        }
    }
}
