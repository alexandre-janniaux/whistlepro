package fr.enst.pact34.whistlepro.app;

import android.app.Activity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.common.FileOperator;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;
import fr.enst.pact34.whistlepro.api2.main.ProcessorInterface;
import fr.enst.pact34.whistlepro.api2.main.TypePiste;

/**
 * Created by mms on 26/04/16.
 */
public abstract class WhistleProActivity extends Activity {

    //constant names for shared data
    public static String SD_PROCESSING_MACINE = "processing_machine";
    public static String SD_RECORDER = "recorder";
    public static String SD_MORCEAU_ACTUEL = "morceau_actuel";
    public static String SD_LISTE_MORCEAU= "liste_morceau";
    public static String SD_PISTE_ACTUELLE = "piste_actuelle";


    private static boolean initialzed = false;
    private static Hashtable<String,Object> sharedData;
    private static List<SavedMorceau> listeMorceau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(initialzed == false )
        {
            sharedData = new Hashtable<>();
            // add recorder
            Recorder recorder = new Recorder();
            addSharedData(SD_RECORDER, recorder);
            //add processing machine
            ProcessingMachine processor = new ProcessingMachine(recorder.getSampleRate(),
                    readRawTextFile(R.raw.voyelles_k20),
                    4, TypePiste.Percussions);
            addSharedData(SD_PROCESSING_MACINE, processor);

            recorder.setListener(processor);

            //TODO config for instru

            processor.setPercuCorrespondance("a", Percu.Type.Kick);
            processor.setPercuCorrespondance("e",Percu.Type.CaisseClaire);
            processor.setPercuCorrespondance("o", Percu.Type.Charleston);

            //TODO sons bateaux a changer
            double Fs = 16000; //la synthese est faite en 16000 ne pas changer sans changer la synthese
            // tous les signaux doivent etre en 16000
            // les signaux sont répétés et mis les uns a la suite des autres
            // pour eviter des "sauts" il faut qu'ils commencent à 0 et finissent à 0.
            Signal sigCaisseClaire = new Signal();
            sigCaisseClaire.setSamplingFrequency(Fs);
            sigCaisseClaire.setLength((int) ((1.0/440) * Fs)+1);

            double t_step = 1 / Fs;
            for (int i = 0; i < sigCaisseClaire.length(); i++) {
                sigCaisseClaire.setValue(i,  Math.sin(2.0 * Math.PI * 440 * i * t_step));
            }
            processor.addPercuData(Percu.Type.Kick, sigCaisseClaire);

            Signal sigKick = new Signal();
            sigKick.setSamplingFrequency(Fs);
            sigKick.setLength((int) ((1.0/493) * Fs)+1);
            for (int i = 0; i < sigKick.length(); i++) {
                sigKick.setValue(i,  Math.sin(2.0 * Math.PI * 493 * i * t_step));
            }
            processor.addPercuData(Percu.Type.CaisseClaire, sigKick);

            Signal sigCharleston = new Signal();
            sigCharleston.setSamplingFrequency(Fs);
            sigCharleston.setLength((int) ((1.0/392) * Fs)+1);
            for (int i = 0; i < sigCharleston.length(); i++) {
                sigCharleston.setValue(i,  Math.sin(2.0 * Math.PI * 392 * i * t_step));
            }
            processor.addPercuData(Percu.Type.Charleston, sigCharleston);

            //chargement donnees
            loadMorceauList();
            updateMorceauList();

            initialzed = true;
        }
    }

    private void updateMorceauList()
    {
        List<Morceau> lm = new LinkedList<>();
        for (SavedMorceau m :
                listeMorceau) {
            lm.add(m.getMorceau());
        }
        addSharedData(SD_LISTE_MORCEAU, Collections.unmodifiableList(lm));
    }

    protected void replaceSharedData(String name, Object data)
    {
        sharedData.put(name, data);
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

    protected void saveMorceau(Morceau morceau)
    {
        File fileDirectory = getFilesDir();
        for (SavedMorceau sm :
                listeMorceau) {
            if(sm.getMorceau()==morceau)
            {
                File f = new File(fileDirectory, sm.getFileName());
                FileOperator.saveToFile(f, morceau.getSaveString());
                return;
            }
        }

        String name = "morceau_" + fileDirectory.listFiles().length;
        File f = new File(fileDirectory, name);
        FileOperator.saveToFile(f, morceau.getSaveString());
        listeMorceau.add(new SavedMorceau(morceau,name));
        
        updateMorceauList();
    }

    private static class SavedMorceau
    {
        public Morceau m;
        public String fileName ="";

        public SavedMorceau(Morceau m, String fileName) {
            this.m = m;
            this.fileName = fileName;
        }

        public Morceau getMorceau() {
            return m;
        }

        public String getFileName() {
            return fileName;
        }
    }
    private void loadMorceauList()
    {
        File fileDirectory = getFilesDir();
        listeMorceau = new LinkedList<>();
        for (File f :
                fileDirectory.listFiles()) {
            Morceau.Builder builder = new Morceau.Builder();
            builder.fromString(FileOperator.getDataFromFile(f));
            if(builder.dataValid())
            {
                Morceau morceau = builder.build();
                listeMorceau.add(new SavedMorceau(morceau,f.getName()));
            }
        }
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
