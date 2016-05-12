package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import fr.enst.pact34.whistlepro.demo.models.PianoRollModel;
import fr.enst.pact34.whistlepro.demo.views.GenericPianoRollView;
import fr.enst.pact34.whistlepro.demo.views.PianoRollView;


public class PianoRollActivity extends Activity {

    private PianoRollView piano;
    private PianoRollModel model;
    private LinearLayout layout;
    private GenericPianoRollView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new LinearLayout(this);


/*        model = new PianoRollModel();

        piano = new PianoRollView(this);
        piano.setModel(model);

        layout.addView(piano);
*/
        view = new GenericPianoRollView(this);
        view.addNote(0,5,0);
        view.addNote(1,4,1);

        layout.addView(view);

        setContentView(layout);
    }
}
