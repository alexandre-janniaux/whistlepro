package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.os.Bundle; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import fr.enst.pact34.whistlepro.demo.R;

/**
 * Created by mms on 26/04/16.
 */
public abstract class WhistleProActivity extends Activity {

    //constant names for shared data
    public static String SD_PROCESSING_MACINE = "processing_machine";
    public static String SD_RECORDER = "recorder";
    public static String SD_CLASSIFIER_DATA = "classif_data";


    private static boolean initialzed = false;
    private static Hashtable<String,Object> sharedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(initialzed == false )
        {
            sharedData = new Hashtable<>();
            addSharedData(SD_CLASSIFIER_DATA, readRawTextFile(R.raw.voyelles_k20));
            initialzed = true;
        }
    }

    protected void addSharedData(String name, Object data)
    {
        if(sharedData.containsKey(name)) throw new RuntimeException("Data name already exists");

        sharedData.put(name,data);
    }

    protected Object getSharedData(String name)
    {
        if(sharedData.containsKey(name)==false) throw new RuntimeException("Data doesn't exists");

        return sharedData.get(name);
    }


    public String readRawTextFile(int resId)
    {

        InputStream inputStream =  getApplicationContext().getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
}
