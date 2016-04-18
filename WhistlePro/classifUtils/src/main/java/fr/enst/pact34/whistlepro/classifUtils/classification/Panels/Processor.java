package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2D;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.common.transformers;
import fr.enst.pact34.whistlepro.api.stream.ClassificationStream;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;

/**
 * Created by mms on 01/03/16.
 */
public class Processor implements AudioDataListener,Runnable {

    private boolean continuee =true;
    LinkedList<double[]> datas = new LinkedList<>();
    double Fs ;
    UserInterface ui = null;
    private String classifierData;

    public Processor(double fs, UserInterface ui,String classifierData)
    {
        this.ui = ui;
        Fs= fs;
        this.classifierData=classifierData;
    }

    @Override
    public void pushData(double[] data) {

        synchronized (datas) {
            datas.add(data);
        }
    }

    public void end()
    {
        continuee = true;
    }

    @Override
    public void run() {

        //Log.e("e","err1");

        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();

        //Log.e("e","err2");
        FakeSpectrumStream spectrumStream = new FakeSpectrumStream();

        //Log.e("e","err3");
        ClassificationStream classifStream = new ClassificationStream(classifierData);

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

        while(continuee) {

            double toProcess[] = null;


                if(datas.size()>0) {
                    synchronized (datas) {
                        toProcess = datas.remove(0);
                    }

                    double sum=0;
                    for(int i = 0; i < toProcess.length; i++)
                    {
                        double tmp = Math.abs(toProcess[i]);
                        if(tmp>max_audio) max_audio = tmp;
                        sum += tmp;
                    }

                    if(sum/toProcess.length<max_audio*0.125) {
                        ui.showText(".");
                        continue;
                    }
                }
                else {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
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
                //System.out.print( res[i]);
            }

            //System.out.println();

            if (max < 0)
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
