package fr.enst.pact34.whistlepro.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class CorrectionActivity extends AppCompatActivity {

/** /!\ IMPORTANT /!\
 NOTE TO READER :
 TO DO : implement the correction (to come)
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);
        ImageButton btnYes = (ImageButton)findViewById(R.id.corrYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "A venir ...", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton btnNo = (ImageButton) findViewById(R.id.corrNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CorrectionActivity.this, ProcessingActivity.class));
            }
        });
    }
}
