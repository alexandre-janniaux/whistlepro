package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 15/03/16.
 */
public class SpectrumProcess implements StreamProcessInterface<Signal,Signal> {

    @Override
    public void process(Signal inputData, Signal outputData) {

        //TODO do transfert in Stream ?
        outputData.setSamplingFrequency(inputData.getSamplingFrequency());

        transformers.fft(inputData,outputData);

    }
}
