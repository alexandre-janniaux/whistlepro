package main.java.fr.enst.pact34.whistlepro.api2.phantoms;

import main.java.fr.enst.pact34.whistlepro.api2.stream.ProcessInterface;
import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class FakeProcessCopy<E extends StreamDataInterface> implements ProcessInterface<E,E> {
    @Override
    public void process(E inputData, E outputData) {
        inputData.copyTo(outputData);
    }
}
