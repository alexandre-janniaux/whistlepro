package fr.enst.pact34.whistlepro.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ProcessingActivity extends AppCompatActivity {

    /** /!\ IMPORTANT /!\
     NOTE TO READER :
     TO DO : The SharedPreference object has to be defined in FirstActivity.java first.
            the synthesis functions have to be implemented
     */
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_activiy);

        //Le bouton qui permet de ne pas passer par le traitement
        ImageButton youBtn = (ImageButton)findViewById(R.id.youBtn);
        youBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProcessingActivity.this, KeepActivity.class));
            }
        });

        //Le bouton qui permet de passer par le traitement
        ImageButton instrBtn = (ImageButton)findViewById(R.id.instrBtn);
        instrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 2){
                    startActivity(new Intent(ProcessingActivity.this, InstrChoiceActivity.class));
                }
                else{
                    //faire la synthèse rythmique
                    startActivity(new Intent(ProcessingActivity.this, KeepActivity.class));
                }

                // TO DO : basculer vers l'activité du choix de l'instrument si type = 2, ou vers l'activité qui permet de réécouter
                /*use SharedPreferences prefInstru = getSharedPreferences("preferences_instruments", MODE_PRIVATE);
                prefInstru.getString("cle", "valeur par defaut");
                prefInstru.edit().putString()*/
            }
        });
    }
}
