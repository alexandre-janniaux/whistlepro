package fr.enst.pact34.whistlepro.pcapp.interfaces.adapters;


public interface CurveAdapterInterface {

    boolean isFinite();

    double getValue(int i);

    double getAbscisse(int i);

    int getNbPoints();

    int getMaxAmplitude();
}
