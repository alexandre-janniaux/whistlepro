package  fr.enst.pact34.whistlepro.api2.stream;

interface DataListenerInterface<E> {

    void fillBufferIn(E data);

}
