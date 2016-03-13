package fr.enst.pact34.whistlepro.api.common;

/**
 * Created by alexandre on 06/03/16.
 */
public class DoubleSignal2D implements DoubleSignal2DInterface {


    double[][] signal;
    final int nbPoints;
    final double frequency;

    public DoubleSignal2D(double[][] signal, int nbPoints, double frequency) {
        this.signal = signal;
        this.nbPoints = nbPoints;
        this.frequency = frequency;
    }

    @Override
    public double[][] getSignal() {
        return signal;
    }

    @Override
    public double getFrequencySample() {
        return frequency;
    }

    @Override
    public int getNbPoints() {
        return nbPoints;
    }

    @Override
    public void setSignal(double[][] signal) {
        this.signal = signal;
    }
}
