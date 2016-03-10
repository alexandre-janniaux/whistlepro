package fr.enst.pact34.whistlepro.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Correction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);
        ImageButton BtnYes = (ImageButton)findViewById(R.id.corrYes);
        BtnYes.OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "A venir ...", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton BtnNo = (ImageButton) findViewById(R.id.corrNo);
        BtnNo.OnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TO DO : point to next activity
            }
        });
    }
}
