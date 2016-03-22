package fr.enst.pact34.whistlepro.api2.test.threading;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessCopy;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.TestBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 17/03/16.
 */
public class TestStreamFlow {
    @Test
    public void test()
    {
        //TODO amelioration a faire
        //variables
        Signal inputData = new Signal();
        Signal outputData = new Signal();

        StreamSimpleBase<Signal,Signal> s = new StreamSimpleBase<>(new Signal(), new Signal(), new FakeProcessCopy<Signal>());

        //input data setup
        inputData.setLength(5);
        for (int i = 0; i < inputData.length(); i++) {

            inputData.setValue(i,i);
        }

        // test setup
        TestBuilder<Signal,Signal> test = new TestBuilder<>(inputData,outputData,s);

        // test start
        for (int j = 0; j < 10; j++) {

            test.startTest();

            for (int i = 0; i < outputData.length(); i++) {
                assertEquals(inputData.getValue(i),outputData.getValue(i),0.01);
            }
        }

    }

}
