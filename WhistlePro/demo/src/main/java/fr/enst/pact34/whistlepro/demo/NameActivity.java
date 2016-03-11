package fr.enst.pact34.whistlepro.demo;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NameActivity extends AppCompatActivity {

    EditText nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);
        ImageButton listenBtn = (ImageButton)findViewById(R.id.listen);
        listenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TO DO : put function play sound tha was captured in the last activity
            }
        });

        ImageButton backBtn = (ImageButton)findViewById(R.id.back);
        backBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TO DO : put intent to switch to previous activity
            }
        });

        EditText nom = (EditText) findViewById(R.id.entreeNom);
        nom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handled = true;
                }
                return handled;
            }
        });
        String strName = nom.getText().toString();
        // TO DO : put function saveFile

    }
}
