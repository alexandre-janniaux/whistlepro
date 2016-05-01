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

import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.api2.main.Piste;

/**
 * Created by mms on 01/05/16.
 */
public class OpenMorceau extends WhistleProActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_morceau);

        morceau = (Morceau) getSharedData(SD_MORCEAU_ACTUEL);
        if(morceau == null)
        {
            throw new RuntimeException("Aucun morceau ouvert !");
        }

        ((TextView) findViewById(R.id.OpenMorceau_nom_morceau)).setText("Titre :" + morceau.getTitle());

        ListView listPisteElements = (ListView) findViewById(R.id.OpenMorceau_listView_piste);

        adapter = new ArrayAdapter<>(this,R.layout.new_piste_rec_done_list_view);

        listPisteElements.setAdapter(adapter);

        listPisteElements.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Piste m = ((Piste) parent.getItemAtPosition(position));
                        replaceSharedData(SD_PISTE_ACTUELLE, m); //
                        //startActivity(new Intent(MainActivity.this, OpenMorceau.class));
                    }
                }
        );

        ((Button) findViewById(R.id.OpenMorceau_btn_new_piste)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(OpenMorceau.this, NewPisteConfig.class));
                    }
                }
        );
    }

    private ArrayAdapter<ListItemPiste> adapter ;
    private Morceau morceau;

    @Override
    protected void onResume() {
        super.onResume();

        List<Piste> listePiste = morceau.getListPiste();
        adapter.clear();
        for (Piste piste: listePiste
                ) {
            adapter.add(new ListItemPiste(piste));
        }
    }



    private class ListItemPiste
    {
        Piste piste;
        public ListItemPiste(Piste m)
        {
            this.piste=m;
        }

        public String toString()
        {
            return "Piste " + piste.getTypePiste().name() + " (id = "+piste.getId()+")";
        }

        public Piste getPiste()
        {
            return piste;
        }
    }

}
