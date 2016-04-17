package fr.enst.pact34.whistlepro.api.Attaque;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import fr.enst.pact34.whistlepro.api.acquisition.Affichage2;
import fr.enst.pact34.whistlepro.api.acquisition.ReadExample;

/**
 * Created by dell on 10/03/2016.
 */
public class Pics {

    //methode retournant la valeur maximale de la liste de double
    private double max(double[] x) {
        if(x.length==0) return 0;
        double max = x[0];
        for (int i = 1; i < x.length; i++) {
            if (x[i] > max) {
                max = x[i];
            }
        }
        return max;
    }
    //Affiche une liste de pics avec la méthode naive qui consiste a prendre un point
    // tant qu'il est superieur au point precedent ET au suivant
    public double[] DetectionPics(double[] x) {
        ArrayList<Double> pics = new ArrayList<Double>();   //on prend des ArrayList car on ne sait pas cb de pics on aura
        ArrayList<Integer> picIndice = new ArrayList<Integer>();
        int len = x.length;

        for (int i = 1; i < len - 1; i++) {
            if (Math.abs(x[i-1]) < Math.abs(x[i]) && Math.abs(x[i+1]) < Math.abs(x[i]) && Math.abs(x[i])>0.1*max(x)) {
                pics.add(x[i]);
                picIndice.add(i);
            }
        }

        int length = pics.size();   //on transforme l'ArrayList pics en liste de double, c'est pour le test
        double[] picDouble = new double[length];
        for (int i = 0; i < length; i++) {
            picDouble[i] = (double) pics.get(i);
        }

        int lengthIndice = picIndice.size();   //on transforme l'ArrayList picIndice en liste de double
        double[] picIndiceDouble = new double[lengthIndice];
        for (int i = 0; i < lengthIndice; i++) {
            picIndiceDouble[i] = (double) picIndice.get(i);
        }

        return picIndiceDouble;   //picDouble pour la valeur du signal, picIndiceDouble pour l'abscisse des valeurs du signal
    }

    //methode effectuant toutes les operations precedentes en 1 appel
    public double[] tAttaque(File file) {

        Affichage2 affich = new Affichage2();
        Pics pics = new Pics();

        double[] x = ReadExample.audioRead(file);

        double[] e = Enveloppe.enveloppe(0.99, x);

        double[] e2 = Enveloppe.sousEchantillonne(200, e);

        double[] derive = Enveloppe.derive(10, e2);

        double[] detectionPics = pics.DetectionPics(derive);

        return detectionPics;
    }



}
