package fr.enst.pact34.whistlepro.api2.Synthese;

import java.util.Hashtable;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

/**
 * Created by mms on 28/04/16.
 */
public class InstruGenerator {
    private double Fs ;
    private Hashtable<Instru.Type,InstruParams> instruGenElmts = new Hashtable<>();

    public InstruGenerator(double Fs)
    {
        this.Fs = Fs;
    }

    public double getSamplingFreq() {
        return Fs;
    }

    public Signal generate(Instru.Type type, double time, double freq) {
        InstruParams e = instruGenElmts.get(type);
        if(e == null)
            throw new RuntimeException(type.name()+"'s data weren't added to the generator.");
        return e.generateTime(time,Fs,freq);
    }

    public void addInstru(Instru.Type typePercussion, double r, double m)
    {
        InstruParams e = new InstruParams(r,m);
        instruGenElmts.put(typePercussion,e);
    }

    private class InstruParams
    {
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
    }

}
