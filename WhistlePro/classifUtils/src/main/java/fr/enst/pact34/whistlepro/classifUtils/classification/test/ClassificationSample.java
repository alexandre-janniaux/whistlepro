package fr.enst.pact34.whistlepro.classifUtils.classification.test;

import fr.enst.pact34.whistlepro.api.classification.FeaturedObjectInterface;

import java.util.ArrayList;

/**
 * Created by mms on 27/02/16.
 */
public class ClassificationSample implements FeaturedObjectInterface {

    String classe = "";
    ArrayList<Double> dbl = new ArrayList<Double>();

    public ClassificationSample(ArrayList<Double> dbl) {
        this.dbl = dbl;
    }

    @Override
    public double getFeature(int number) {

        return dbl.get(number);
    }

    @Override
    public int countFeatures() {

        return dbl.size();
    }
}
