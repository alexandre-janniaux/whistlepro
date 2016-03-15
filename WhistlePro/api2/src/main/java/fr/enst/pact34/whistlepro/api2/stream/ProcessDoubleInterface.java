package main.java.fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 14/03/16.
 */
public interface ProcessDoubleInterface<E, F, G> {

    void process(E inputData,F inputData2, G outputData);
}
