package fr.enst.pact34.whistlepro.demo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import fr.enst.pact34.whistlepro.demo.R;

public class FirstActivity extends WhistleProActivity {

    /** /!\ IMPORTANT /!\
     NOTE TO READER :
     Important variables : type (int), = 0as long as the user hasn't chosen, = 1 if the user chooses percussions, = 2 if he chooses melodie
                           tempo (int) contains the tempo

     TO DO: implement a charedPreferences object to make the variable type global over the app
     */

    //SharedPreferences prefInstru = getSharedPreferences("preferences_instruments", MODE_PRIVATE);
    int type = 0; //cette variable contient le type d'enregistrement : 0 tant qu'il n'y a pas de choix, 1 pour percussions, et 2 pour melodie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //Le menu déroulant de tempo
        NumberPicker nbpicker = (NumberPicker)findViewById(R.id.np);
        nbpicker.setMaxValue(250);
        nbpicker.setMinValue(40);
        nbpicker.setWrapSelectorWheel(false);

        int tempo = nbpicker.getValue(); //Here you can get the value of the ** T E M P O ** the user sets

        //Le bouton qui donne accès aux pistes déjà enregistrées
        Button pistesBtn = (Button)findViewById(R.id.firstAccesPistes);
        pistesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, AddTrackActivity.class));
            }
        });

        //Le bouton qui oriente vers la voie "Mélodie"
        Button melodieBtn = (Button)findViewById(R.id.firstMel);
        melodieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
            }
        });

        //Le bouton qui oriente vers la voie "Percussions"
        Button percuBtn = (Button)findViewById(R.id.firstPercu);
        percuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                startActivity(new Intent(FirstActivity.this, PercussionTest.class));
            }
        });

        //Le bouton qui mène à l'activité suivante
        Button captBtn = (Button)findViewById(R.id.firstEnregiBtn);
        captBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, EnregistrementActivity.class));
            }
        });

    }
}
