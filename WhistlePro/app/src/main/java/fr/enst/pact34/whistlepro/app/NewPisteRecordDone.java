package fr.enst.pact34.whistlepro.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.PistePercu;
import fr.enst.pact34.whistlepro.api2.main.ProcessorInterface;

/**
 * Created by mms on 29/04/16.
 */
public class NewPisteRecordDone extends WhistleProActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_piste_rec_done);

        final ProcessorInterface processor = (ProcessorInterface) getSharedData(SD_PROCESSING_MACINE);
        if (processor == null) {
            throw new RuntimeException(SD_PROCESSING_MACINE + " should exist in shared data.");
        }

        ListView listPisteElements = (ListView) findViewById(R.id.NewPisteRecordDone_listView_piste_elements);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.new_piste_rec_done_list_view);
        listPisteElements.setAdapter(adapter);
        //adapter.add("Test");

        final Piste piste = processor.getPiste();
        if(piste == null)
        {
            adapter.add("Error");
        }
        else if(piste.getTypePiste()== Piste.TypePiste.Percussions)
        {
            adapter.add("Piste percussions");
            adapter.add("Temps "+piste.getTotalTime());
            PistePercu pistePercu = (PistePercu) piste;
            for (Percu percu:
                 pistePercu.getPercuList()) {

                Percu.Type  type = percu.getType();
                String typeStr = "X";
                if(type!=null) typeStr = type.name();
                adapter.add(" => " + percu.getStartTime() + ":" + percu.getEndTime()+ " => "+typeStr);

            }
        }
        else if(piste.getTypePiste()== Piste.TypePiste.Melodie)
        {
            adapter.add("Piste melodie");
            adapter.add("Temps "+piste.getTotalTime());
            PisteMelodie pisteMelodie = (PisteMelodie) piste;
            for (Instru instru:
                    pisteMelodie.getInstruList()) {

                String typeStr = "X"; 
                adapter.add(" => " + instru.getStartTime() + ":" + instru.getEndTime()+ " => "+instru.getFreq());

            }
        }

        ((Button) findViewById(R.id.NewPisteRecordDone_button_play)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Signal sound = processor.synthetisePiste(piste); 

                        double[] tmp_dbl = new double[sound.length()];
                        sound.fillArray(tmp_dbl);

                        AudioPlayer ap = new AudioPlayer();
                        ap.start();
                        ap.push(tmp_dbl);
                        ap.stop();

                    }
                }
        );


        ((Button) findViewById(R.id.NewPisteRecordDone_save)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(piste!=null)
                        {
                            Morceau morceau = (Morceau) getSharedData(SD_MORCEAU_ACTUEL);
                            morceau.addPiste(piste);
                            saveMorceau(morceau);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),
                                    "La piste n'a pas pu être enregistrée. Veuillez annulez l'enregistrement.",
                                    Toast.LENGTH_LONG);
                        }

                    }
                }
        );

        ((Button) findViewById(R.id.NewPisteRecordDone_delete)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            finish();
                    }
                }
        );
    }

}
