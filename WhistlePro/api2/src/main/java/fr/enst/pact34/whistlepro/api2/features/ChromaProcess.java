package fr.enst.pact34.whistlepro.api2.features;

import java.util.Arrays;

import fr.enst.pact34.whistlepro.api2.classification.FeatureProviderInterface;
import fr.enst.pact34.whistlepro.api2.common.transformers;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;


public class ChromaProcess implements FeatureProviderInterface, StreamProcessInterface<Spectrum,Signal>
{
    private final int NB_CHROMAS = 24;
    private double minFrequency = 0, maxFrequency = 3500;

    private double[] midis = new double[12];
    private double[][] filters = null;

    public ChromaProcess() {
        reset();
    }

    public int countFeatures() { return NB_CHROMAS; }

    private double[][] getFilters() {
        if(filters == null) {
            int maxFilters = 10;
            int filterSize = 10;
            for(int i=0; i<maxFilters; ++i) {
                filters[i] = new double[filterSize];
                for(int j=0; j<filterSize; ++j) {

                }
            }

        }
        return filters;
    }


    public static double freq2midi(double freq) { return 69 + 12*Math.log(freq/440.); }
    public static double midi2freq(double midi) { return Math.exp((midi-69)/12)*440; }

    public void process(Spectrum sigIn, Signal sigOut)
    {


        /*
        System.arraycopy(mfcc,0,lastMfcc,0,NB_VAL_MFCC);
        System.arraycopy(newDmfcc,0,lastDMfcc,0,NB_VAL_MFCC);


        sigOut.setLength(COUNT_MFCC_TOTAL);
        sigOut.fromArray(mfcc, 0, NB_VAL_MFCC);
        sigOut.fromArray(newDmfcc, 1*NB_VAL_MFCC, NB_VAL_MFCC);
        sigOut.fromArray(newDDmfcc, 2*NB_VAL_MFCC, NB_VAL_MFCC);


        sigOut.setSamplingFrequency(sigIn.getFs());*/

    }

    @Override
    public void reset() {
    }


}
