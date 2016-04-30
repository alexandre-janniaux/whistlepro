package fr.enst.pact34.whistlepro.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.enst.pact34.whistlepro.api2.main.ProcessorInterface;

/**
 * Created by mms on 29/04/16.
 */
public class NewPisteRecord extends WhistleProActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_piste_rec);

        final ProcessorInterface processor = (ProcessorInterface) getSharedData(SD_PROCESSING_MACINE);
        final Recorder recorder = (Recorder) getSharedData(SD_RECORDER);

        if (processor == null) {
            throw new RuntimeException(SD_PROCESSING_MACINE + " should exist in shared data.");
        }
        if (recorder == null) {
            throw new RuntimeException(SD_RECORDER + " should exist in shared data.");
        }

        if(recorder.isRecording()) recorder.stopRec();
        if(processor.isRecProcessing()) processor.stopRecProcessing();


        final Button play_pause_btn = (Button) findViewById(R.id.NewPisteRecord_button_start);
        play_pause_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (processor.isRecProcessing() == false) {
                            processor.startRecProcessing();
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

        ((Button) findViewById(R.id.NewPisteRecord_button_stop)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (processor.isRecProcessing()) {
                            processor.stopRecProcessing();
                        }
                        if (recorder.isRecording()) {
                            recorder.stopRec();
                        }
                        play_pause_btn.setText("Restart");
                    }
                });


        ((Button) findViewById(R.id.NewPisteRecord_button_next)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (processor.isRecProcessing()) {
                            processor.stopRecProcessing();
                        }
                        if (recorder.isRecording()) {
                            recorder.stopRec();
                        }
                        if(processor.hasRecordedData())
                        {
                            processor.waitEnd();
                            startActivity(new Intent(NewPisteRecord.this, NewPisteRecordDone.class));
                            finish();
                        }
                    }
                });

    }
}
