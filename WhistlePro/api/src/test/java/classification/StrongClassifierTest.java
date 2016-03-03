package classification;

import fr.enst.pact34.whistlepro.api.classification.FeaturedObjectInterface;
import fr.enst.pact34.whistlepro.api.classification.StrongClassifier;
import fr.enst.pact34.whistlepro.api.classification.WeakClassifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 03/03/16.
 */
public class StrongClassifierTest {


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
    public void testClassifying()
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

        StrongClassifier sc = new StrongClassifier.Builder()
                .add(classifier)
                .add(classifier2)
                .add(classifier3)
                .build();

        Sample test = new Sample();
        test.count = 5;

        test.vals = new double[]{0, 1, 1, 1, 0};
        //assertEquals((int)classifier.classify(test),-1);
        //assertEquals((int)classifier2.classify(test),1);
        //assertEquals((int)classifier3.classify(test),1);
        assertEquals(sc.classify(test), -1*5.2+1*4.8+1*0.4, Double.MIN_VALUE);

        test.vals = new double[]{0, 2, 2, 2, 0};
        //assertEquals((int)classifier.classify(test),1);
        //assertEquals((int)classifier2.classify(test),1);
        //assertEquals((int)classifier3.classify(test),-1);
        assertEquals(sc.classify(test), 1*5.2+1*4.8-1*0.4, Double.MIN_VALUE);

        test.vals = new double[]{0, -1, -1, -1, 0};
        //assertEquals((int)classifier.classify(test),-1);
        //assertEquals((int)classifier2.classify(test),-1);
        //assertEquals((int)classifier3.classify(test),1);
        assertEquals(sc.classify(test), -1*5.2-1*4.8+1*0.4, Double.MIN_VALUE);

    }

    @Test
    public void testClassifyingStr()
    {
        StrongClassifier sc = new StrongClassifier.Builder()
                .fromString(
                "<weakClassifier>"+"feature=2;threshold=1.5;isLeft=true;coef=5.2;"+"</weakClassifier>"
                +"<weakClassifier>"+"feature=3;threshold=1;isLeft=true;coef=4.8;"+"</weakClassifier>"
                +"<weakClassifier>"+"feature=1;threshold=1;isLeft=false;coef=0.4;"+"</weakClassifier>"
                )
                .build();

        Sample test = new Sample();
        test.count = 5;

        test.vals = new double[]{0, 1, 1, 1, 0};
        //assertEquals((int)classifier.classify(test),-1);
        //assertEquals((int)classifier2.classify(test),1);
        //assertEquals((int)classifier3.classify(test),1);
        assertEquals(sc.classify(test), -1*5.2+1*4.8+1*0.4, Double.MIN_VALUE);

        test.vals = new double[]{0, 2, 2, 2, 0};
        //assertEquals((int)classifier.classify(test),1);
        //assertEquals((int)classifier2.classify(test),1);
        //assertEquals((int)classifier3.classify(test),-1);
        assertEquals(sc.classify(test), 1*5.2+1*4.8-1*0.4, Double.MIN_VALUE);

        test.vals = new double[]{0, -1, -1, -1, 0};
        //assertEquals((int)classifier.classify(test),-1);
        //assertEquals((int)classifier2.classify(test),-1);
        //assertEquals((int)classifier3.classify(test),1);
        assertEquals(sc.classify(test), -1*5.2-1*4.8+1*0.4, Double.MIN_VALUE);

    }

}
