package fr.enst.pact34.whistlepro.demo.activities;

import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachineEventListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import fr.enst.pact34.whistlepro.demo.R;

public class PercussionTest extends Activity implements UserInterface, AudioDataListener, ProcessingMachineEventListener {

    AudioIn recorder ;
    ProcessingMachine machine ;
    public static String CLASSIFIER_DATA;
    public static int Fs = 16000;
    //Thread processor ;
    ArrayList<String> strs = new ArrayList<>();
    TextView affichage ;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        CLASSIFIER_DATA = readRawTextFile(getApplicationContext(),R.raw.voyelles_k20);

        recorder = new AudioIn();

        dataTmp = new double[recorder.getSampleSize()];

        machine = new ProcessingMachine(Fs,CLASSIFIER_DATA,2);

        machine.setEventLister(this);

        recorder.setListener(this);

        //processor = new Thread(machine);

        //processor.start();

        affichage = (TextView)findViewById(R.id.view);


    }


    public static String readRawTextFile(Context ctx, int resId)
    {

        InputStream inputStream =  ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }

    @Override
    public void showText(String text) {
        //Log.e("AUDIO","reco => "+text);
            strs.add(text);
        //((TextView)findViewById(R.id.view)).append(text);
    }



    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job

        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms

        timer.schedule(timerTask, 0, 99); //

    }

    public void stoptimertask(View v) {

        //stop the timer, if it's not already null

        if (timer != null) {

            timer.cancel();

            timer = null;

        }

    }

    final int len = 25;
    String max = "";
    HashSet<String> classes = new HashSet<>();
    public void initializeTimerTask() {



        timerTask = new TimerTask() {

            public void run() {


                //use a handler to run a toast that shows the current timestamp

                handler.post(new Runnable() {

                    public void run() {



                            while(strs.size()>0)
                            {
                                String str;
                                synchronized (strs)
                                {
                                str = strs.remove(0);
                                }
                                if(classes.contains(str)==false) classes.add(str);
                                max=max.concat(str);
                            }

                            if(max.length()> len)
                            {
                                max=max.substring(max.length()-len);
                            }



                        affichage.setText(max);
                    }

                });

            }

        };

    }

    double[] dataTmp ;
    //Semaphore s = new Semaphore(1);
    @Override
    public void dataReceiver(short[] data) {
        if(data.length == dataTmp.length) {
            for (int i = 0; i < data.length; i++) {
                dataTmp[i] = ((double)data[i]) / Short.MAX_VALUE;
            }
        }
        else {
            for (int i = 0; i < data.length; i++) {
                dataTmp[i] = 0;
            }
        }

        //try {
         //   s.acquire();
        //} catch (InterruptedException e) {
          //  e.printStackTrace();
        //}
        machine.pushData(dataTmp);

        Log.d("appli pact34", "pushh");
    }

    @Override
    public void newWorkEvent(WorkEvent e) {
        Log.d("appli pact34", "rrrr");
        String tmp = machine.getLastReco();
        showText(tmp);
        Log.d("appli pact34", "reco : "+tmp);
        Log.d("appli pact34", "rrrr2");
        //s.release();
    }
}
