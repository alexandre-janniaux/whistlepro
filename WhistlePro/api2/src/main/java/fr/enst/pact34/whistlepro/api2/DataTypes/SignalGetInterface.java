package main.java.fr.enst.pact34.whistlepro.api2.DataTypes;

/**
 * Created by mms on 14/03/16.
 */
public interface SignalGetInterface {

    /**
     * Get the value of the signal at the point i.
     * @param i index of the point to get (starts at 0)
     * @return the value at index i
     */
    double getValue(int i);

    /**
     * Get the number of point in the signal
     * @return the number of point
     */
    int length();

    /**
     * Get the sample frequency of the signal
     * @return sampling frequency
     */
    double getSamplingFrequency();

}
