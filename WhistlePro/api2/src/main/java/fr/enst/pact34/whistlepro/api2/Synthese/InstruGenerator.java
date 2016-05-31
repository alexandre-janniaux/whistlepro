package fr.enst.pact34.whistlepro.api2.Synthese;

import java.util.Hashtable;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;

/**
 * Created by mms on 28/04/16.
 */
public class InstruGenerator {
    private double Fs ;
    private Hashtable<PisteMelodie.Instrument,InstruParams> instruGenElmts = new Hashtable<>();

    public InstruGenerator(double Fs)
    {
        this.Fs = Fs;
    }

    public double getSamplingFreq() {
        return Fs;
    }

    public Signal generate(PisteMelodie.Instrument instrument, double time, double freq) {
        InstruParams e = instruGenElmts.get(instrument);
        if(e == null)
            throw new RuntimeException(instrument.name()+"'s data weren't added to the generator.");
        return e.generateTime(time,Fs,freq);
    }

    public void addInstru(PisteMelodie.Instrument instrument, double r, double m)
    {
        InstruParams e = new InstruParams(r,m);
        instruGenElmts.put(instrument,e);
    }

    public void replaceInstru(PisteMelodie.Instrument instru, double r, double m) {
        instruGenElmts.remove(instru);
        addInstru(instru,r,m);
    }

    public InstruData getInstruData(PisteMelodie.Instrument instru)
    {
        return (InstruData) instruGenElmts.get(instru);
    }

    private class InstruParams implements InstruData {
        double r;
        double m;

        public InstruParams(double r, double m) {
            this.r = r;
            this.m = m;
        }

        public Signal generateTime(double time, double Fe, double freq) {
            int nbSamples = (int) (time * Fe);
            Signal toRet = new Signal();
            toRet.setLength(nbSamples);

            toRet.fromArray(SyntheseFM.instFM(nbSamples, Fe, freq, r,m));

            toRet.setSamplingFrequency(Fe);
            return toRet;
        }

        @Override
        public double getM() {
            return m;
        }

        @Override
        public double getR() {
            return r;
        }
    }

}
