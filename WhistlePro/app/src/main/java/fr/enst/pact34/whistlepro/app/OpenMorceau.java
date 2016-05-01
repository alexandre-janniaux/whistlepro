package fr.enst.pact34.whistlepro.app;

import android.os.Bundle;
import android.widget.TextView;

import fr.enst.pact34.whistlepro.api2.main.Morceau;

/**
 * Created by mms on 01/05/16.
 */
public class OpenMorceau extends WhistleProActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_morceau);

        Morceau m = (Morceau) getSharedData(SD_MORCEAU_ACTUEL);
        if(m == null)
        {
            throw new RuntimeException("Aucun morceau ouvert !");
        }

        ((TextView) findViewById(R.id.OpenMorceau_nom_morceau)).setText(m.getTitle());
    }

}
