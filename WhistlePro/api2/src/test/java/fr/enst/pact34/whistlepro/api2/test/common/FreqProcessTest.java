package fr.enst.pact34.whistlepro.api2.test.common;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.FreqProcess;
import fr.enst.pact34.whistlepro.api2.common.NoteCorrector;
import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.TestBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by mms on 23/04/16.
 */
public class FreqProcessTest {

    @Test
    public void freqProcess_16000_Test()
    {
        //System.out.println("Le test ne marche pas toujours a cause du bruit aleatoire. ");

        Signal inputData = new Signal();
        //inputData.fromArray(new double[]{2,3,-1});

        double Fs = 16000;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));
        int nbTest = 10;
        int nberr = 1;
        for (double freq: NoteCorrector.getNoteFreqs()
             ) {
            int countSuccess = 0;
            for(int j = 0; j < nbTest; j ++) {
                System.out.print("in " + freq+",");
                double t_step = 1 / Fs;
                double alpha_bruit = 0.8;
                for (int i = 0; i < inputData.length(); i++) {
                    inputData.setValue(i, Math.sin(2.0 * Math.PI * freq * i * t_step) + Math.random() *Math.random() * alpha_bruit);
                }


                // test setup
                Frequency outputData = new Frequency();
                TestBuilder<Signal, Frequency> test = new TestBuilder<>(inputData, outputData,
                        new StreamSimpleBase<>(new Signal(), new Frequency(), new FreqProcess((int) Fs,inputData.length()))
                );


                test.startTest();


                System.out.print(" => out :" + outputData.getFrequency());
//*
                if(outputData.getFrequency()!=freq) System.out.println(" =================== > err n°" + nberr++);
                else System.out.println();
//*/              System.out.println();
                if(freq == outputData.getFrequency()) countSuccess++;
            }

            //assertTrue(((double)countSuccess)/nbTest >= 0.8);
        }

    }


    @Test
    public void freqProcess_44100_Test()
    {
        //System.out.println("Le test ne marche pas toujours a cause du bruit aleatoire. ");

        Signal inputData = new Signal();
        //inputData.fromArray(new double[]{2,3,-1});

        double Fs = 44100;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));
        int nbTest = 10;
        for (double freq: NoteCorrector.getNoteFreqs()
                ) {
            int countSuccess = 0;
            for(int j = 0; j < nbTest; j ++) {
                System.out.print("in :" + freq);
                double t_step = 1 / Fs;
                double alpha_bruit;
                if (freq < 130) alpha_bruit = 0.05; //premiere gamme plus sensible au bruit
                else alpha_bruit = 0.1;
                for (int i = 0; i < inputData.length(); i++) {
                    inputData.setValue(i, Math.sin(2.0 * Math.PI * freq * i * t_step) + Math.random() * alpha_bruit);
                }

                // test setup
                Frequency outputData = new Frequency();
                TestBuilder<Signal, Frequency> test = new TestBuilder<>(inputData, outputData,
                        new StreamSimpleBase<>(new Signal(), new Frequency(), new FreqProcess((int)Fs,inputData.length()))
                );

                for (int i = 0; i < 5; i++) {

                    test.startTest();
                }

                System.out.println(" => out :" + outputData.getFrequency());

                if (freq == outputData.getFrequency()) countSuccess++;
                try {
                    wait(0,100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            assertTrue(((double)countSuccess)/nbTest >= 0.8);
        }

        //TODO real test with results
    }
}
