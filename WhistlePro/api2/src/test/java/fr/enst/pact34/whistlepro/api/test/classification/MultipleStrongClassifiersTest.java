package test.java.fr.enst.pact34.whistlepro.api.test.classification;

import fr.enst.pact34.whistlepro.api.classification.FeaturedObjectInterface;
import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api.classification.StrongClassifier;
import fr.enst.pact34.whistlepro.api.classification.WeakClassifier;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mms on 08/03/16.
 */
public class MultipleStrongClassifiersTest {

    private boolean strContains(ArrayList<String> strList, String str)
    {
        for (String s: strList
             ) {
           if (s.equals(str)) return true;
        }

        return false;
    }

    @Test
    public void testBuilderStr() {
        MultipleStrongClassifiers msc =
                new MultipleStrongClassifiers.Builder().fromString(
                        "<MultipleClassifierItem classe='AA' >"
                                + "<weakClassifier>" + "feature=2;threshold=1.5;isLeft=true;coef=5.2;" + "</weakClassifier>"
                                + "<weakClassifier>" + "feature=3;threshold=1;isLeft=true;coef=4.8;" + "</weakClassifier>"
                                + "</MultipleClassifierItem >"
                                + "<MultipleClassifierItem classe='BB' >"
                                + "<weakClassifier>" + "feature=2;threshold=1.5;isLeft=true;coef=5.2;" + "</weakClassifier>"
                                + "<weakClassifier>" + "feature=1;threshold=1;isLeft=false;coef=0.4;" + "</weakClassifier>"
                                + "</MultipleClassifierItem >"
                                + "<MultipleClassifierItem classe='CC' >"
                                + "<weakClassifier>" + "feature=3;threshold=1;isLeft=true;coef=4.8;" + "</weakClassifier>"
                                + "<weakClassifier>" + "feature=1;threshold=1;isLeft=false;coef=0.4;" + "</weakClassifier>"
                                + "</MultipleClassifierItem >"
                ).build();

        ArrayList<String> classes = msc.classes();

        assertEquals(3, classes.size());

        assertTrue(strContains(classes, "AA"));
        assertTrue(strContains(classes, "BB"));
        assertTrue(strContains(classes, "CC"));
    }

    @Test
    public void testBuilder()
    {
        WeakClassifier classifier =
                new WeakClassifier.Builder()
                        .setFeatureIndex(2)
                        .setThreshold(1.5)
                        .setLeft(true)
                        .setCoef(5.2)
                        .build();

        WeakClassifier classifier2 =
                new WeakClassifier.Builder()
                        .setFeatureIndex(3)
                        .setThreshold(1)
                        .setLeft(true)
                        .setCoef(4.8)
                        .build();

        WeakClassifier classifier3 =
                new WeakClassifier.Builder()
                        .setFeatureIndex(1)
                        .setThreshold(1)
                        .setLeft(false)
                        .setCoef(0.4)
                        .build();

        StrongClassifier AA = new StrongClassifier.Builder()
                .add(classifier)
                .add(classifier2)
                .build();

        StrongClassifier BB = new StrongClassifier.Builder()
                .add(classifier)
                .add(classifier3)
                .build();

        StrongClassifier CC = new StrongClassifier.Builder()
                .add(classifier2)
                .add(classifier3)
                .build();

        MultipleStrongClassifiers msc =
                new MultipleStrongClassifiers.Builder()
                        .addClassifier("AA",AA)
                        .addClassifier("BB",BB)
                        .addClassifier("CC",CC)
                .build();

        ArrayList<String> classes = msc.classes();

        assertEquals(3,classes.size());

        assertTrue(strContains(classes,"AA"));
        assertTrue(strContains(classes,"BB"));
        assertTrue(strContains(classes,"CC"));
    }

    public static class Sample implements FeaturedObjectInterface
    {

        public double vals[] = new double[]{0.,0.,0.,0.,0.};
        public int count = 5 ;

        @Override
        public double getFeature(int featureIndex) {
            return vals[featureIndex];
        }

        @Override
        public int countFeatures() {
            return count;
        }
    }

    @Test
    public void testClassify()
    {
        WeakClassifier classifier =
                new WeakClassifier.Builder()
                        .setFeatureIndex(2)
                        .setThreshold(1.5)
                        .setLeft(true)
                        .setCoef(5.2)
                        .build();

        WeakClassifier classifier2 =
                new WeakClassifier.Builder()
                        .setFeatureIndex(3)
                        .setThreshold(1)
                        .setLeft(true)
                        .setCoef(4.8)
                        .build();

        WeakClassifier classifier3 =
                new WeakClassifier.Builder()
                        .setFeatureIndex(1)
                        .setThreshold(1)
                        .setLeft(false)
                        .setCoef(0.4)
                        .build();

        StrongClassifier AA = new StrongClassifier.Builder()
                .add(classifier)
                .add(classifier2)
                .build();

        StrongClassifier BB = new StrongClassifier.Builder()
                .add(classifier)
                .add(classifier3)
                .build();

        StrongClassifier CC = new StrongClassifier.Builder()
                .add(classifier2)
                .add(classifier3)
                .build();

        MultipleStrongClassifiers msc =
                new MultipleStrongClassifiers.Builder()
                        .addClassifier("AA",AA)
                        .addClassifier("BB",BB)
                        .addClassifier("CC",CC)
                .build();

        ArrayList<String> classes = msc.classes();

        Hashtable<String,StrongClassifier> table = new Hashtable<>();
        table.put("AA",AA);
        table.put("BB",BB);
        table.put("CC",CC);


        Sample test = new Sample();
        test.count = 5;

        test.vals = new double[]{0, 1, 1, 1, 0};
        ArrayList<Double> result = msc.classify(test);
        for(int i = 0; i < result.size(); i ++) {
            assertEquals(result.get(i),
                    table.get(classes.get(i)).classify(test),
                    Double.MIN_VALUE);
        }

        test.vals = new double[]{0, 2, 2, 2, 0};
        result = msc.classify(test);
        for(int i = 0; i < result.size(); i ++) {
            assertEquals(result.get(i),
                    table.get(classes.get(i)).classify(test),
                    Double.MIN_VALUE);
        }

        test.vals = new double[]{0, -1, -1, -1, 0};
        result = msc.classify(test);
        for(int i = 0; i < result.size(); i ++) {
            assertEquals(result.get(i),
                    table.get(classes.get(i)).classify(test),
                    Double.MIN_VALUE);
        }
    }


    @Test
    public void testClassifyStr() {
        WeakClassifier classifier =
                new WeakClassifier.Builder()
                        .setFeatureIndex(2)
                        .setThreshold(1.5)
                        .setLeft(true)
                        .setCoef(5.2)
                        .build();

        WeakClassifier classifier2 =
                new WeakClassifier.Builder()
                        .setFeatureIndex(3)
                        .setThreshold(1)
                        .setLeft(true)
                        .setCoef(4.8)
                        .build();

        WeakClassifier classifier3 =
                new WeakClassifier.Builder()
                        .setFeatureIndex(1)
                        .setThreshold(1)
                        .setLeft(false)
                        .setCoef(0.4)
                        .build();

        StrongClassifier AA = new StrongClassifier.Builder()
                .add(classifier)
                .add(classifier2)
                .build();

        StrongClassifier BB = new StrongClassifier.Builder()
                .add(classifier)
                .add(classifier3)
                .build();

        StrongClassifier CC = new StrongClassifier.Builder()
                .add(classifier2)
                .add(classifier3)
                .build();

        MultipleStrongClassifiers msc =
                new MultipleStrongClassifiers.Builder()
                        .addClassifier("AA", AA)
                        .addClassifier("BB", BB)
                        .addClassifier("CC", CC)
                        .build();

        ArrayList<String> classes = msc.classes();

        Hashtable<String,StrongClassifier> table = new Hashtable<>();
        table.put("AA",AA);
        table.put("BB",BB);
        table.put("CC",CC);


        Sample test = new Sample();
        test.count = 5;

        test.vals = new double[]{0, 1, 1, 1, 0};
        ArrayList<Double> result = msc.classify(test);
        double max = -Double.MAX_VALUE;
        int i_max = -1;
        for(int i = 0; i < result.size(); i ++) {
            if(result.get(i)>max)
            {
                max = result.get(i);
                i_max = i;
            }
        }
        assertTrue(classes.get(i_max).equals(msc.classifyStr(test)));

        test.vals = new double[]{0, 2, 2, 2, 0};
        result = msc.classify(test);
        max = -Double.MAX_VALUE;
        i_max = -1;
        for(int i = 0; i < result.size(); i ++) {
            if(result.get(i)>max)
            {
                max = result.get(i);
                i_max = i;
            }
        }
        assertTrue(classes.get(i_max).equals(msc.classifyStr(test)));

        test.vals = new double[]{0, -1, -1, -1, 0};
        result = msc.classify(test);
        max = -Double.MAX_VALUE;
        i_max = -1;
        for(int i = 0; i < result.size(); i ++) {
            if(result.get(i)>max)
            {
                max = result.get(i);
                i_max = i;
            }
        }
        assertTrue(classes.get(i_max).equals(msc.classifyStr(test)));

    }
}
