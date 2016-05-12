package fr.enst.pact34.whistlepro.api2.test.synthese;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.Synthese.PercuGenerator;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 28/04/16.
 */
public class PercuGeneratorTest {

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

        PercuGenerator generator = new PercuGenerator(Fs);
        generator.addPercu(Percu.Type.Kick,inputData);
/*
        Signal output = generator.generate(Percu.Instrument.Kick, 0.010);
        assertEquals(inputData.length()/2,output.length());
        for(int i = 0; i +inputData.length()< output.length(); i++)
        {
            assertEquals(inputData.getValue(i%inputData.length()),output.getValue(i),Double.MIN_VALUE);
        }
        output = generator.generate(Percu.Instrument.Kick, 0.020);
        assertEquals(inputData.length(),output.length());
        for(int i = 0; i +inputData.length() < output.length(); i++)
        {
            assertEquals(inputData.getValue(i%inputData.length()),output.getValue(i),Double.MIN_VALUE);
        }
        output = generator.generate(Percu.Instrument.Kick, 0.030);
        assertEquals(inputData.length()*3/2,output.length());
        for(int i = 0; i+inputData.length() < output.length(); i++)
        {
            assertEquals(inputData.getValue(i%inputData.length()),output.getValue(i),Double.MIN_VALUE);
        }*/
        // TODO redo test
    }

}
