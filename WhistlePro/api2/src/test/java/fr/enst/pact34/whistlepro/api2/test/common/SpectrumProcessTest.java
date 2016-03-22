package fr.enst.pact34.whistlepro.api2.test.common;

import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessCopy;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.TestBuilder;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 20/03/16.
 */
public class SpectrumProcessTest {

    @Test
    public void test()
    {
        String inputDataFile = "../testData/features/signal.data";
        String outputDataFile = "../testData/features/fft.data";
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        Signal outputData = new Signal();
        Signal outputDataRef = TestUtils.createSignalFromFile(outputDataFile);

        // test setup
        TestBuilder<Signal,Signal> test = new TestBuilder<>(inputData,outputData,
                new StreamSimpleBase<>(new Signal(), new Signal(), new SpectrumProcess())
        );

        // test start
        test.startTest();

        //outputData verification

        assertEquals(outputDataRef.length(),outputData.length());

        assertEquals(outputDataRef.getSamplingFrequency(),outputData.getSamplingFrequency(),
                Math.max(outputDataRef.getSamplingFrequency()*1E-3,1E-14));

        for (int i = 0; i < outputDataRef.length(); i++) {
            assertEquals(outputDataRef.getValue(i),outputData.getValue(i),
                    Math.max(outputDataRef.getValue(i)*1E-3,1E-14));
        }

    }


}
