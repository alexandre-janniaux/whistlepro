package features;

import common.ConvolutionInterface;
import common.Convolution1D;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import org.apache.commons.math3.transform.FastCosineTransformer;
import org.apache.commons.math3.transform.DctNormalization;
import org.apache.commons.math3.transform.TransformType;

public class MfccFeatureProvider 
{

    // TODO: getter/setter for frequencies and mel filters;
    private int nbMelFilter = 24;
    private double minFrequency=300., maxFrequency=8000.;
    private double samplingFrequency=16000.;

    public double frequencyToMel(double frequency) 
    {
        return 1125*Math.log(1+frequency/700.);
    }

    public double melToFrequency(double mel)
    {
        return 700*(Math.exp(mel/1125)-1);
    }

    public int countFeatures() 
    {
        // MFCC + d(MFCC) + dd(MFCC)
        return 13; //+ 13 + 13;
    }

    public double[][] computeFilterBank()
    {
        return null;
    }

    public double[] computeFilter(double[] signal)
    {
        double[] output = new double[this.nbMelFilter];
        double minMel = frequencyToMel(this.minFrequency);
        double maxMel = frequencyToMel(this.maxFrequency);
        double stepMel = (maxMel-minMel)/this.nbMelFilter;

        for(int i=0; i < this.nbMelFilter; ++i)
        {
            double f = melToFrequency(minMel+i*stepMel);
            double f_d = Math.floor((signal.length+1)*f/this.samplingFrequency);
            double[] kernel = new double[6];
            int shift = 0;

            ConvolutionInterface filter = new Convolution1D(shift, kernel);
            output[i] = computePower(filter.convoluate(signal, 0, signal.length));
        }

        return signal;
    }

    //////////////////////////////
    /// @brief compute power of a signal, which is the sum of the values of the signal
    //////////////////////////////
    public double computePower(double[] signal)
    {
        double power = 0;
        for(double x : signal)
        {
            power += x;
        }
        return power;
    }

    public ArrayList<Double> processMfcc(double[] spectrum)
    {
        // COMPUTE THE POWER SPECTRUM
        for(int i=0; i<spectrum.length; ++i)
            spectrum[i] = spectrum[i]*spectrum[i]; 
        
        // FILTERING
        double[] filtered = this.computeFilter(spectrum);

        for(Double x : filtered) x = Math.log(x);

        // DCT TRANSFORM
        FastCosineTransformer dct = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);

        double mfcc[] = dct.transform(filtered, TransformType.FORWARD);
        
        ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.ensureCapacity(13);
        for(int i=0; i<13; ++i)
            coeffs.add(mfcc[i]);

        return coeffs;
    }

}
