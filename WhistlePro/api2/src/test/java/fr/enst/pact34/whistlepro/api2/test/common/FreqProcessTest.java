package fr.enst.pact34.whistlepro.api2.test.common;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.FreqProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.TestBuilder;


/**
 * Created by mms on 23/04/16.
 */
public class FreqProcessTest {

    @Test
    public void freqProcessTest()
    {
        Signal inputData = new Signal();
        //inputData.fromArray(new double[]{2,3,-1});

        double Fs = 44100;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int)(0.020*Fs));
        double freq =  3322;
        double t_step = 1/Fs;
        for(int i = 0; i < inputData.length();i++)
        {
            inputData.setValue(i, Math.sin(2.0*Math.PI*freq*i*t_step)+Math.random()*0.1);
        }

        // test setup
        Frequency outputData = new Frequency();
        TestBuilder<Signal,Frequency> test = new TestBuilder<>(inputData,outputData,
                new StreamSimpleBase<>(new Signal(), new Frequency(), new FreqProcess())
        );

        for (int i = 0; i < 5; i++) {

            test.startTest();
        }

        System.out.print(outputData.getFrequency());

        //TODO real test with results
    }
}
