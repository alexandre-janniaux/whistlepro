import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import features.FilterBankMel;

class FilterBankMelTest {

    @Test
    public void frequencyToMel() {
        // TODO: implement test
        throw new UnsupportedOperationException("Le test de calcul du signal en échelle de mél doit être fait");
    }

    @Test
    public void melToFrequency() {
        double mel = 100., freq = 100.;
        assertTrue(FilterBankMel.melToFrequency(100.)-freq <= Double.MIN_VALUE); // TODO: precalculate mel coeff and round it
    }

    @Test
    public void computeFilterFrequencies()
    {
        throw new UnsupportedOperationException("Implémenter le test computeFilterFrequencies");
    }


}
