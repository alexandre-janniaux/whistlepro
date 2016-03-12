package fr.enst.pact34.whistlepro.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstrChoiceActivity extends AppCompatActivity {

/** /!\ IMPORTANT /!\
 NOTE TO READER :
 TO DO : Put the code behind the buttons, to synthesize sounds with the right instrument (4buttons for the moment)
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instr_choice);

        Button guitarBtn = (Button)findViewById(R.id.guitBtn);
        guitarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO : Get the guitar to play the thing !
                startActivity(new Intent(InstrChoiceActivity.this, KeepActivity.class));
            }
        });

        Button clarinetteBtn = (Button)findViewById(R.id.clariBtn);
        clarinetteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO : Get the clarinette to play the thing !
                startActivity(new Intent(InstrChoiceActivity.this, KeepActivity.class));
            }
        });

        Button bassBtn = (Button)findViewById(R.id.baBtn);
        bassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO : Get the bass to play the thing !
                startActivity(new Intent(InstrChoiceActivity.this, KeepActivity.class));
            }
        });

        Button trompetteBtn = (Button)findViewById(R.id.tromBtn);
        trompetteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO : Get the trompette to play the thing !
                startActivity(new Intent(InstrChoiceActivity.this, KeepActivity.class));
            }
        });


    }
}