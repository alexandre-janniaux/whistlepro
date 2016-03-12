package fr.enst.pact34.whistlepro.api.test.stream;
import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.classification.ArrayClassifier;
import fr.enst.pact34.whistlepro.api.common.*;
import fr.enst.pact34.whistlepro.api.stream.ClassificationStream;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;
import fr.enst.pact34.whistlepro.api.stream.SpectrumStream;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by  Mohamed on 11/03/16.
 */
public class SpectrumMfccClassifStreamTest {

    @Test
    public void spectrumMfccAndClassifStreamTest() {

        FakeAcquiStream acquiStream = new FakeAcquiStream();
        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();
        SpectrumStream spectrumStream = new SpectrumStream();
        ClassificationStream classificationStream = new ClassificationStream(FileOperator.getDataFromFile("../testData/classification/voyelles.scs"));
        FakeReceiver2D fakeReceiverClassif = new FakeReceiver2D();

        acquiStream.subscribe(spectrumStream);

        spectrumStream.subscribe(mfccFeatureStream);

        mfccFeatureStream.subscribe(classificationStream);

        classificationStream.subscribe(fakeReceiverClassif);

        acquiStream.start();

        double[][] res = fakeReceiverClassif.getData();
        ArrayList<String> classes = classificationStream.getClasses();

        String[] classRec = new String[res.length];
        for (int j = 0; j < res.length; j++) {

            int i_max = -1;
            double max = -Double.MAX_VALUE;
            for (int i = 0; i < res[j].length; i++) {
                if (max < res[j][i]) {
                    max = res[j][i];
                    i_max = i;
                }
            }
            classRec[j] = classes.get(i_max);
        }

        int count = 0;
        for (int i = 0; i < classRec.length; i++) {
            if(classRec[i].equals("a")) count++;
        }

        assertTrue(count>0.5*classRec.length);

    }


    public static class FakeReceiver2D implements DataListenerInterface<DoubleSignal2DInterface>
    {

        private ArrayList<double[]> mfccs = new ArrayList<>();

        public double[][] getData()
        {
            double[][] res = new double[mfccs.size()][];
            for(int i = 0; i < mfccs.size(); i++)
            {
                res[i] = mfccs.get(i);
            }

            return res;
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
                double Tps = 20e-3;
                double TStart = 20e-3;
                int nbPts = (int) (Tps / Ts);
                int nbPtsIgnore = (int) (TStart / Ts);

                //reading ignore part
                double[] buffer1 = new double[nbPtsIgnore];
                readWavFile.readFrames(buffer1, nbPtsIgnore);
                buffer1 = null;

                //reading useful part
                while(readWavFile.getFramesRemaining()>nbPts) {

                    double[][] buffer = new double[1][nbPts];

                    readWavFile.readFrames(buffer, nbPts);

                    DoubleSignal2DInterface outputData = new DoubleSignal2D(
                            buffer,
                            nbPts,
                            Fs
                    );

                    this.push(outputData);
                }

                readWavFile.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (WavFileException e) {
                e.printStackTrace();
            }
        }
    }

}
