package fr.enst.pact34.whistlepro.api2.test.common;

import org.junit.Test;

import java.util.LinkedList;

import fr.enst.pact34.whistlepro.api2.common.SplitterProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.States;
import fr.enst.pact34.whistlepro.api2.stream.StreamDataListenerInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamInputWraper;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 17/04/16.
 */
public class SplitterTest implements StreamDataListenerInterface<Signal> {


    LinkedList<Signal> outputData =  new LinkedList<>();

    @Test
    public void splitterTest()
    {
        double[] inputData =  new double[]{0, 1, 2, 3, 4, 5, 6, 4, 8, 9, 10, 11};


        // test setup
        int nbSample = 2;
        double Fs = 123;
        StreamProcessInterface<LinkedList<double[]>,LinkedList<Signal>> splitterProcess = new SplitterProcess(nbSample, Fs); //new FakeProcessCopy<>(); //TODO put real process
        StreamInputWraper<double[], Signal> splitterStream = new StreamInputWraper<>(new Signal(), splitterProcess);

        splitterStream.subscribe(this);


        // test start
        splitterStream.fillBufferIn(inputData);
        splitterStream.process();
        while(splitterStream.getOutputState() == States.OUTPUT_BUSY)
            splitterStream.pushData();


        //outputData verification
        assertEquals((int)(Math.floor(inputData.length/nbSample)+Math.floor((inputData.length-nbSample)/nbSample)),outputData.size());

        for (int i = 0; i < nbSample; i+=nbSample) {
            for (int j = 0; j < nbSample; j++) {
                assertEquals(outputData.get(i).getValue(j),inputData[i+j],Double.MIN_VALUE);
            }
        }

    }

    @Override
    public void fillBufferIn(Signal data) {
        Signal s = new Signal();
        data.copyTo(s);
        outputData.add(s);
    }

    @Override
    public int getInputState() {
        return 0;
    }
}
