package features;

import common.ConvolutionInterface;
import common.Convolution1D;
import common.TriangleConvolution;
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
    private int minFrequency=300, maxFrequency=8000;
    private int samplingFrequency=16000;

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

    public int[] computeFilterFrequencies(int minFrequency, int maxFrequency)
    {
        double minMel = frequencyToMel(minFrequency);
        double maxMel = frequencyToMel(maxFrequency);
        double stepMel = (maxMel-minMel)/this.nbMelFilter;

        int[] frequencies = new int[this.nbMelFilter];
        frequencies[0] = minFrequency;
        for(int i=1; i <= this.nbMelFilter+1; ++i)
        {
            frequencies[i] = (int) melToFrequency(minMel+i*stepMel);
        }
        return frequencies;
 
    }

    public TriangleConvolution[] computeFilterBank(int[] frequencies)
    {
        TriangleConvolution[] filters = new TriangleConvolution[frequencies.length-1];
        for(int i=0; i<frequencies.length-1; ++i)
        {
            filters[i] = new TriangleConvolution(frequencies[i], frequencies[i+1], frequencies[i+2]);
        }
        return filters;
    }

    public double[] computeFilter(double[] signal)
    {
        double[] output = new double[this.nbMelFilter];
        int[] frequencies = computeFilterFrequencies(this.minFrequency, this.maxFrequency);
        TriangleConvolution[] filters = computeFilterBank(frequencies);
        
        for(int i=0; i < this.nbMelFilter; ++i)
        {
            // double f_d = Math.floor((signal.length+1)*f/this.samplingFrequency);

            output[i] = computePower(filters[i].convoluate(signal, 0, signal.length));
        }
        return output;
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
