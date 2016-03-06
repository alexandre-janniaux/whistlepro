package fr.enst.pact34.whistlepro.api.test.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.api.common.Spectrum;
import fr.enst.pact34.whistlepro.api.common.transformers;
import org.junit.Test;
import fr.enst.pact34.whistlepro.api.features.MfccFeatureProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MfccFeatureProviderTest {

    @Test
    public void numberOfFeatures() {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        assertEquals(13, mfcc.countFeatures());
    }


    @Test
    public void computePower()
    {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        double[] signal = {0,1.,2.,3.,4.,5.,6.,7.};
        assertTrue(mfcc.computePower(signal) - 28. <= Double.MIN_VALUE);
    }


    @Test
    public void mfcc() {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();

        try {
            WavFile readWavFile = WavFile.openWavFile(new File("../testData/features/a.wav"));
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

            Spectrum sp = new Spectrum(nbPts,Fs,fft);

            double[] coef = mfcc.processMfcc(sp);


            ArrayList<String> lines = FileOperator.getLinesFromFile("../testData/features/a_matlab.mfcc");

            assertEquals(lines.size(),1);

            String[] line = lines.get(0).split(";");

            assertEquals(13,line.length);

            for(i = 0; i < coef.length; i ++)
            {

                double tmp = Double.parseDouble(line[i]);
                if(tmp != 0)
                    assertEquals(tmp,coef[i],Math.abs(tmp*0.001));
                else
                    assertEquals(tmp,coef[i],1e-14);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (WavFileException e) {
            e.printStackTrace();
        }


    }


}
