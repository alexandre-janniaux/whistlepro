package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import fr.enst.pact34.whistlepro.demo.R;

public class AddTrackActivity extends Activity {

    //Ne sera pas utilisé pour la démo, a priori

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        //Le bouton Home
        ImageButton homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTrackActivity.this, FirstActivity.class));
            }
        });

        ImageButton futureBtn = (ImageButton)findViewById(R.id.addTrackFutureBtn);
       futureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "A venir ...", Toast.LENGTH_LONG).show();
            }
        });

        Button nvelEnregistr = (Button)findViewById(R.id.newCaptureBtn);
        nvelEnregistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTrackActivity.this, FirstActivity.class));
            }
        });
    }
}
