package fr.enst.pact34.whistlepro.api.common;


public interface DoubleSignalInterface {

    double[] getSignal();

    int getNbSamples();

    double getSampleFrequency();

    int getBufferId();

    void setBufferId(int bufferId);
}


