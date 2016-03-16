package fr.enst.pact34.whistlepro.api2.test.phantoms;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessCopy;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.common.StreamDataEnd;
import fr.enst.pact34.whistlepro.api2.test.common.StreamDataPutter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 15/03/16.
 */
public class FakeProcessCopyTest {

    @Test
    public void test()
    {
        //input data setup
        Signal inputData = new Signal();

        inputData.setLength(5);

        for (int i = 0; i < inputData.length(); i++) {

            inputData.setValue(i,i);
        }

        // setup
        StreamDataPutter<Signal> start = new StreamDataPutter<>(inputData);

        StreamSimpleBase<Signal, Signal> powerFilterStream = new StreamSimpleBase<>(new Signal(), new Signal(), new FakeProcessCopy<Signal>());

        start.subscribe(powerFilterStream);

        StreamDataEnd<Signal> end = new StreamDataEnd<>(new Signal());
        powerFilterStream.subscribe(end);

        //start

        start.pushData();

        //outData
        Signal outputData = end.getBuffer();

        for (int i = 0; i < outputData.length(); i++) {
            assertEquals(inputData.getValue(i),outputData.getValue(i),0.01);
        }

    }
}
