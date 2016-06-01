package fr.enst.pact34.whistlepro.app.activities.v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.app.NewPisteConfig;
import fr.enst.pact34.whistlepro.app.R;
import fr.enst.pact34.whistlepro.app.activities.WhistleProActivity;

/**
 * Created by mms on 29/04/16.
 */
public class NewMorceauActivity extends WhistleProActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Morceau morceau = (Morceau) getSharedData(SD_MORCEAU_ACTUEL);
        if(morceau == null)
        {
            throw new RuntimeException(SD_MORCEAU_ACTUEL + " should exist in shared data.");
        }
        setContentView(R.layout.activity_new_morceau);
        ((EditText)findViewById(R.id.NewMorceau_editText_title)).setText("Morceau n°"+(getNbMorceau()+1));
        ((Button) findViewById(R.id.NewMorceau_button_next)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((EditText)findViewById(R.id.NewMorceau_editText_title)).getText().toString().isEmpty()) {
                            Toast.makeText(getBaseContext(),"Vous devez définir un nom",Toast.LENGTH_LONG).show();
                            return;
                        }
                        morceau.setTitle( ((EditText)findViewById(R.id.NewMorceau_editText_title)).getText().toString());
                        startActivity(new Intent(NewMorceauActivity.this, NewPisteConfig.class));
                        finish();
                    }
                }
        );
    }
}
