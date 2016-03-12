package fr.enst.pact34.whistlepro.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewTrackActivity extends AppCompatActivity {

    public void AppExit()
    {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_track);

        //Le bouton pour ajouter une piste
        Button addTrack = (Button)findViewById(R.id.newTrackBtn);
        addTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewTrackActivity.this, AddTrackActivity.class));
            }
        });

        //Le bouton pour quitter l'application
        Button quit = (Button)findViewById(R.id.quitBtn);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExit();
            }
        });
    }
}
