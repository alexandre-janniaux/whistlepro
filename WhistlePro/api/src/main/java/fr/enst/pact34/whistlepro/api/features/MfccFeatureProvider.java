package fr.enst.pact34.whistlepro.api.features;

import java.util.ArrayList;
import java.lang.Math;

import fr.enst.pact34.whistlepro.api.classification.FeatureProviderInterface;
import fr.enst.pact34.whistlepro.api.common.Spectrum;
import fr.enst.pact34.whistlepro.api.common.transformers;


public class MfccFeatureProvider implements FeatureProviderInterface
{
    private final int nbMelFilter = 24;
    private double minFrequency = 0, maxFrequency = 3500;

    public int countFeatures() 
    {
        // MFCC + d(MFCC) + dd(MFCC)
        return 13; //+ 13 + 13;
    }

    public double[] applyFilter(double[] signal, double[] filter)
    {
        double output[]= new double[signal.length];
       //TODO : pour l'instant on suppose que signal et filter ont la meme taille
        for(int i = 0; i < signal.length; i ++)
        {
            output[i]=signal[i]*filter[i];
        }

        return output;
    }

    public double[] computeMelSpectrum(double[] signal, double[] echelleFreq)
    {
        double[] output = new double[nbMelFilter];
        double[][] filters = FilterBankMel.computeFilterBank(minFrequency, maxFrequency,nbMelFilter,echelleFreq);


        for(int i=0; i < filters.length ; ++i)
        {
            output[i] = computePower(applyFilter(signal,filters[i]));
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


    public ArrayList<Double> processMfcc(Spectrum spectrumIn)
    {
        //limit to maxFrequency (3500 Hz)
        int iMax = (int)(Math.round(maxFrequency*spectrumIn.getNbPtsSig()/spectrumIn.getFs())+1);
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

        double mfcc[] = transformers.dct(filtered);
        
        ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.ensureCapacity(13);
        for(int i=0; i<13; ++i)
            coeffs.add(mfcc[i]);

        return coeffs;
    }


}
