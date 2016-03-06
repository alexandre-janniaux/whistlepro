package fr.enst.pact34.whistlepro.api.features;

import fr.enst.pact34.whistlepro.api.acquisition.AffichageFFT;
import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.classification.FeatureProviderInterface;
import fr.enst.pact34.whistlepro.api.common.Spectrum;
import fr.enst.pact34.whistlepro.api.common.transformers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mms on 22/02/16.
 */
public class tests {

    private static final String filename = "data/a.wav";

    public static void main(String[] args)
    {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        System.out.print(" a");


        try {
            WavFile readWavFile = WavFile.openWavFile(new File(filename));
            double Fs = readWavFile.getSampleRate();
            double Ts = 1.0/readWavFile.getSampleRate();
            double Tps = 100e-3;
            double Tstart =20e-3;
            int nbPts = (int)(Tps/Ts);
            int nbPtsIgnore =(int)(Tstart/Ts);

            //reading ignore part
            double[] buffer = new double[nbPtsIgnore];
            int i;
            readWavFile.readFrames(buffer,nbPtsIgnore);

            //reading useful part
            buffer = new double[nbPts];
            readWavFile.readFrames(buffer,nbPts);




            readWavFile.close();

            double[] fft = transformers.fft(buffer);

            for(i=0; i < fft.length; i++)
            {
                fft[i]=2*(fft[i]/fft.length);

            }


            Spectrum sp = new Spectrum(fft.length,Fs,fft);

            double[] coef = mfcc.processMfcc(sp);

            System.out.println();
            for(i = 0; i < coef.length; i ++)
            {
                System.out.print(coef[i] + ";");
            }

            System.out.println();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (WavFileException e) {
            e.printStackTrace();
        }


        System.out.print(" a");
    }
}
