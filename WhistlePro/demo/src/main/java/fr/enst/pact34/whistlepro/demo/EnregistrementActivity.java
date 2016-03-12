package fr.enst.pact34.whistlepro.demo;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class EnregistrementActivity extends AppCompatActivity {

/** /!\ IMPORTANT /!\
 NOTE TO READER :
 TO DO : put the record function (module acquisition)
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrement);
        ImageButton okBtn = (ImageButton)findViewById(R.id.enregistrementStop);
        okBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnregistrementActivity.this, NameActivity.class));
            }
        });

        //TO DO : put the record function
    }
}
