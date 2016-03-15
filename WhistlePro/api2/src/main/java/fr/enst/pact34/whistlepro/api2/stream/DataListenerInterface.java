package  fr.enst.pact34.whistlepro.api2.stream;

public interface DataListenerInterface<E> {

    void bufferFilled();

    E getBufferToFill();


}
