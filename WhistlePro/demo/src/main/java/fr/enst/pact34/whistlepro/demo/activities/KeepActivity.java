package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import fr.enst.pact34.whistlepro.demo.R;

public class KeepActivity extends Activity {

    /** /!\ IMPORTANT /!\
     NOTE TO READER :
     Important variables : validSound (type = sound type we use) contains the sound when leaving this screen
     modifiedSound (type = idem) contains the sound after synthesis and all
     originalUserSound (type = idem) contains the sound before treatment

     TO DO : put files in these variables !
            put the reading function
            implement the partition button when the partition is available
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_proc);

        //Le bouton pour accepter les modifications
        Button acceptBtn = (Button)findViewById(R.id.validYesBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO validSound = modifiedSound; //implémenter ces variables
                startActivity(new Intent(KeepActivity.this, NewTrackActivity.class));
            }
        });

        //Le bouton pour garder son enregistrement
        Button noAcceptBtn = (Button)findViewById(R.id.validNoBtn);
        noAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO validSound = originalUserSound ; //implémenter ces variables
                startActivity(new Intent(KeepActivity.this, FirstActivity.class));
            }
        });

        //Le bouton pour écouter
        ImageButton listenBtn = (ImageButton)findViewById(R.id.listenBtn);
        listenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO DO : pointer vers les fonctions de lecture
            }
        });

        //Le bouton pour accéder à la partition
        ImageButton partitionBtn = (ImageButton)findViewById(R.id.partiBtn);
        partitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //implémenter cette fonction (PAN 4)
                Toast.makeText(getApplicationContext(), "A venir ...", Toast.LENGTH_LONG).show();
            }
        });
    }
}
