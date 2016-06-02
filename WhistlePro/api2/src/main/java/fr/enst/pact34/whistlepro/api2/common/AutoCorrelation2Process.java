package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;



public class AutoCorrelation2Process implements StreamProcessInterface<Signal,Frequency> {

    private final int nbSample;
    private int Fs;

    public AutoCorrelation2Process(int Fs, int nbSample)
    {
        this.nbSample=nbSample;
        this.Fs=Fs;
    }

    @Override
    public void process(Signal inputData, Frequency outputData) {

        double freq = autocorrelate(inputData);

        outputData.setFrequency(freq);
    }

    public double autocorrelate(Signal inputData) {
        double sampleRate = inputData.getSamplingFrequency();
        int SIZE = inputData.length();
        int MAX_SAMPLES = (int) Math.floor(SIZE / 2);
        int best_offset = -1;
        double best_correlation = 0;
        double rms = 0;
        boolean foundGoodCorrelation = false;
        double[] correlations = new double[MAX_SAMPLES];

        for (int i = 0; i < inputData.length(); i++) {
            double val = inputData.getValue(i);
            rms += val * val;
        }
        rms = Math.sqrt(rms / SIZE);
        if (rms < 0.01) // not enough signal
            return -1;

        double lastCorrelation = 1;
        for (int offset = 0; offset < MAX_SAMPLES; offset++) {
            double correlation = 0;

            for (int i = 0; i < MAX_SAMPLES; i++) {
                correlation += Math.abs(inputData.getValue(i) - (inputData.getValue(i + offset)));
            }
            correlation = 1 - (correlation / MAX_SAMPLES);
            correlations[offset] = correlation; // store it, for the tweaking we need to do below.
            if ((correlation > 0.9) && (correlation > lastCorrelation)) {
                foundGoodCorrelation = true;
                if (correlation > best_correlation) {
                    best_correlation = correlation;
                    best_offset = offset;
                }
            } else if (foundGoodCorrelation) {
                // short-circuit - we found a good correlation, then a bad one, so we'd just be seeing copies from here.
                // Now we need to tweak the offset - by interpolating between the values to the left and right of the
                // best offset, and shifting it a bit.  This is complex, and HACKY in this code (happy to take PRs!) -
                // we need to do a curve fit on correlations[] around best_offset in order to better determine precise
                // (anti-aliased) offset.

                // we know best_offset >=1,
                // since foundGoodCorrelation cannot go to true until the second pass (offset=1), and
                // we can't drop into this clause until the following pass (else if).
                double shift = (correlations[best_offset + 1] - correlations[best_offset - 1]) / correlations[best_offset];
                return sampleRate / (best_offset + (8 * shift));
            }
            lastCorrelation = correlation;
        }
        if (best_correlation > 0.01) {
            // console.log("f = " + sampleRate/best_offset + "Hz (rms: " + rms + " confidence: " + best_correlation + ")")
            return sampleRate / best_offset;
        }
        return -1;
    }

    @Override
    public void reset() {

    }
}
