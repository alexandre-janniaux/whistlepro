package fr.enst.pact34.whistlepro.api.common;

/**
 * Created by Mohamed  on 22/02/16.
 */
public class Spectrum {

    private double[] values;
    private double fs;
    private double nbPtsSig; //nombre de pts du signal sur lequel on a calculé la fft

    public Spectrum(double nbPtsSig, double fs, double[] values) {
        this.nbPtsSig = nbPtsSig;
        this.values = values;
        this.fs = fs;
    }

    public double[] getSpectrumValues() {
        return values.clone();
    }


    public double getFs() {
        return fs;
    }

    public double getNbPtsSig() {
        return nbPtsSig;
    }
}
