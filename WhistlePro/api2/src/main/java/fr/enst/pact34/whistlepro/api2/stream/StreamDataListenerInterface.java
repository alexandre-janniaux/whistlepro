package  fr.enst.pact34.whistlepro.api2.stream;

public interface StreamDataListenerInterface<E> {

    void fillBufferIn(E data);

    int getInputState();
}
