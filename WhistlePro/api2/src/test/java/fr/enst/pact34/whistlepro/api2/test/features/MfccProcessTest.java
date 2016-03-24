package fr.enst.pact34.whistlepro.api2.test.features;

import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.features.MfccProcess;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.TestBuilder;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 20/03/16.
 */
public class MfccProcessTest {

    @Test
    public void test()
    {
        String inputDataFile = "../testData/features/fft.data";
        String outputDataFile = "../testData/features/mfcc.data";
        Spectrum inputData = TestUtils.createSpectrumFromFile(inputDataFile);
        Signal outputData = new Signal();
        Signal outputDataRef = TestUtils.createSignalFromFile(outputDataFile);

        // test setup
        TestBuilder<Spectrum,Signal> test = new TestBuilder<>(inputData,outputData,
                new StreamSimpleBase<>(new Spectrum(), new Signal(), new MfccProcess())
        );

        // test start
        test.startTest();

        //outputData verification

        assertEquals(outputDataRef.length(),outputData.length());

        assertEquals(outputDataRef.getSamplingFrequency(),outputData.getSamplingFrequency(),
                Math.max(outputDataRef.getSamplingFrequency()*1E-3,1E-14));

        for (int i = 0; i < outputDataRef.length(); i++) {
            assertEquals(outputDataRef.getValue(i),outputData.getValue(i),
                    Math.max(Math.abs(outputDataRef.getValue(i)*1E-3),1E-14));
        }

    }


}
