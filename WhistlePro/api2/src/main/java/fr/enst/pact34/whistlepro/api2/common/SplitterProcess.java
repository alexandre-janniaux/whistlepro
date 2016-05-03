package fr.enst.pact34.whistlepro.api2.common;

import java.util.Arrays;
import java.util.LinkedList;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 17/04/16.
 */
public class SplitterProcess implements StreamProcessInterface<LinkedList<double[]>,LinkedList<Signal>> {

    int nbSamples ;
    int halfSamples ;
    private double fs;

    public SplitterProcess(int nbSamples, double fs) {
        this.nbSamples = nbSamples;
        halfSamples = nbSamples/2;
        this.fs = fs;
        splitBuffer = new double[nbSamples];

    }

    private double[] splitBuffer ; // used to keep last 10 sec for next sample
    private int iSB = 0;

    @Override
    public void process(LinkedList<double[]> inputData, LinkedList<Signal> outputData) {

        for (int i = 0; i < inputData.size(); i++) {
            double[] tmp = inputData.get(i);
            for(int j = 0; j < tmp.length; j++)
            {
                splitBuffer[iSB++] = tmp[j];
                if(iSB==splitBuffer.length)
                {
                    Signal s = new Signal();
                    s.setSamplingFrequency(fs);
                    s.fromArray(splitBuffer);
                    outputData.add(s);
                    int k = (int)Math.floor(splitBuffer.length/2);
                    System.arraycopy(splitBuffer,k,splitBuffer,0,k);
                    iSB=k;
                }
            }
        }
    }

    @Override
    public void reset() {
        iSB = 0;
    }
}
