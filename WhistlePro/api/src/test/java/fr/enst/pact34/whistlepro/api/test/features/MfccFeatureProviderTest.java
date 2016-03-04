package fr.enst.pact34.whistlepro.api.test.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import fr.enst.pact34.whistlepro.api.features.MfccFeatureProvider;

class MfccFeatureProviderTest {

    @Test
    public void numberOfFeatures() {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        assertEquals(13, mfcc.countFeatures());
    }


    @Test
    public void computePower()
    {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        double[] signal = {0,1.,2.,3.,4.,5.,6.,7.};
        assertTrue(mfcc.computePower(signal) - 28. <= Double.MIN_VALUE);
    }


    @Test
    public void mfcc() {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        // TODO: implement test
        throw new UnsupportedOperationException("Le test de calcul des MFCC doit être fait");
    }


}
