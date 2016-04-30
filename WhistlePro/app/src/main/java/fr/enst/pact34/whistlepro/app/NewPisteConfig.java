package fr.enst.pact34.whistlepro.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import fr.enst.pact34.whistlepro.api2.main.ProcessorInterface;
import fr.enst.pact34.whistlepro.api2.main.TypePiste;

/**
 * Created by mms on 29/04/16.
 */
public class NewPisteConfig extends WhistleProActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_piste_config);

        final ProcessorInterface processor = (ProcessorInterface) getSharedData(SD_PROCESSING_MACINE);
        if(processor == null)
        {
            throw new RuntimeException(SD_PROCESSING_MACINE + " should exist in shared data.");
        }

        ((Button) findViewById(R.id.NewPisteConfig_button_next)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if( ((RadioButton)findViewById(R.id.NewPisteConfig_Percussion)).isChecked() ) {
                            processor.init(TypePiste.Percussions);
                        }
                        else if( ((RadioButton)findViewById(R.id.NewPisteConfig_Melodie)).isChecked() )
                        {
                            processor.init(TypePiste.Melodie);
                        }
                        else
                        {
                            return;
                        }

                        startActivity(new Intent(NewPisteConfig.this, NewPisteRecord.class));
                        finish();
                    }
                }
        );
    }
}
