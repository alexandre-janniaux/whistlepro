package features;

import acquisition.WavFile;
import acquisition.WavFileException;
import common.DataListenerInterface;
import common.DataSourceInterface;
import common.Spectrum;
import common.transformers;
import stream.MfccFeatureStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mms on 27/02/16.
 */
public class MfccStreamUsageExample {


    public static void main(String[] args) {

        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();

        FakeSpectrumStream spectrumStream = new FakeSpectrumStream();
        FakeReceiverMFCC fakeReceiverMFCC = new FakeReceiverMFCC();

        spectrumStream.subscribe(mfccFeatureStream);

        mfccFeatureStream.subscribe( fakeReceiverMFCC);

        spectrumStream.calc();
        spectrumStream.calc();

        if(mfccFeatureStream.isWorkAvailable()) mfccFeatureStream.doWork();

        if(mfccFeatureStream.isWorkAvailable()) mfccFeatureStream.doWork();

        if(mfccFeatureStream.isWorkAvailable()) mfccFeatureStream.doWork();


    }

    public static class FakeReceiverMFCC implements DataListenerInterface<ArrayList<Double>>
    {

        @Override
        public void onPushData(DataSourceInterface<ArrayList<Double>> source, ArrayList<ArrayList<Double>> inputData) {
            System.out.println("pushdata");
            for(int j = 0; j < inputData.size(); j++) {
                System.out.println("=>" );
                ArrayList<Double> dd = inputData.get(j);
                for (int i = 0; i < dd.size(); i++) {
                    System.out.print(dd.get(i)+";");

                }
                System.out.println( );
            }
        }

        @Override
        public void onCommit(DataSourceInterface<ArrayList<Double>> source) {
            System.out.println("source");
        }

        @Override
        public void onTransaction(DataSourceInterface<ArrayList<Double>> source) {
            System.out.println("transaction");

        }
    }

    public static  class FakeSpectrumStream extends DataSourceInterface<Spectrum>
    {
        public void calc()
        {

            try {
                WavFile readWavFile = WavFile.openWavFile(new File("data/a.wav"));
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

                ArrayList<Spectrum> sps = new ArrayList<>();
                sps.add(sp);

                this.push(sps);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (WavFileException e) {
                e.printStackTrace();
            }
        }
    }

}