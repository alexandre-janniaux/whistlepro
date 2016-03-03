package fr.enst.pact34.WhistleProDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Activity implements UserInterface {
    AudioIn recorder ;
    ProcessingMachine machine ;
    public static String CLASSIFIER_DATA;
    public static int Fs = 16000;
    Thread processor ;
    ArrayList<String> strs = new ArrayList<>();
    TextView affichage ;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        CLASSIFIER_DATA = readRawTextFile(getApplicationContext(),R.raw.voyelles);

        recorder = new AudioIn();

        machine = new ProcessingMachine(Fs,this);

        recorder.setListener(machine);

        processor = new Thread(machine);

        processor.start();

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
        synchronized (strs) {
            strs.add(text);
        }
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

        timer.schedule(timerTask, 0, 20); //

    }

    public void stoptimertask(View v) {

        //stop the timer, if it's not already null

        if (timer != null) {

            timer.cancel();

            timer = null;

        }

    }


    public void initializeTimerTask() {



        timerTask = new TimerTask() {

            public void run() {


                //use a handler to run a toast that shows the current timestamp

                handler.post(new Runnable() {

                    public void run() {

                        synchronized (strs)
                        {
                            while(strs.size()>0)
                            {
                                affichage.append(strs.remove(0));
                            }
                        }

                        if(affichage.getText().length()> 2000)
                        {
                            affichage.setText(affichage.getText().subSequence(500,1500));
                        }


                    }

                });

            }

        };

    }
}
