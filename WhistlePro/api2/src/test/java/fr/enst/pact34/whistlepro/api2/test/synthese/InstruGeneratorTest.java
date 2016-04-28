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
        Signal inputData = new Signal();
        double Fs = 16000;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));

        double t_step = 1 / Fs;
        for (int i = 0; i < inputData.length(); i++) {
            inputData.setValue(i,  Math.sin(2.0 * Math.PI * 2000 * i * t_step));
        }

        InstruGenerator generator = new InstruGenerator(Fs);
        generator.addInstru(Instru.Type.Piano, inputData);

        Signal output = generator.generate(Instru.Type.Piano, 0.010);
        assertEquals(inputData.length()/2,output.length());
        for(int i = 0; i < output.length(); i++)
        {
            assertEquals(inputData.getValue(i%inputData.length()),output.getValue(i),Double.MIN_VALUE);
        }
        output = generator.generate(Instru.Type.Piano, 0.020);
        assertEquals(inputData.length(),output.length());
        for(int i = 0; i < output.length(); i++)
        {
            assertEquals(inputData.getValue(i%inputData.length()),output.getValue(i),Double.MIN_VALUE);
        }
        output = generator.generate(Instru.Type.Piano, 0.030);
        assertEquals(inputData.length()*3/2,output.length());
        for(int i = 0; i < output.length(); i++)
        {
            assertEquals(inputData.getValue(i%inputData.length()),output.getValue(i),Double.MIN_VALUE);
        }
    }

}
