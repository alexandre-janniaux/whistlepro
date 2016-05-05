package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import fr.enst.pact34.whistlepro.demo.views.PianoRollView;


public class PianoRollActivity extends Activity {

    private PianoRollView piano;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        piano = new PianoRollView(this);
        layout = new LinearLayout(this);

        layout.addView(piano);

        setContentView(layout);
    }
}
