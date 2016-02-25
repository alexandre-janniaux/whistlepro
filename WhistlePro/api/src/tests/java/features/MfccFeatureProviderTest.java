import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import features.MfccFeatureProvider;

class MfccFeatureProviderTest {

    @Test
    public void numberOfFeatures() {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        assertEquals(13, mfcc.countFeatures());
    }

    @Test
    public void frequencyToMel() {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        // TODO: implement test
        throw new UnsupportedOperationException("Le test de calcul du signal en échelle de mél doit être fait");
    }

    @Test
    public void melToFrequency() {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
        double mel = 100., freq = 100.;
        assertTrue(mfcc.melToFrequency(100.)-freq <= Double.MIN_VALUE); // TODO: precalculate mel coeff
    }

    @Test
    public void computeFilterFrequencies()
    {
        MfccFeatureProvider mfcc = new MfccFeatureProvider();
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
