package fr.enst.pact34.whistlepro.api.common;


public class DoubleSignal2D implements DoubleSignal2DInterface {

    double[][] signal;
    int nbPoints;
    double frequency;
    int bufferId;

    public DoubleSignal2D(double[][] signal, int nbPoints, double frequency) {
        this.signal = signal;
        this.nbPoints = nbPoints;
        this.frequency = frequency;
    }

    @Override
    public int getBufferId() { return this.bufferId; }

    @Override
    public void setBufferId(int bufferId) { this.bufferId = bufferId; }

    @Override
    public double[][] getSignal() { return signal; }

    @Override
    public double getFrequencySample() { return frequency; }

    @Override
    public int getNbPoints() {
        return nbPoints;
    }

    @Override
    public void setSignal(double[][] signal) {
        this.signal = signal;
    }

    @Override
    public void setNbPoints(int nbPoints) { this.nbPoints = nbPoints; }

    @Override
    public void setFrequencySample(double frequency) { this.frequency = frequency; }
}
