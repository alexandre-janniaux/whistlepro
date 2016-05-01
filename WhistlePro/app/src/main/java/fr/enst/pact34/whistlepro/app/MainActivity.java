package fr.enst.pact34.whistlepro.app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.main.Morceau;


public class MainActivity extends WhistleProActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.main_newMorceau)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceSharedData(SD_MORCEAU_ACTUEL,new Morceau());
                        startActivity(new Intent(MainActivity.this, NewMorceauActivity.class));
                    }
                }
        );

        ListView listPisteElements = (ListView) findViewById(R.id.main_listView_morceau);

        adapter = new ArrayAdapter<>(this,R.layout.new_piste_rec_done_list_view);

        listPisteElements.setAdapter(adapter);

        listPisteElements.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Morceau m = ((ListItemMorceau) parent.getItemAtPosition(position)).getMorceau();
                        replaceSharedData(SD_MORCEAU_ACTUEL, m);
                        startActivity(new Intent(MainActivity.this, OpenMorceau.class));
                    }
                }
        );



    }

    private ArrayAdapter<ListItemMorceau> adapter ;

    @Override
    protected void onResume() {
        super.onResume();

        List<Morceau> listeMorceau = (List<Morceau>) getSharedData(SD_LISTE_MORCEAU);
        adapter.clear();
        for (Morceau morceau: listeMorceau
                ) {
            adapter.add(new ListItemMorceau(morceau));
        }
    }

    private class ListItemMorceau
    {
        Morceau m;
        public ListItemMorceau(Morceau m )
        {
            this.m=m;
        }

        public String toString()
        {
            return m.getTitle();
        }

        public Morceau getMorceau()
        {
            return m;
        }
    }
}


