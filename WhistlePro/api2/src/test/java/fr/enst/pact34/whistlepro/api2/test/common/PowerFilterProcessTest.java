package fr.enst.pact34.whistlepro.api2.test.common;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.PowerFilterProcess;
import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.TestBuilder;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mms on 20/03/16.
 */
public class PowerFilterProcessTest {

    @Test
    public void spectrumTest()
    {

        Signal inputData = new Signal();
        Signal outputData = new Signal();

        // test setup
        TestBuilder<Signal,Signal> test = new TestBuilder<>(inputData,outputData,
                new StreamSimpleBase<>(new Signal(), new Signal(), new PowerFilterProcess())
        );

        //valid sig
        inputData.fromArray(new double[]{1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,1});
        // test start
        test.startTest();

        //outputData verification
        assertTrue(outputData.isValid());
        assertEquals(inputData.length(), outputData.length(), 0.01);
        for (int i = 0; i < outputData.length(); i++) {
            assertEquals(inputData.getValue(i),outputData.getValue(i),0.01);
        }

        //invalid sig
        inputData.fromArray(new double[]{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        // test start
        test.startTest();

        //outputData verification
        assertTrue(outputData.isValid()==false);
    }


}
