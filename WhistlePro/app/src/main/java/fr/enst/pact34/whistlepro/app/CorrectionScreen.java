package fr.enst.pact34.whistlepro.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CorrectionScreen extends AppCompatActivity {

    TextView correctionTxt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction_screen);
        ImageButton BtnYes = (ImageButton)findViewById(R.id.correctionBtnYesDemo);
        BtnYes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "A venir ...", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton BtnNo = (ImageButton)findViewById(R.id.correctionBtnNo);
        BtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //TO DO : put function to go to next screen
            }
        });

    }
}
