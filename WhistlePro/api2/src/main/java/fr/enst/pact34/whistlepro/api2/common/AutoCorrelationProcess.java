package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 23/04/16.
 */
public class AutoCorrelationProcess implements StreamProcessInterface<Signal,Frequency> {

    private final int nbSample;
    private int Fs;
    double[] res ;

    public AutoCorrelationProcess(int Fs, int nbSample)
    {
        this.nbSample=nbSample;
        this.Fs=Fs;
        this.res = new double[nbSample];
    }

    @Override
    public void process(Signal inputData, Frequency outputData) {

        //* Auto-correlation

       double max = Double.MAX_VALUE;
       int i_max = 0;

       for (int i = 0; i < res.length; i++) {
           res[i] = 0;
           for (int j = 0; j < res.length  ; j++) {
               res[i]+=inputData.getValue(j)*inputData.getValue((i+j)%res.length);
           }
           res[i]/=(i);
           if(i > 0 && res[i]> max && res[i]> res[i-1]) {
               max =res[i];
               i_max= i;
           }
       }

       double freq = -1;
       if(i_max>0) freq= inputData.getSamplingFrequency()/i_max;

        outputData.setFrequency(freq);
    }

    @Override
    public void reset() {

    }
}
