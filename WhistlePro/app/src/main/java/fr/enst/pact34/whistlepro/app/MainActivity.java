package fr.enst.pact34.whistlepro.app;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.app.activities.v1.NewMorceauActivity;
import fr.enst.pact34.whistlepro.app.activities.v1.TrackListActivity;
import fr.enst.pact34.whistlepro.app.activities.WhistleProActivity;


public class MainActivity extends WhistleProActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton addButton = ((FloatingActionButton) findViewById(R.id.add_music_button));
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceSharedData(SD_MORCEAU_ACTUEL, new Morceau());
                        startActivity(new Intent(MainActivity.this, NewMorceauActivity.class));
                    }
                }
        );

        List<Morceau> listeMorceau = (List<Morceau>) getSharedData(SD_LISTE_MORCEAU);
        ListView listPisteElements = (ListView) findViewById(R.id.main_listView_morceau);
        adapter = new MusicListAdapter(this, 0, listeMorceau);
        listPisteElements.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        toolbar.setVisibility(View.INVISIBLE);

    }

    public void startRD_Synthese(View v)
    {
        startActivity(new Intent(MainActivity.this, RDSyntheseActivity.class));
    }

    private MusicListAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();

        List<Morceau> listeMorceau = (List<Morceau>) getSharedData(SD_LISTE_MORCEAU);
        ListView listPisteElements = (ListView) findViewById(R.id.main_listView_morceau);
        adapter = new MusicListAdapter(this, 0, listeMorceau);
        listPisteElements.setAdapter(adapter);

    }

    class MusicListAdapter extends ArrayAdapter<Morceau>{
        private final List<Morceau> musics;

        public MusicListAdapter(Context context, int resource, List<Morceau> musics) {
            super(context, resource, musics);

            this.musics = musics;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int index = position; // Stupid java

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.musiclist_musicitem, parent, false);
            }

            Morceau morceau = this.musics.get(position);

            View item = (View) convertView.findViewById(R.id.musiclist_musicitem);
            //ImageView picture = (ImageView) convertView.findViewById(R.id.trackitem_picture);
            TextView name = (TextView) convertView.findViewById(R.id.musiclist_musicitem_name);
            //ToggleButton mute = (ToggleButton) convertView.findViewById(R.id.trackitem_mute_button);
            //ToggleButton solo = (ToggleButton) convertView.findViewById(R.id.trackitem_solo_button);

            item.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Morceau m = musics.get(index);
                    replaceSharedData(SD_MORCEAU_ACTUEL, m);
                    startActivity(new Intent(MainActivity.this, TrackListActivity.class));
                }
            });

            if (morceau.getTitle() != null && morceau.getTitle().isEmpty() == false) name.setText(morceau.getTitle());
            else name.setText("Composition sans nom");

            //TODO: set picture


            return convertView;
        }
    }
}


