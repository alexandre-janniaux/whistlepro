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

        //inputData.setLength((int)(0.020*inputData.getSamplingFrequency())); skipAssertion=true;// assertion will fail
        System.out.println("input sig length :" + 1000 * inputData.length() / inputData.getSamplingFrequency() + " ms");

        // ************************************************************************ //

        outputData = new Spectrum();
        transformers fftCalc = new transformers();

        t = System.currentTimeMillis();
        fftCalc.fft(inputData, outputData);
        System.out.println("Time : " + (System.currentTimeMillis() - t));

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
        outputData = new Spectrum();
        t = System.currentTimeMillis();
        fftCalculator.fft(inputData,outputData);
        System.out.println("Time : " + (System.currentTimeMillis()-t));

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
    }
}
