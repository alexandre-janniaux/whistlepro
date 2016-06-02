package fr.enst.pact34.whistlepro.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;
import fr.enst.pact34.whistlepro.app.activities.WhistleProActivity;

/**
 * Created by mms on 29/04/16.
 */
public class NewPisteRecord extends WhistleProActivity {
    private static final String UPDATE_UI_BROADCAST_ACTION = "fr.enst.pact34.whistlepro.app.NewPistRecord.updateUI";

    final ProcessingMachine processor = (ProcessingMachine) getSharedData(SD_PROCESSING_MACINE);
    final Recorder recorder = (Recorder) getSharedData(SD_RECORDER);
    private TextView noteTextView;

    Handler handlerUpdateUI;
    private Runnable runnableUpdateUI;
    private Intent intent;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(UPDATE_UI_BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_piste_rec);

        noteTextView = (TextView) findViewById(R.id.noteTextView);
        noteTextView.setText("record");

        intent = new Intent(UPDATE_UI_BROADCAST_ACTION);

        runnableUpdateUI = new Runnable() {
            @Override
            public void run() {
                handlerUpdateUI.removeCallbacks(runnableUpdateUI);
                double freq = processor.freq;
                //Intent intent = new Intent("fr.enst.pact34.whistlepro.app.NewPistRecord.updateUI");
                intent.putExtra("frequency", freq);
                sendBroadcast(intent);

                // LOOP UI UPDATE
                if (recorder.isRecording()) handlerUpdateUI.postDelayed(runnableUpdateUI, 100);
            }
        };

        handlerUpdateUI = new Handler();


        if (processor == null) {
            throw new RuntimeException(SD_PROCESSING_MACINE + " should exist in shared data.");
        }
        if (recorder == null) {
            throw new RuntimeException(SD_RECORDER + " should exist in shared data.");
        }

        if(recorder.isRecording()) recorder.stopRec();
        if(processor.isRecProcessing()) processor.stopRecProcessing();

        //processor.setEventLister(this);

        initUI();

    }

    private void updateUI(Intent intent) {
        double freq = intent.getDoubleExtra("frequence", 0.);
        noteTextView.setText("f="+freq);
    }

    private void initUI() {
        final Button play_pause_btn = (Button) findViewById(R.id.NewPisteRecord_button_start);


        // PLAY
        play_pause_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(processor.isRecProcessing()==false) {
                            processor.startRecProcessing();
                            handlerUpdateUI.postDelayed(runnableUpdateUI, 100);
                        }

                        if (recorder.isRecording()) {
                            recorder.stopRec();
                            play_pause_btn.setText("Continue");
                        } else {
                            recorder.startRec();
                            play_pause_btn.setText("Pause");
                        }
                    }
                });

        // STOP
        ((Button) findViewById(R.id.NewPisteRecord_button_stop)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        play_pause_btn.setText("STOPPING PROCESSING");
                        processor.stopRecProcessing();
                        play_pause_btn.setText("STOPPED PROCESSING");
                        recorder.stopRec();
                        play_pause_btn.setText("Restart");
                    }
                });


        ((Button) findViewById(R.id.NewPisteRecord_button_next)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("testPM","ask stop " +System.currentTimeMillis());
                        processor.stopRecProcessing();
                        Log.d("testPM", "stopped " + System.currentTimeMillis());
                        recorder.stopRec();
                        Log.d("testPM", "stopped 2" + System.currentTimeMillis());

                        handlerUpdateUI.removeCallbacks(runnableUpdateUI);

                        if(processor.hasRecordedData())
                        {
                            processor.waitEnd();
                            startActivity(new Intent(NewPisteRecord.this, NewPisteRecordDone.class));
                            finish();
                        }
                    }
                });
    }
/*
    @Override
    public void newWorkEvent(WorkEvent e) {

        Log.d("testPM","event :" +e.name() +" "+System.currentTimeMillis()+ " " +processor.isRecProcessing()+ " " + processor.transcriptionEnded()
        +" dr = " + processor.getDataRecevied()+ "; nb last="+processor.getLast_nb_done()+"; nb tr ="+processor.getTransciptionNB());

    }*/
}
