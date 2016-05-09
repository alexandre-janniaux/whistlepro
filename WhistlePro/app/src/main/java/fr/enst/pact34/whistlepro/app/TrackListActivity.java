package fr.enst.pact34.whistlepro.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.api2.main.Piste;


public class TrackListActivity extends WhistleProActivity {

    private TrackListAdapter trackListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.track_list);

        Morceau morceau = (Morceau) getSharedData(SD_MORCEAU_ACTUEL);
        if (morceau == null) {

        }

        TextView musicName = (TextView) findViewById(R.id.tracklist_music_name);
        musicName.setText(morceau.getTitle());

        Log.d("whistlepro", "whistlepro dit pistes " + morceau.getListPiste().size());

        ListView trackList = (ListView) findViewById(R.id.track_list_view);
        trackListAdapter = new TrackListAdapter(this, 0, morceau.getListPiste());
        trackList.setAdapter(trackListAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_track_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NewPisteConfig.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Morceau morceau = (Morceau) getSharedData(SD_MORCEAU_ACTUEL);
        List<Piste> listePistes = morceau.getListPiste();
        ListView listPisteElements = (ListView) findViewById(R.id.track_list_view);
        trackListAdapter = new TrackListAdapter(this, 0, listePistes);
        listPisteElements.setAdapter(trackListAdapter);

    }


    class TrackListAdapter extends ArrayAdapter {

        private final List<Piste> listPiste;

        public TrackListAdapter(Context context, int resource, List<Piste> listPiste) {
            super(context, resource, listPiste);

            this.listPiste = listPiste;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_list_item, parent, false);
            }

            final Piste piste = this.listPiste.get(position);

            View item = (View) convertView.findViewById(R.id.tracklist_item);
            ImageView picture = (ImageView) convertView.findViewById(R.id.trackitem_picture);
            TextView name = (TextView) convertView.findViewById(R.id.trackitem_piste_name);
            ToggleButton mute = (ToggleButton) convertView.findViewById(R.id.trackitem_mute_button);
            ToggleButton solo = (ToggleButton) convertView.findViewById(R.id.trackitem_solo_button);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceSharedData(SD_PISTE_ACTUELLE, piste);
                    Intent intent = new Intent(getApplicationContext(), OpenMorceau.class);
                    startActivity(intent);
                }
            });

            if (piste.getTitle() != null && piste.getTitle().isEmpty() == false) name.setText("test" +piste.getTitle());
            else name.setText("Piste sans nom");
            mute.setChecked(piste.getMuted());
            mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    piste.setMuted(isChecked);
                }
            });
            solo.setChecked(piste.getSolo());
            solo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    piste.setSolo(isChecked);
                }
            });
            //TODO: set picture
            //TODO: callback for mute button

            return convertView;
        }
    }


}
