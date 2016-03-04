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

        while (mfccFeatureStream.isWorkAvailable()) mfccFeatureStream.doWork();

        while (classifStream.isWorkAvailable()) classifStream.doWork();


        ArrayList<Double> res = fakeReceiverClassif.getVal();
        ArrayList<String> classes = classifStream.getClasses();
        for (int i = 0; i < res.size(); i ++)
        {
            System.out.println(classes.get(i)+ " => "+res.get(i));
        }
    }

    public static class FakeReceiverClassif implements DataListenerInterface<ArrayList<Double>>
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
        public void onPushData(DataSource<ArrayList<Double>> source, ArrayList<ArrayList<Double>> inputData) {
            //System.out.print("pushdata ");

            for(int j = 0; j < inputData.size(); j++) {
                storedData.add(inputData.get(j));
            }
        }

        @Override
        public void onCommit(DataSource<ArrayList<Double>> source) {
            //System.out.println("source");
        }

        @Override
        public void onTransaction(DataSource<ArrayList<Double>> source) {
            //System.out.println("transaction");

        }

    }

    public static  class FakeSpectrumStream extends DataSource<Spectrum>
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


                ArrayList<Spectrum> sps = new ArrayList<>();

                while(readWavFile.readFrames(buffer,nbPts) == nbPts)
                {

                    double[] fft = transformers.fft(buffer);

                    for(i=0; i < fft.length; i++)
                    {
                        fft[i]=2*(fft[i]/fft.length);
                    }

                    sps.add(new Spectrum(fft.length,Fs,fft));

                }

                readWavFile.close();

                this.push(sps);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (WavFileException e) {
                e.printStackTrace();
            }


        }
    }

}
