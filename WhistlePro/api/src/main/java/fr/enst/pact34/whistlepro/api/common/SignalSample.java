package fr.enst.pact34.whistlepro.api.common;

/**
 * Created by mms on 05/03/16.
 */
public class SignalSample {

    public double[] signal ;
    public double Fs;

    public SignalSample(double fs, double[] signal) {
        Fs = fs;
        this.signal = signal;
    }

    public double[] getSignal() {
        return signal;
    }

    public double getFs() {
        return Fs;
    }
}
