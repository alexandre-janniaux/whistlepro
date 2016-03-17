package  fr.enst.pact34.whistlepro.api2.stream;

interface StreamDataListenerInterface<E> {

    void fillBufferIn(E data);

}
