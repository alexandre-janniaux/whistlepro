package fr.enst.pact34.whistlepro.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.ProcessorInterface;

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

        Morceau morceau = (Morceau) getSharedData(SD_MORCEAU_ACTUEL);
        if(morceau == null)
        {
            throw new RuntimeException("Aucun morceau ouvert !");
        }
        final EditText titre = (EditText) findViewById(R.id.NewPisteConfig_Title);

        titre.setText("Piste n°"+(morceau.nbPiste()+1));

        ((Button) findViewById(R.id.NewPisteConfig_button_next)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) findViewById(R.id.NewPisteConfig_Percussion)).isChecked()) {
                            processor.init(Piste.TypePiste.Percussions);
                        } else if (((RadioButton) findViewById(R.id.NewPisteConfig_Melodie)).isChecked()) {
                            processor.init(Piste.TypePiste.Melodie);
                        } else {
                            return;
                        }
                        processor.setTitle(titre.getText().toString());
                        if(titre.getText().toString().isEmpty())
                        {
                            Toast.makeText(getBaseContext(), "Vous devez définir un nom", Toast.LENGTH_LONG).show();
                            return;
                        }
                        startActivity(new Intent(NewPisteConfig.this, NewPisteRecord.class));
                        finish();
                    }
                }
        );
    }
}
