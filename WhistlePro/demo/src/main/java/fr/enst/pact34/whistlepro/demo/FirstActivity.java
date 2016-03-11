package fr.enst.pact34.whistlepro.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class FirstActivity extends AppCompatActivity {

    /** /!\ IMPORTANT /!\
     NOTE TO READER :
     Important variables : type (int), = 0as long as the user hasn't chosen, = 1 if the user chooses percussions, = 2 if he chooses melodie
                           tempo (int) contains the tempo

     TO DO: Point the button "morceaux" (pistesBtn, id firstAccesPistes to the activity giving access to the saved tracks
     */

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

        int tempo = nbpicker.getValue(); //Here you can get the value of the ** T E M P O ** the user set

        //Le bouton qui donne accès aux pistes déjà enregistrées
        Button pistesBtn = (Button)findViewById(R.id.firstAccesPistes);
        pistesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO DO : donner accès à l'activité qui permet d'accéder aux pistes locales
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
        melodieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
            }
        });


    }
}
