package fr.enst.pact34.whistlepro.api.test.stream;
import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.common.*;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;
import fr.enst.pact34.whistlepro.api.stream.SpectrumStream;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by  Mohamed on 11/03/16.
 */
public class SpectrumMfccStreamTest {

    @Test
    public void spectrumAndMfccStreamTest() {

        FakeAcquiStream acquiStream = new FakeAcquiStream();
        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();
        SpectrumStream spectrumStream = new SpectrumStream();
        FakeReceiverMFCC fakeReceiverMFCC = new FakeReceiverMFCC();

        acquiStream.subscribe(spectrumStream);

        spectrumStream.subscribe(mfccFeatureStream);

        mfccFeatureStream.subscribe(fakeReceiverMFCC);

        acquiStream.start();

        double[] coef = fakeReceiverMFCC.getData();

        ArrayList<String> lines = FileOperator.getLinesFromFile("../testData/features/a_matlab.mfcc");

        assertEquals(lines.size(),1);

        String[] line = lines.get(0).split(";");

        assertEquals(13,line.length);

        for(int i = 0; i < coef.length; i ++)
        {

            double tmp = Double.parseDouble(line[i]);
            if(tmp != 0)
                assertEquals(tmp,coef[i],Math.abs(tmp*0.001));
            else
                assertEquals(tmp,coef[i],1e-14);
        }
    }


    public static class FakeReceiverMFCC implements DataListenerInterface<DoubleSignal2DInterface>
    {

        private ArrayList<double[]> mfccs = new ArrayList<>();

        public double[] getData()
        {
            if(mfccs.size()>0) return mfccs.remove(0);
            return null;
        }

        @Override
        public void onPushData(DataSource<DoubleSignal2DInterface> source, DoubleSignal2DInterface inputData) {
            double[][] signal = inputData.getSignal();

            for(int j = 0; j < signal.length; j++) {
                mfccs.add(signal[j]);
            }
        }

    }


    public static  class FakeAcquiStream extends DataSource<DoubleSignal2DInterface> {
        private void start() {

            try {
                WavFile readWavFile = WavFile.openWavFile(new File("../testData/features/a.wav"));
                double Fs = readWavFile.getSampleRate();
                double Ts = 1.0 / readWavFile.getSampleRate();
                double Tps = 100e-3;
                double TStart = 20e-3;
                int nbPts = (int) (Tps / Ts);
                int nbPtsIgnore = (int) (TStart / Ts);

                //reading ignore part
                double[] buffer1 = new double[nbPtsIgnore];
                readWavFile.readFrames(buffer1, nbPtsIgnore);
                buffer1 = null;

                //reading useful part
                double[][] buffer = new double[1][nbPts];

                readWavFile.readFrames(buffer, nbPts)  ;

                readWavFile.close();


                DoubleSignal2DInterface outputData = new DoubleSignal2D(
                        buffer,
                        nbPts,
                        Fs
                );


                this.push(outputData);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (WavFileException e) {
                e.printStackTrace();
            }
        }
    }

}
