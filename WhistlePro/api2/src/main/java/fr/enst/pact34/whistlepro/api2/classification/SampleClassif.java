package fr.enst.pact34.whistlepro.api2.classification;

import java.util.ArrayList;

/**
 * Created by mms on 27/02/16.
 */
public class SampleClassif implements FeaturedObjectInterface {


    private ArrayList<Double> dbl ;

    public SampleClassif(Builder builder) {
        dbl = builder.dbl;
    }


    @Override
    public double getFeature(int featureIndex) {
        return dbl.get(featureIndex);
    }

    @Override
    public int countFeatures() {
        return 13;
    }


    public static class Builder
    {

        private ArrayList<Double> dbl = new ArrayList<>();

        public Builder fromValues(ArrayList<Double> dbls)
        {
            dbl =(ArrayList<Double>) dbls.clone();
            return this;
        }

        public SampleClassif build()
        {
            if(dbl.size()!=13 ) return null;

            return new SampleClassif(this);
        }


    }
}
