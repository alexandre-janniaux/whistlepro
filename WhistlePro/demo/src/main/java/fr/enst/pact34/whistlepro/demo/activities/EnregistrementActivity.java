package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import fr.enst.pact34.whistlepro.api2.main.ProcessorInterface;
import fr.enst.pact34.whistlepro.demo.R;

public class EnregistrementActivity extends WhistleProActivity {


    final ProcessorInterface processor  = (ProcessorInterface) getSharedData(SD_PROCESSING_MACINE);
    final Recorder recorder = (Recorder) getSharedData(SD_RECORDER);

    /**
     * /!\ IMPORTANT /!\
     * NOTE TO READER :
     * TO DO : put the record function (module acquisition)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrement);


        ImageButton okBtn = (ImageButton) findViewById(R.id.enregistrementStop);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recorder.stopRec();
                processor.waitEnd();// attend la fin des traitements
                startActivity(new Intent(EnregistrementActivity.this, NameActivity.class));
            }
        });

        //Code for the metronome switching
        final int[] imageArray = {R.drawable.metronome, R.drawable.metronome};// TODO metronome_flip};


        final Handler handler = new Handler();

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                imageView.setImageResource(imageArray[i]);
                /*
                i++;
                if(i>imageArray.length-1)
                {
                    i=0;
                }
                */
                i = (i + 1) % imageArray.length;

                handler.postDelayed(this, 500);  //for interval...
            }
        };
        handler.postDelayed(runnable, 20); //for initial delay..


        //TO DO : put the record function

        processor.startRecProcessing();

    }

    @Override
    protected void onResume() {
        super.onResume();
        recorder.startRec();
    }

    @Override
    protected void onPause() {
        super.onPause();
        recorder.stopRec();
    }
}



