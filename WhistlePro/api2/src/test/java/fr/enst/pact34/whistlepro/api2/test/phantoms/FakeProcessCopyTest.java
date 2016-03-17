package fr.enst.pact34.whistlepro.api2.test.phantoms;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessCopy;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.common.StreamDataEnd;
import fr.enst.pact34.whistlepro.api2.test.common.StreamDataPutter;
import fr.enst.pact34.whistlepro.api2.test.common.TestBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 15/03/16.
 */
public class FakeProcessCopyTest {

    @Test
    public void test()
    {
        //variables
        Signal inputData = new Signal();
        Signal outputData = new Signal();

        //input data setup
        inputData.setLength(5);
        for (int i = 0; i < inputData.length(); i++) {

            inputData.setValue(i,i);
        }

        // test setup
        TestBuilder<Signal,Signal> test = new TestBuilder<>(inputData,outputData,
                new StreamSimpleBase<>(inputData, outputData, new FakeProcessCopy<Signal>())
                );

        // test start
        test.startTest();

        //outputData verification

        for (int i = 0; i < outputData.length(); i++) {
            assertEquals(inputData.getValue(i),outputData.getValue(i),0.01);
        }

    }
}
