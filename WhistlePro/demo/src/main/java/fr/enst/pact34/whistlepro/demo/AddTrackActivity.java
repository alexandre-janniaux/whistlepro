package fr.enst.pact34.whistlepro.demo;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddTrackActivity extends AppCompatActivity {

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
                startActivity(new Intent(AddTrackActivity.this, AddTrackActivity.class));
            }
        });

        ImageButton futureBtn = (ImageButton)findViewById(R.id.addTrackFutureBtn);
       futureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "A venir ...", Toast.LENGTH_LONG).show();
            }
        });

    }
}
