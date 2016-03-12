package fr.enst.pact34.whistlepro.demo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProcessingActiviy extends AppCompatActivity {

    /** /!\ IMPORTANT /!\
     NOTE TO READER :
     TO DO : implement the intent to switch to the right activity. The SharedPreference object has to be defined in FirstActivity.java first. 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_activiy);

        //Le bouton qui permet de ne pas passer par le traitement
        ImageButton youBtn = (ImageButton)findViewById(R.id.youBtn);
        youBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO : basculer vers l'activité qui permet de réécouter, voir la partition, etc
            }
        });

        //Le bouton qui permet de passer par le traitement
        ImageButton instrBtn = (ImageButton)findViewById(R.id.instrBtn);
        instrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO : basculer vers l'activité du choix de l'instrument si type = 2, ou vers l'activité qui permet de réécouter
                /*use SharedPreferences prefInstru = getSharedPreferences("preferences_instruments", MODE_PRIVATE);
                prefInstru.getString("cle", "valeur par defaut");
                prefInstru.edit().putString()*/
            }
        });
    }
}
