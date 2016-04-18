package fr.enst.pact34.whistlepro.api.common;


public class DoubleSignal implements DoubleSignalInterface {

    private double[] signal;
    private int nbSamples;

    public void setNbSamples(int nbSamples) {
        this.nbSamples = nbSamples;
    }

    public void setSampleFrequency(double sampleFrequency) {
        this.sampleFrequency = sampleFrequency;
    }

    private double sampleFrequency;

    public DoubleSignal(double[] signal, int nbSamples, double sampleFrequency) {
        this.signal = signal;
        this.nbSamples = nbSamples;
        this.sampleFrequency = sampleFrequency;
    }

    public double[] getSignal() {
        return signal;
    }

    public int getNbSamples() {
        return nbSamples;
    }

    public double getSampleFrequency() {
        return sampleFrequency;
    }

    public void setSignal(double[] signal) { this.signal = signal; }

    @Override
    public int getBufferId() {
        return 0;
    }

    @Override
    public void setBufferId(int bufferId) {

    }

}
