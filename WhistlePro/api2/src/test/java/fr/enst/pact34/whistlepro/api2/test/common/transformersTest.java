package fr.enst.pact34.whistlepro.api2.test.common;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.FFTCalculator;
import fr.enst.pact34.whistlepro.api2.common.transformers;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 23/04/16.
 */
public class transformersTest {

    @Test
    public void testPerfFFT()
    {
        String inputDataFile = "../testData/features/signal.data";
        String outputDataFile = "../testData/features/fft.data";
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        Spectrum outputDataRef = TestUtils.createSpectrumFromFile(outputDataFile);
        Spectrum outputData ;
        long t;
        boolean skipAssertion = false;
        int i_max =1;
        //les premieres fois sont plus longue O.o
        //inputData.setLength((int)(0.020*inputData.getSamplingFrequency())); skipAssertion=true;i_max=10;// assertion will fail
        System.out.println("input sig length :" + 1000 * inputData.length() / inputData.getSamplingFrequency() + " ms");

        // ************************************************************************ //

        transformers fftCalc = new transformers();

        outputData = new Spectrum();
        for(int i = 0; i < i_max;i++) {
            outputData = new Spectrum();
            t = System.currentTimeMillis();
            fftCalc.fft(inputData, outputData);
            System.out.println("Time v1 : " + (System.currentTimeMillis() - t));
        }

        if(skipAssertion==false) {
            assertEquals(outputDataRef.length(), outputData.length());

            assertEquals(outputDataRef.getNbPtsSig(), outputData.getNbPtsSig());

            assertEquals(outputDataRef.getFs(), outputData.getFs(),
                    Math.max(outputDataRef.getFs() * 1E-3, 1E-14));

            for (int i = 0; i < outputDataRef.length(); i++) {
                assertEquals(outputDataRef.getValue(i), outputData.getValue(i),
                        Math.max(Math.abs(outputDataRef.getValue(i) * 1E-3), 1E-14));
            }
        }
        // ************************************************************************ //
        FFTCalculator fftCalculator = new FFTCalculator(inputData.length());
        for(int i = 0; i < i_max;i++) {
            outputData = new Spectrum();
            t = System.currentTimeMillis();
            fftCalculator.fft(inputData, outputData);
            System.out.println("Time v2 : " + (System.currentTimeMillis() - t));
        }
        if(skipAssertion==false) {
            assertEquals(outputDataRef.length(), outputData.length());

            assertEquals(outputDataRef.getNbPtsSig(), outputData.getNbPtsSig());

            assertEquals(outputDataRef.getFs(), outputData.getFs(),
                    Math.max(outputDataRef.getFs() * 1E-3, 1E-14));

            //for (int i = 0; i < outputDataRef.length(); i++) {
            //    assertEquals(outputDataRef.getValue(i), outputData.getValue(i),
            //            Math.max(Math.abs(outputDataRef.getValue(i) * 1E-3), 1E-14));
            //}
        }
    }
}
