package fr.enst.pact34.whistlepro.api.common;



public interface DoubleSignal2DInterface {

    int getBufferId();

    void setBufferId(int bufferId);

    double[][] getSignal();

    double getFrequencySample();

    int getNbPoints();

    void setSignal(double[][] signal);

    void setNbPoints(int nbPoints);

    void setFrequencySample(double frequency);
}
