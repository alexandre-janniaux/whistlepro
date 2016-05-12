package fr.enst.pact34.whistlepro.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
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

    private ArrayAdapter<InstrumentSpinnerEntry> adapter;
    private Thread playThread;

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
        piste_name.setText("Piste :" + piste.getTitle());

        PianoRollView piano = (PianoRollView) findViewById(R.id.pianoroll_piano);
        piano.setModel(model);

        if (piste.getTypePiste() == Piste.TypePiste.Melodie) {
            PisteMelodie melodie = (PisteMelodie) piste;
            for (Instru instru : melodie.getInstruList()) {
                model.addNote(new PianoRollModel.NoteProperty(0, instru.getNote(), 1., instru.getStartTime(), instru.getEndTime()));
            }
            model.update();
        }


        ((ImageButton) findViewById(R.id.OpenMorceau_play)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if (playThread != null) playThread.interrupt();
                        //playThread = new Thread(new Runnable() {
                        //    @Override
                        //    public void run() {
                                Signal sound = processor.synthetisePiste(piste);
                                double[] tmp_dbl = new double[sound.length()];
                                sound.fillArray(tmp_dbl);
                                AudioPlayer ap = new AudioPlayer();
                                ap.start();
                                ap.push(tmp_dbl);
                                ap.stop();
                                playThread = null;
                        //    }
                        //});
                        //playThread.start();
                    }
                }
        );

        Spinner spinner = (Spinner) findViewById(R.id.OpenMorceau_chooseInstrument);

        ArrayList<InstrumentSpinnerEntry> instruments = new ArrayList<>();
        instruments.add(new InstrumentSpinnerEntry(PisteMelodie.Instrument.Piano, "Piano"));
        instruments.add(new InstrumentSpinnerEntry(PisteMelodie.Instrument.Boise, "Boise"));
        instruments.add(new InstrumentSpinnerEntry(PisteMelodie.Instrument.Cuivre, "Cuivre"));
        // instruments.add(new InstrumentSpinnerEntry());

        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, instruments);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InstrumentSpinnerEntry instrument = adapter.getItem(position);
                PisteMelodie piste = (PisteMelodie) getSharedData(SD_PISTE_ACTUELLE);
                piste.setInstrument(instrument.getInstrument());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // SET THE CORRECT INSTRUMENT AT FIRST SCREEN
        for(int i=0; i< adapter.getCount(); ++i) {
            PisteMelodie piste = (PisteMelodie) getSharedData(SD_PISTE_ACTUELLE);
            PisteMelodie.Instrument instrument = piste.getInstrument();
            InstrumentSpinnerEntry entry = adapter.getItem(i);
            if (entry.getInstrument() == instrument) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private Morceau morceau;
    private Piste piste;
    private PianoRollModel model;

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void chooseInstrumentPopup(View v) {

    }

    private class InstrumentSpinnerEntry {
        private PisteMelodie.Instrument instrument;
        private final String name;

        public InstrumentSpinnerEntry(PisteMelodie.Instrument instrument, String name) {

            this.instrument = instrument;
            this.name = name;
        }

        public PisteMelodie.Instrument getInstrument() {
            return instrument;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
