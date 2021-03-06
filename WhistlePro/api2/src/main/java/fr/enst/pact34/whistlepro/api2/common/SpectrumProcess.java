package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 15/03/16.
 */
public class SpectrumProcess implements StreamProcessInterface<Signal,Spectrum> {

    FFTCalculator fftCalc ;

    public SpectrumProcess(int sigLength)
    {
        fftCalc = new FFTCalculator(sigLength);
    }

    @Override
    public void process(Signal inputData, Spectrum outputData) {

        outputData.setFs(inputData.getSamplingFrequency());

        fftCalc.fft(inputData,outputData);

    }

    @Override
    public void reset() {

    }
}
