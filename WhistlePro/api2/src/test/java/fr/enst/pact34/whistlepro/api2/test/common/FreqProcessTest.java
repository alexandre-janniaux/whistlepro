package fr.enst.pact34.whistlepro.api2.test.common;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.FreqProcess;
import fr.enst.pact34.whistlepro.api2.common.NoteCorrector;
import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.TestBuilder;

import static org.junit.Assert.assertEquals;


/**
 * Created by mms on 23/04/16.
 */
public class FreqProcessTest {

    @Test
    public void freqProcess_16000_Test()
    {
        System.out.println("Le test ne marche pas toujours a cause du bruit aleatoire. ");

        Signal inputData = new Signal();
        //inputData.fromArray(new double[]{2,3,-1});

        double Fs = 16000;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));
        for (double freq: NoteCorrector.getNoteFreqs()
             ) {
            System.out.print("in :" + freq);
            double t_step = 1 / Fs;
            double alpha_bruit ;
            if(freq < 130 ) alpha_bruit = 0.1; //premiere gamme plus sensible au bruit
            else alpha_bruit = 0.2;
            for (int i = 0; i < inputData.length(); i++) {
                inputData.setValue(i, Math.sin(2.0 * Math.PI * freq * i * t_step) + Math.random() * alpha_bruit);
            }


            // test setup
            Frequency outputData = new Frequency();
            TestBuilder<Signal, Frequency> test = new TestBuilder<>(inputData, outputData,
                    new StreamSimpleBase<>(new Signal(), new Frequency(), new FreqProcess())
            );

            for (int i = 0; i < 5; i++) {

                test.startTest();
            }

            System.out.println(" => out :" +outputData.getFrequency());

            assertEquals(freq, outputData.getFrequency(), Double.MIN_VALUE);
        }

    }


    @Test
    public void freqProcess_44100_Test()
    {
        System.out.println("Le test ne marche pas toujours a cause du bruit aleatoire. ");

        Signal inputData = new Signal();
        //inputData.fromArray(new double[]{2,3,-1});

        double Fs = 44100;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));
        for (double freq: NoteCorrector.getNoteFreqs()
                ) {
            System.out.print("in :" + freq);
            double t_step = 1 / Fs;
            double alpha_bruit ;
            if(freq < 130 ) alpha_bruit = 0.05; //premiere gamme plus sensible au bruit
            else alpha_bruit = 0.1;
            for (int i = 0; i < inputData.length(); i++) {
                inputData.setValue(i, Math.sin(2.0 * Math.PI * freq * i * t_step) + Math.random() * alpha_bruit);
            }

            // test setup
            Frequency outputData = new Frequency();
            TestBuilder<Signal, Frequency> test = new TestBuilder<>(inputData, outputData,
                    new StreamSimpleBase<>(new Signal(), new Frequency(), new FreqProcess())
            );

            for (int i = 0; i < 5; i++) {

                test.startTest();
            }

            System.out.println(" => out :" +outputData.getFrequency());

            assertEquals(freq, outputData.getFrequency(), Double.MIN_VALUE);
        }

        //TODO real test with results
    }
}
