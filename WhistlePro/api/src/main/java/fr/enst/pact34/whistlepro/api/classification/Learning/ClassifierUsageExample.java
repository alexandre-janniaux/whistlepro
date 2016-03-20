package fr.enst.pact34.whistlepro.api.classification.Learning;

import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.common.*;
import fr.enst.pact34.whistlepro.api.stream.ClassificationStream;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mms on 27/02/16.
 */
public class ClassifierUsageExample {


    public static void main(String[] args) {

        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();
        FakeSpectrumStream spectrumStream = new FakeSpectrumStream();
        ClassificationStream classifStream = new ClassificationStream(FileOperator.getDataFromFile("data/voyelles.scs"));
        FakeReceiverClassif fakeReceiverClassif = new FakeReceiverClassif();

        spectrumStream.subscribe(mfccFeatureStream);

        mfccFeatureStream.subscribe(classifStream);

        classifStream.subscribe(fakeReceiverClassif);

        String fileToClassify = "data/a.wav";

        spectrumStream.calcOnWav(fileToClassify);

        ArrayList<Double> res = fakeReceiverClassif.getVal();
        ArrayList<String> classes = classifStream.getClasses();
        for (int i = 0; i < res.size(); i ++)
        {
            System.out.println(classes.get(i)+ " => "+res.get(i));
        }
    }

    public static class FakeReceiverClassif implements DataListenerInterface<DoubleSignal2DInterface>
    {

        private ArrayList<ArrayList<Double>> storedData = new ArrayList<>();

        public ArrayList<Double> getVal()
        {
            if(storedData.size()>0)
                return storedData.remove(0);
            else
                return null;
        }

        @Override
        public void fillIn(DataSource<DoubleSignal2DInterface> source, DoubleSignal2DInterface inputData) {
            //System.out.print("pushdata ");
            double[][] signal = inputData.getSignal();

            for(int i = 0; i < signal.length; i++) {
                storedData.add(transformers.doubleToArray(signal[i]));
            }
        }

    }

    public static  class FakeSpectrumStream extends DataSource<DoubleSignal2DInterface>
    {

        public void calcOnWav(String fileName)
        {

            try {
                WavFile readWavFile = WavFile.openWavFile(new File(fileName));
                double Fs = readWavFile.getSampleRate();
                double Ts = 1.0/readWavFile.getSampleRate();
                double Tps = 20e-3;
                double Tstart =20e-3;
                int nbPts = (int)(Tps/Ts);
                int nbPtsIgnore =(int)(Tstart/Ts);

                //reading ignore part
                double[] buffer = new double[nbPtsIgnore];
                int i;
                readWavFile.readFrames(buffer,nbPtsIgnore);

                //reading useful part
                buffer = new double[nbPts];


                double[][] output = new double[1][]; //FIXME: bigger buffer

                while(readWavFile.readFrames(buffer,nbPts) == nbPts)
                {

                    double[] fft = transformers.fft(buffer);

                    output[1] = fft;
                    DoubleSignal2DInterface outputData = new DoubleSignal2D(
                            output,
                            nbPts,
                            Fs
                    );

                    fillOut(outputData);

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
