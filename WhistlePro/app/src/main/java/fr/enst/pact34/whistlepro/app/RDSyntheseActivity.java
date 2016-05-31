package fr.enst.pact34.whistlepro.app;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.InstruData;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;

/**
 * Created by mms on 31/05/16.
 */
public class RDSyntheseActivity extends WhistleProActivity {

    ProcessingMachine processingMachine;
    TextView txt_r,txt_m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rd_activity);

        processingMachine = (ProcessingMachine) getSharedData(SD_PROCESSING_MACINE);

        txt_r = (TextView) findViewById(R.id.txt_r);
        txt_m = (TextView) findViewById(R.id.txt_m);
    }

    public void onInstruChanged(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        InstruData id = null;
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_boise:
                if (checked)
                    id = processingMachine.getInstruData(PisteMelodie.Instrument.Boise);
                    break;
            case R.id.rb_cuivre:
                if (checked)
                    id = processingMachine.getInstruData(PisteMelodie.Instrument.Cuivre);
                    break;
            case R.id.rb_piano:
                if (checked)
                    id = processingMachine.getInstruData(PisteMelodie.Instrument.Piano);
                    break;
        }

        if(id != null)
        {
            txt_r.setText(String.valueOf(id.getR()));
            txt_m.setText(String.valueOf(id.getM()));
        }
    }

    public void saveInstru(View v)
    {
        if(((RadioButton)findViewById(R.id.rb_boise)).isChecked())
        {
            processingMachine.replaceInstruData(PisteMelodie.Instrument.Boise,
                    Double.valueOf(txt_r.getText().toString()),
                    Double.valueOf(txt_m.getText().toString()));
            Toast.makeText(this.getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
        }
        else if(((RadioButton)findViewById(R.id.rb_cuivre)).isChecked())
        {
            processingMachine.replaceInstruData(PisteMelodie.Instrument.Cuivre,
                    Double.valueOf(txt_r.getText().toString()),
                    Double.valueOf(txt_m.getText().toString()));
            Toast.makeText(this.getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
        }
        else if(((RadioButton)findViewById(R.id.rb_piano)).isChecked())
        {
            processingMachine.replaceInstruData(PisteMelodie.Instrument.Piano,
                    Double.valueOf(txt_r.getText().toString()),
                    Double.valueOf(txt_m.getText().toString()));
            Toast.makeText(this.getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
        }
    }

    public void playTest(View v)
    {
        double f = Double.valueOf(((TextView)findViewById(R.id.txt_freq)).getText().toString());
        double t = Double.valueOf(((TextView)findViewById(R.id.txt_time)).getText().toString());

        PisteMelodie pisteTest = new PisteMelodie();
        Instru p = new Instru();
        p.setStartTime(0);
        p.setEndTime(t);
        p.setFreq(f);

        if(((RadioButton)findViewById(R.id.rb_boise)).isChecked())
        {
            pisteTest.setInstrument(PisteMelodie.Instrument.Boise);
        }
        else if(((RadioButton)findViewById(R.id.rb_cuivre)).isChecked())
        {
            pisteTest.setInstrument(PisteMelodie.Instrument.Cuivre);
        }
        else// if(((RadioButton)findViewById(R.id.rb_piano)).isChecked())
        {
            pisteTest.setInstrument(PisteMelodie.Instrument.Piano);
        }

        pisteTest.addInstru(p);
        pisteTest.setTotalTime(t);

        Signal sound = processingMachine.synthetisePiste(pisteTest);

        double[] tmp_dbl = new double[sound.length()];
        sound.fillArray(tmp_dbl);

        AudioPlayer ap = new AudioPlayer();
        ap.start();
        ap.push(tmp_dbl);
        ap.stop();
    }

}
