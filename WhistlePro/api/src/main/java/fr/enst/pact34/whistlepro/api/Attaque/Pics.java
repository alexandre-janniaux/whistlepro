package fr.enst.pact34.whistlepro.api.Attaque;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dell on 10/03/2016.
 */
public class Pics {

    public double[] DetectionPics(double[] x) {
        ArrayList<Double> pics = new ArrayList<Double>();
        ArrayList<Integer> picIndice = new ArrayList<Integer>();
        int len = x.length;
        for (int i = 1; i < len - 1; i++) {
            if (x[i - 1] < x[i] && x[i + 1] < x[i]) {
                pics.add(x[i]);
                picIndice.add(i);
            }
        }

        int length = pics.size();
        double[] picDouble = new double[length];
        for (int i = 0; i < length; i++) {
            picDouble[i] = (double) pics.get(i);
        }

        return picDouble;
    }

    public double[] PicsReduced(double[] x) {

        ArrayList<Double> pics = new ArrayList<Double>();
        ArrayList<Integer> picIndice = new ArrayList<Integer>();
        int len = x.length;
        int compteur = 0;

        for (int j = 0; j < len; j++) {
            ArrayList<Double> sousPics = new ArrayList<Double>();
            if (compteur == 5) {
                compteur = 0;
                sousPics = null;
                sousPics.add(x[j]);
                for (int k = j - 4; k < j; k++) {
                    if (x[k] == Collections.max(sousPics)) {
                        pics.add(x[k]);
                        picIndice.add(k);
                    }
                }
            }
            compteur++;
        }

        int length = pics.size();
        double[] picDouble = new double[length];
        for (int i = 0; i < length; i++) {
            picDouble[i] = (double) pics.get(i);
        }

        return picDouble;

    }

}
