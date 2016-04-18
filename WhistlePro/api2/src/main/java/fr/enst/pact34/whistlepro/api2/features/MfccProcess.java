package fr.enst.pact34.whistlepro.api2.features;

import fr.enst.pact34.whistlepro.api2.classification.FeatureProviderInterface;
import fr.enst.pact34.whistlepro.api2.common.transformers;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

import java.util.Arrays;


public class MfccProcess implements FeatureProviderInterface, StreamProcessInterface<Spectrum,Signal>
{
    private final int nbMelFilter = 24;
    private double minFrequency = 0, maxFrequency = 3500;
    private double[][] lastMfcc = new double[10][13];
    private double[][] lastDMfcc = new double[10][13];

    private double[] dmfcc = new double[13];
    private double[] ddmfcc = new double[13];

    private int mfcc_index = 0;
    private int dmfcc_index = 0;

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

    private double lastFs = -1 , lastN = -1;
    private double[][] filters =null;

    public double[] computeMelSpectrum(double[] fft, double Fs, double N)
    {
        double[] output = new double[nbMelFilter];

        if(lastFs!=Fs || lastN!=N)
        {
            double[] echelleFreq = new double[fft.length];
            for (int i = 0; i < fft.length; i++) {
                echelleFreq[i] = i * Fs / N;
            }
            filters = FilterBankMel.computeFilterBank(minFrequency, maxFrequency,nbMelFilter,echelleFreq);
        }

        lastN = N;
        lastFs = Fs;

        for(int i=0; i < filters.length ; ++i)
        {
            output[i] = computePower(applyFilter(fft,filters[i]));
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


    public void process(Spectrum sigIn, Signal sigOut)
    {
        //limit to maxFrequency (3500 Hz)
        int iMax = (int)(Math.round(maxFrequency*sigIn.getNbPtsSig()/sigIn.getFs())+1);
        double fftC[] = Arrays.copyOf(sigIn.getSpectrumValues(),iMax);

        // COMPUTE THE POWER SPECTRUM
        for(int i=0; i<fftC.length; ++i)
            fftC[i] = fftC[i]*fftC[i];

        //spectrumIn = new Spectrum(spectrumIn.getNbPtsSig(),spectrumIn.getFs(),fftC);

        // FILTERING
        double[] filtered = computeMelSpectrum(fftC, sigIn.getFs(), sigIn.getNbPtsSig());

        for(int i = 0; i < filtered.length; i++)
        {
            filtered[i] = Math.log(filtered[i]);
        }

        double mfcc[] = transformers.dct(filtered, 13);

        System.arraycopy(mfcc, 0, lastMfcc[mfcc_index], 0, 13);
        for (int i=0; i<lastMfcc.length; ++i) {
            for(int j=0; j<13; ++j) {
                dmfcc[j] += (i < lastMfcc.length / 2 ? (-1) : 1) * lastMfcc[(i + mfcc_index) % lastMfcc.length][j];
            }
        }
        System.arraycopy(dmfcc, 0, lastDMfcc[dmfcc_index], 0, 13);

        for (int i=0; i<lastDMfcc.length; ++i) {
            for(int j=0; j<13; ++j) {
                ddmfcc[j] += (i < lastDMfcc.length / 2 ? (-1) : 1) * lastDMfcc[(i + dmfcc_index) % lastDMfcc.length][j];
            }
        }

        mfcc_index += 1;
        dmfcc_index += 1;

        sigOut.setLength(39);
        sigOut.fromArray(mfcc, 0, 13);
        sigOut.fromArray(dmfcc, 13, 13);
        sigOut.fromArray(ddmfcc, 26, 13);


        sigOut.setSamplingFrequency(sigIn.getFs());
    }


}
