package  fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 14/03/16.
 */
public interface StreamProcessInterface<E, F> {

    void process(E inputData, F outputData);
}
