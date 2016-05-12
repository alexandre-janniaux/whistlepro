package fr.enst.pact34.whistlepro.toolsapp;

import android.util.Log;
import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2D;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.common.transformers;
import fr.enst.pact34.whistlepro.api.stream.ClassificationStream;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;

import java.util.ArrayList;
import java.util.LinkedList;


public class ProcessingMachine implements AudioDataListener,Runnable {

    LinkedList<Double> datas = new LinkedList<Double>();
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

        Log.e("e","err1");

        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();

        Log.e("e","err2");
        FakeSpectrumStream spectrumStream = new FakeSpectrumStream();

        Log.e("e","err3");
        ClassificationStream classifStream = new ClassificationStream(Main.CLASSIFIER_DATA); //TODO: add a way to inject Main.CLASSIFIER_DATA;

        Log.e("e","err4");
        FakeReceiverClassif fakeReceiverClassif = new FakeReceiverClassif();

        Log.e("e","err5");
        spectrumStream.subscribe(mfccFeatureStream);

        Log.e("e","err6");
        mfccFeatureStream.subscribe(classifStream);

        Log.e("e","err7");
        classifStream.subscribe(fakeReceiverClassif);

        Log.e("e","err8");

        double max_audio=0;

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

            ArrayList<Double> res = fakeReceiverClassif.getVal();
            ArrayList<String> classes = classifStream.getClasses();
            String max_c = "-";
            double max = -Double.MAX_VALUE;
            for (int i = 0; i < res.size(); i++) {
                if (res.get(i) > max) {
                    max = res.get(i);
                    max_c = classes.get(i);
                }
            }

            if (max < 3)
                max_c = ".";

            ui.showText(max_c);
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

            for(int j = 0; j < signal.length; j++) {
                storedData.add(transformers.doubleToArray(signal[j]));
            }
        }

    }

    public static  class FakeSpectrumStream extends DataSource<DoubleSignal2DInterface>
    {

        public void calcOnWav(double[] buffer,double Fs)
        {

            double[][] output = new double[1][];

            double[] fft = transformers.fft(buffer);

            output[0] = fft;

            DoubleSignal2DInterface outputData = new DoubleSignal2D(output, buffer.length, Fs);
            this.fillOut(outputData);
        }
    }
}
