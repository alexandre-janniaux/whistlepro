package fr.enst.pact34.whistlepro.app.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.enst.pact34.whistlepro.app.R;


public class QuickRecorderFragment extends Fragment {

    private Button buttonRecordRhythm;
    private Button buttonRecordMelody;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_quick_recorder, container, false);
        Bundle args = getArguments();
        //((TextView) rootView.findViewById(android.R.id.text1)).setText(
        //        Integer.toString(args.getInt(ARG_OBJECT)));
        //this.buttonRecordMelody = (Button) rootView.findViewById(R.id.record_melody_button);
        //this.buttonRecordRhythm = (Button) rootView.findViewById(R.id.record_rhythm_button);

        return rootView;
    }

    public void onButtonRecordPressed(View v) {
        if (v == this.buttonRecordRhythm) {

        } else if (v == this.buttonRecordMelody) {

        }
    }
}
