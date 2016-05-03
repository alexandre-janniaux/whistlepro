package fr.enst.pact34.whistlepro.api2.features;

import fr.enst.pact34.whistlepro.api2.classification.FeatureProviderInterface;
import fr.enst.pact34.whistlepro.api2.common.transformers;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

import java.util.Arrays;


public class MfccProcess implements FeatureProviderInterface, StreamProcessInterface<Spectrum,Signal>
{

    private final int NB_VAL_MFCC = 13;
    private final int COUNT_MFCC_TOTAL = NB_VAL_MFCC*3;
    private final int nbMelFilter = 24;
    private double minFrequency = 0, maxFrequency = 3500;

    private double[] lastMfcc = new double[NB_VAL_MFCC];
    private double[] lastDMfcc = new double[NB_VAL_MFCC];



    public MfccProcess() {
        reset();
    }

    public int countFeatures()
    {
        // MFCC + d(MFCC) + dd(MFCC)
        return COUNT_MFCC_TOTAL;
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

    double[] newDmfcc = new double[NB_VAL_MFCC];
    double[] newDDmfcc = new double[NB_VAL_MFCC];

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

        for (int i = 0; i < NB_VAL_MFCC; i++) {
            newDmfcc[i] = mfcc[i] - lastMfcc[i];
        }
        for (int i = 0; i < NB_VAL_MFCC; i++) {
            newDDmfcc[i] = newDmfcc[i] - lastDMfcc[i];
        }

        System.arraycopy(mfcc,0,lastMfcc,0,NB_VAL_MFCC);
        System.arraycopy(newDmfcc,0,lastDMfcc,0,NB_VAL_MFCC);


        sigOut.setLength(COUNT_MFCC_TOTAL);
        sigOut.fromArray(mfcc, 0, NB_VAL_MFCC);
        sigOut.fromArray(newDmfcc, 1*NB_VAL_MFCC, NB_VAL_MFCC);
        sigOut.fromArray(newDDmfcc, 2*NB_VAL_MFCC, NB_VAL_MFCC);


        sigOut.setSamplingFrequency(sigIn.getFs());
    }

    @Override
    public void reset() {
        for (int i = 0; i < lastMfcc.length; i++) {
            lastMfcc[i]= 0;
        }
        for (int i = 0; i < lastDMfcc.length; i++) {
            lastDMfcc[i]= 0;
        }
    }


}
