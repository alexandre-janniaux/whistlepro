package fr.enst.pact34.whistlepro.demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.Hashtable;
import java.util.concurrent.locks.Lock;

/**
 * Created by mms on 26/04/16.
 */
public abstract class WhistleProActivity extends Activity {

    //constant names for shared data
    public static String SD_PROCESSING_MACINE = "processing_machine";


    private static boolean initialzed = false;
    private static Hashtable<String,Object> sharedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("testtt", "going to initialisationnnnn");
        if(initialzed == false )
        {
            sharedData = new Hashtable<>();
            initialzed = true;
            Log.d("testtt","initialisationnnnn");
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
}
