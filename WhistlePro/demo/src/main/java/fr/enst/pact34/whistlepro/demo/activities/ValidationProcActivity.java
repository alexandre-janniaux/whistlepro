package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.enst.pact34.whistlepro.demo.R;

public class ValidationProcActivity extends Activity {

/** /!\ IMPORTANT /!\
 NOTE TO READER :
 Important variables : validSound (type = sound type we use) contains the sound when leaving this screen
                        modifiedSound (type = idem) contains the sound after synthesis and all
                        originalUserSound (type = idem) contains the sound before treatment

 TO DO : put files in these variables !
        fill in the intents once the following activity has been coded
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_proc);

        //Le bouton pour accepter les modifications
        Button acceptBtn = (Button)findViewById(R.id.validYesBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO validSound = modifiedSound ; //implémenter ces variables
                //TODO startActivity(new Intent(ValidationProcActivity.this, TODO.class)); //point to ajouter piste activity
            }
        });

        //Le bouton pour garder son enregistrement
        Button noAcceptBtn = (Button)findViewById(R.id.validNoBtn);
        noAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO validSound = originalUserSound ; //implémenter ces variables
                //TODO startActivity(new Intent(ValidationProcActivity.this, TODO.class)); //idem
            }
        });
    }
}
