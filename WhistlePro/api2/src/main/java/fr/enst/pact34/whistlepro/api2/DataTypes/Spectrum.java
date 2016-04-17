package fr.enst.pact34.whistlepro.api2.dataTypes;

import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

import java.util.Arrays;

/**
 * Created by Mohamed  on 22/02/16.
 */
public class Spectrum implements StreamDataInterface<Spectrum> {

    private double[] values = new double[0];
    private double fs = -1;
    private int nbPtsSig = -1; //nombre de pts du signal sur lequel on a calculé la fft

    public double[] getSpectrumValues() {
        return values.clone();
    }


    public double getFs() {
        return fs;
    }

    public int getNbPtsSig() {
        return nbPtsSig;
    }


    public void setFs(double fs) {
        this.fs = fs;
    }

    public void setLength(int l) {
        values = new double[l];
    }

    public int length() {
        return values.length;
    }

    public void setValue(int i, double v) {
        values[i]=v;
    }

    public void setNbPtsSig(int nbPtsSig) {
        this.nbPtsSig = nbPtsSig;
    }

    @Override
    public void copyTo(Spectrum spectrum) {
        spectrum.setLength(this.values.length);
        spectrum.values = Arrays.copyOf(this.values,this.values.length);
        spectrum.fs=this.fs;
        spectrum.nbPtsSig=this.nbPtsSig;
        spectrum.id=this.id;
        spectrum.valid=this.valid;
    }

    @Override
    public Spectrum getNew() {
        //TODO
        Spectrum s = new Spectrum();
        return s;
    }

    public double getValue(int i) {
        return values[i];
    }


    int id = -1;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


    boolean valid = true;

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setValid(boolean v) {
        valid =v;
    }
}
