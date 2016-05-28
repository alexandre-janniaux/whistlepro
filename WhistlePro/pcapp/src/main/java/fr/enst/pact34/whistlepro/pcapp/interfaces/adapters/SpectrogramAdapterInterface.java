package fr.enst.pact34.whistlepro.pcapp.interfaces.adapters;


public interface SpectrogramAdapterInterface {

    // returns whether the signal has infinite samples or not
    public boolean isFinite();

    // return the norm of TFST(window, freq)
    public double getValue(int window, int freqNum);

    // return the frequency associated with freqNum (kFe/N)
    public double getFrequency(int freqNum);

    // return the number of windows in spectrum
    public int getNbWindows();

    // return the time length of a window
    public double getWindowLength();

    // return the max value of spectrum
    public double getMaxAmplitude();

    public int getNbFrequencies();
}
