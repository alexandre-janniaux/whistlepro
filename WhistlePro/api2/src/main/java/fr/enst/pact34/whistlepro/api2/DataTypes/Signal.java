package main.java.fr.enst.pact34.whistlepro.api2.dataTypes;

import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by  Mohamed on 14/03/16.
 */

public class Signal implements SignalGetInterface,SignalSetInterface,StreamDataInterface<Signal> {

    @Override
    public void setValue(int i, double v) {

    }

    @Override
    public void setLength(int newLength) {

    }

    @Override
    public void setSamplingFrequency(double Fs) {

    }

    @Override
    public double getValue(int i) {
        return 0;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public double getSamplingFrequency() {
        return 0;
    }

    @Override
    public void copyTo(Signal signal) {

    }
}
