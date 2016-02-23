package features;

import java.util.ArrayList;
import java.lang.Math;

import classification.FeatureProviderInterface;
import common.Spectrum;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.transform.FastCosineTransformer;
import org.apache.commons.math3.transform.DctNormalization;
import org.apache.commons.math3.transform.TransformType;

public class MfccFeatureProvider implements FeatureProviderInterface
{

    // TODO: getter/setter for frequencies and mel filters;
    private static final int nbMelFilter = 24;
    private static final int minFrequency=0, maxFrequency=3500;
    private int samplingFrequency=16000;

    public static double frequencyToMel(double frequency) 
    {
        return 1125*Math.log(1+frequency/700.);
    }

    public static double melToFrequency(double mel)
    {
        return 700*(Math.exp(mel/1125)-1);
    }

    public int countFeatures() 
    {
        // MFCC + d(MFCC) + dd(MFCC)
        return 13; //+ 13 + 13;
    }

    public static double[] computeFilterFrequencies(double minFrequency, double maxFrequency)
    {
        double minMel = frequencyToMel(minFrequency);
        double maxMel = frequencyToMel(maxFrequency);
        double stepMel = (maxMel-minMel)/(nbMelFilter+1) ;

        double[] frequencies = new double[nbMelFilter+2]; 
        for(int i=0; i < nbMelFilter+2; ++i)
        {
            frequencies[i] = melToFrequency(minMel+i*stepMel);
        }
        return frequencies;
 
    }

    public static double[] computeTriangleFilter(double[] echelleFreq, double fmin, double fcenter, double fmax)
    {
        double filter[] = new double[echelleFreq.length];

        double a1 = 1.0/(fcenter-fmin);
        double b1 = -fmin*a1;

        double a2 = -1.0/(fmax-fcenter);
        double b2 = -fmax*a2;

        for(int i = 0; i < echelleFreq.length; i++)
        {
            if(echelleFreq[i] <= fmin || echelleFreq[i]>= fmax)
            {
                filter[i] = 0;
            }
            else
            {
                if(echelleFreq[i] <= fcenter )
                {
                    filter[i] = a1*echelleFreq[i]+b1;
                }
                else
                {
                    filter[i] = a2*echelleFreq[i]+b2;
                }
            }
        }

        return filter;
    }

    public static  double[][] computeFilterBank(double[] frequencies, double[] echelleFreq)
    {
        double filters[][] = new double[frequencies.length-2][];

        for(int i = 1; i < frequencies.length-1; i++)
        {
            filters[i-1] = computeTriangleFilter(echelleFreq,frequencies[i-1],frequencies[i],frequencies[i+1]);
        }

        return filters;
    }

    public static double[] applyFilter(double[] signal, double[] filter)
    {
        double output[]= new double[signal.length];
       //TODO : pour l'instant on suppose que signal et filter ont la meme taille
        for(int i = 0; i < signal.length; i ++)
        {
            output[i]=signal[i]*filter[i];
        }

        return output;
    }

    public static double[] computeMelSpectrum(double[] signal, double[] echelleFreq)
    {
        double[] output = new double[nbMelFilter];
        double[][] filters = computeFilterBank(computeFilterFrequencies(minFrequency, maxFrequency),echelleFreq);


        for(int i=0; i < filters.length ; ++i)
        {
            output[i] = computePower(applyFilter(signal,filters[i]));
        }
        return output;
    }

    //////////////////////////////
    /// @brief compute power of a signal, which is the sum of the values of the signal
    //////////////////////////////
    public static double computePower(double[] signal)
    {
        double power = 0;
        for(double x : signal)
        {
            power += x;
        }
        return power;
    }


    public static ArrayList<Double> processMfcc(Spectrum spectrumIn)
    {
        //limit to 3500 Hz
        int iMax = (int)(Math.round(3500*spectrumIn.getNbPtsSig()/spectrumIn.getFs())+1);
        double fftC[] = new double[iMax];
        double[] spectrum = spectrumIn.getSpectrumValues();
        for(int i = 0; i < iMax; i++) fftC[i]=spectrum[i];

        // COMPUTE THE POWER SPECTRUM
        for(int i=0; i<fftC.length; ++i)
            fftC[i] = fftC[i]*fftC[i];

        spectrumIn = new Spectrum(spectrumIn.getNbPtsSig(),spectrumIn.getFs(),fftC);

        // FILTERING
        double[] filtered = computeMelSpectrum(spectrumIn.getSpectrumValues(), spectrumIn.getSpectrumScale());

        for(int i = 0; i < filtered.length; i++)
        {
            filtered[i] = Math.log(filtered[i]);
        }

        double mfcc[] = dct(filtered);
        
        ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.ensureCapacity(13);
        for(int i=0; i<13; ++i)
            coeffs.add(mfcc[i]);

        return coeffs;
    }

    private static double[] dct(double[] signal) {
        int N = signal.length;
        double dct[] = new double[N];
        dct[0] = 0;
        for(int n = 0; n < N; n++)
        {
            dct[0] += signal[n]* 1;//1=cos(0)=>Math.cos((Math.PI*(2*n+1)*k)/(2*N)) k = 0;
        }
        dct[0]= dct[0]/Math.sqrt(N);

        for(int k = 1; k < N ; k++)
        {
            dct[k] = 0;
            for(int n = 0; n < N; n++)
            {
                dct[k] += signal[n]*Math.cos((Math.PI*(2*n+1)*k)/(2*N));
            }
            dct[k]= (dct[k]*Math.sqrt(2))/Math.sqrt(N);
        }
        return dct;
    }

}
