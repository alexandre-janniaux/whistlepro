package fr.enst.pact34.whistlepro.api.common;

/**
 * Created by alexandre on 06/03/16.
 */
public interface DoubleSignal2DInterface {

    double[][] getSignal();

    double getFrequencySample();

    int getNbPoints();
}
