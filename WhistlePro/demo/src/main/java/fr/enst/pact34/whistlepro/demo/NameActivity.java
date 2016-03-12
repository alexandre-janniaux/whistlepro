package fr.enst.pact34.whistlepro.demo;

import android.content.DialogInterface;
import android.content.Intent;
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
/** /!\ IMPORTANT /!\
 NOTE TO READER :
 TO DO : point to the right functions
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);
        ImageButton listenBtn = (ImageButton)findViewById(R.id.playBack);
        listenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TO DO : put function play sound that was captured in the last activity
            }
        });

        ImageButton backBtn = (ImageButton)findViewById(R.id.goBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NameActivity.this, EnregistrementActivity.class));
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

        ImageButton nextBtn = (ImageButton)findViewById(R.id.next);
        nextBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NameActivity.this, CorrectionActivity.class));
            }
        });

    }
}