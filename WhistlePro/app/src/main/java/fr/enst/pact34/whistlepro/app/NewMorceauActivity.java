package fr.enst.pact34.whistlepro.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.enst.pact34.whistlepro.api2.main.Morceau; 

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
        ((Button) findViewById(R.id.NewMorceau_button_next)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        morceau.setTitle( ((EditText)findViewById(R.id.NewMorceau_editText_title)).getText().toString());
                        startActivity(new Intent(NewMorceauActivity.this, NewPisteConfig.class));
                        finish();
                    }
                }
        );
    }
}
