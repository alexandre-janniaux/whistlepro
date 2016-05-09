package fr.enst.pact34.whistlepro.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.ProcessorInterface;
import fr.enst.pact34.whistlepro.app.models.PianoRollModel;
import fr.enst.pact34.whistlepro.app.views.PianoRollView;

/**
 * Created by mms on 01/05/16.
 */
public class OpenMorceau extends WhistleProActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_morceau);

        model = new PianoRollModel();

        final ProcessorInterface processor = (ProcessorInterface) getSharedData(SD_PROCESSING_MACINE);
        if (processor == null) {
            throw new RuntimeException(SD_PROCESSING_MACINE + " should exist in shared data.");
        }

        morceau = (Morceau) getSharedData(SD_MORCEAU_ACTUEL);
        piste = (Piste) getSharedData(SD_PISTE_ACTUELLE);

        if(morceau == null || piste == null)
        {
            throw new RuntimeException("Aucun morceau ouvert !");
        }

        TextView morceau_name = ((TextView) findViewById(R.id.OpenMorceau_nom_morceau));
        TextView piste_name = ((TextView) findViewById(R.id.OpenMorceau_nom_piste));

        morceau_name.setText("Titre :" + morceau.getTitle());
        piste_name.setText("Piste :" + piste.getName());

        PianoRollView piano = (PianoRollView) findViewById(R.id.pianoroll_piano);
        piano.setModel(model);

        if (piste.getTypePiste() == Piste.TypePiste.Melodie) {
            PisteMelodie melodie = (PisteMelodie) piste;
            for (Instru instru : melodie.getInstruList()) {
                model.addNote(new PianoRollModel.NoteProperty(0, 0, 1., instru.getStartTime(), instru.getEndTime()));
            }
            model.update();
        }
    }

    private Morceau morceau;
    private Piste piste;
    private PianoRollModel model;

    @Override
    protected void onResume() {
        super.onResume();
    }

}
