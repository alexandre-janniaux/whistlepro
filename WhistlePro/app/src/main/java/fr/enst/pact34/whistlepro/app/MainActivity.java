package fr.enst.pact34.whistlepro.app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.enst.pact34.whistlepro.api2.main.Morceau;


public class MainActivity extends WhistleProActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.main_newMorceau)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceSharedData(SD_MORCEAU_ACTUEL,new Morceau());
                        startActivity(new Intent(MainActivity.this, NewMorceauActivity.class));
                    }
                }
        );
    }
}


