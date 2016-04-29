package fr.enst.pact34.whistlepro.api2.test.synthese;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.InstruGenerator;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.Synthese.PercuGenerator;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 28/04/16.
 */
public class InstruGeneratorTest {

    @Test
    public void testGeneration()
    {
        double Fs = 16000;
        InstruGenerator generator = new InstruGenerator(Fs);
        generator.addInstru(Instru.Type.Piano, 0.9, 0.9);

        Signal output = generator.generate(Instru.Type.Piano, 0.010,100);
        assertEquals(0.010*Fs,output.length(),Double.MIN_VALUE);

        output = generator.generate(Instru.Type.Piano, 0.020,100);
        assertEquals(0.020*Fs,output.length(),Double.MIN_VALUE);

        output = generator.generate(Instru.Type.Piano, 0.030,100);
        assertEquals(0.030*Fs,output.length(),Double.MIN_VALUE);

        //TODO ADD VERIFICATION ON VALUES
    }

}
