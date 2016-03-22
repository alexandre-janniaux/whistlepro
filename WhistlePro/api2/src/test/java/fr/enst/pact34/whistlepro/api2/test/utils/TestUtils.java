package fr.enst.pact34.whistlepro.api2.test.utils;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by  Mohamed on 20/03/16.
 * Set of useful functions to perform the tests easily
 */
public class TestUtils {

    /**
     * Create a signal from a data file created by a Matlab script
     * (format : Fs = first line, signal = one value per line)
     * @param fileName of the file containing data
     * @return the signal created
     */
    public static Signal createSignalFromFile(String fileName)
    {
        ArrayList<String> lines = new ArrayList<>();

        //on ouvre le fichier pour pouvoir lire dedans
        BufferedReader fichier = null;
        try {
            fichier = new BufferedReader(
                    new FileReader(fileName));

            //variable tmp pour stocker la ligne lue
            String line;

            //boucle parcourant le fichier ligne par ligne
            //et caractere par caractere sur chaque ligne
            while ((line = fichier.readLine()) != null)
                if(line.isEmpty()==false) lines.add(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            //on tente de fermer le fichier
            try { fichier.close();} catch (IOException e) {}
        }

        Signal sig = new Signal();


        sig.setSamplingFrequency(Double.parseDouble(lines.get(0)));
        sig.setLength(lines.size()-1);
        for(int i = 1; i < lines.size();i++) {
            sig.setValue(i-1,Double.parseDouble(lines.get(i)));
        }

        return sig;
    }
}
