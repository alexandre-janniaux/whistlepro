package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.ProcessInterface;

/**
 * Created by mms on 15/03/16.
 */
public class SpectrumProcess implements ProcessInterface<Signal,Signal>{

    @Override
    public void process(Signal inputData, Signal outputData) {

        transformers.fft(inputData,outputData);

    }
}
