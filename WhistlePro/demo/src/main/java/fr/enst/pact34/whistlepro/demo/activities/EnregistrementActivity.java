package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import fr.enst.pact34.whistlepro.demo.R;

public class EnregistrementActivity extends Activity {

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
