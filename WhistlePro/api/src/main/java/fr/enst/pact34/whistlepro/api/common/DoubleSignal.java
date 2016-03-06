package fr.enst.pact34.whistlepro.api.common;


public class DoubleSignal implements DoubleSignalInterface {

    private final double[] signal;
    private final int nbSamples;
    private final double sampleFrequency;

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

}
