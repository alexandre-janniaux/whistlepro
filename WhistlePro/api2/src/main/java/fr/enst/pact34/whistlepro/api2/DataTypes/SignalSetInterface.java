package main.java.fr.enst.pact34.whistlepro.api2.dataTypes;

/**
 * Created by mms on 14/03/16.
 */
public interface SignalSetInterface extends SignalGetInterface {

    /**
     * Set the value v to the point number i in the signal
     * @param i index of the point to change (start from 0)
     * @param v value to set
     */
    void setValue(int i, double v);

    /**
     * Set the new length of the signal
     * the signal
     * @param newLength of signal
     */
    void setLength(int newLength);

    /**
     * Set the new sampling frequency of the signal
     * @param Fs sampling frequency
     */
    void setSamplingFrequency(double Fs);

}
