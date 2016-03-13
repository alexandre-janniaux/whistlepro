package fr.enst.pact34.whistlepro.demo;

import fr.enst.pact34.whistlepro.api.common.*;
import fr.enst.pact34.whistlepro.api.stream.ClassificationStream;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by mms on 01/03/16.
 */
public class ProcessingMachine implements AudioDataListener,Runnable {

    LinkedList<Double> datas = new LinkedList<>();
    double Fs ;
    int nbSamples ;
    UserInterface ui = null;

    public ProcessingMachine(double fs,UserInterface ui)
    {
        this.ui = ui;
        Fs= fs;
        nbSamples = (int) (0.020*Fs);
    }

    @Override
    public void dataReceiver(short[] data) {

        synchronized (datas) {
            for (short d : data) {
                datas.add(((double) d) / Short.MAX_VALUE);
            }
        }
    }

    @Override
    public void run() {

        //Log.e("e","err1");

        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();

        //Log.e("e","err2");
        FakeSpectrumStream spectrumStream = new FakeSpectrumStream();

        //Log.e("e","err3");
        ClassificationStream classifStream = new ClassificationStream(PercussionTest.CLASSIFIER_DATA);

        //Log.e("e","err4");
        FakeReceiverClassif fakeReceiverClassif = new FakeReceiverClassif();

        //Log.e("e","err5");
        spectrumStream.subscribe(mfccFeatureStream);

        //Log.e("e","err6");
        mfccFeatureStream.subscribe(classifStream);

        //Log.e("e","err7");
        classifStream.subscribe(fakeReceiverClassif);

        //Log.e("e","err8");

        double max_audio=0;

        ArrayList<String> classes = classifStream.getClasses();

        while(true) {

            double toProcess[] = new double[nbSamples];

            synchronized (datas) {

                if(datas.size()<nbSamples)
                {
                    continue;
                }
                else
                {
                    double sum=0;
                    for(int i = 0; i < nbSamples; i++)
                    {
                        double tmp = Math.abs(datas.get(0));
                        if(tmp>max_audio) max_audio = tmp;
                        sum += tmp;
                        toProcess[i] = datas.remove(0);
                    }
                    if(sum/nbSamples<max_audio*0.125) {
                        ui.showText(".");
                        continue;
                    }
                }

            }

            spectrumStream.calcOnWav(toProcess, Fs);

            //while (mfccFeatureStream.isWorkAvailable()) mfccFeatureStream.doWork();

            //while (classifStream.isWorkAvailable()) classifStream.doWork();


            double[] res = fakeReceiverClassif.getVal();

            if(res == null) continue;

            String max_c = "-";
            double max = -Double.MAX_VALUE;
            for (int i = 0; i < res.length; i++) {
                if (res[i] > max) {
                    max = res[i];
                    max_c = classes.get(i);
                }
            }

            if (max < 0.1)
                max_c = "-";

            ui.showText(max_c);
        }

    }


    public static class FakeReceiverClassif implements DataListenerInterface<DoubleSignal2DInterface>
    {

        private ArrayList<double[]> storedData = new ArrayList<>();

        public double[] getVal()
        {
            if(storedData.size()>0)
                return storedData.remove(0);
            else
                return null;
        }

        @Override
        public void onPushData(DataSource<DoubleSignal2DInterface> source, DoubleSignal2DInterface inputData) {

            for(int index=0; index < inputData.getSignal().length; ++index)
            {
                storedData.add(inputData.getSignal()[index]);

            }
        }
    }

    public static  class FakeSpectrumStream implements DataSourceInterface<DoubleSignal2DInterface> {

        public void calcOnWav(double[] buffer,double Fs)
        {

            double[][] fft = new double[1][];
            fft[0] = transformers.fft(buffer);

            this.datasource.push(new DoubleSignal2D(fft, buffer.length, Fs));

        }

        private DataSource<DoubleSignal2DInterface> datasource = new DataSource<>();

        @Override
        public void subscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
            this.datasource.subscribe(listener);
        }

        @Override
        public void unsubscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
            datasource.unsubscribe(listener);
        }
    }
}
