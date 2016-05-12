package fr.enst.pact34.whistlepro.api2.test.utils;

import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamDestBase;

/**
 * Created by mms on 15/03/16.
 */
public class StreamDataEnd<E extends StreamDataInterface<E>> extends StreamDestBase<E> {

    E buffer = null;

    public StreamDataEnd(E bufferIn) {
        super(bufferIn);
        buffer=bufferIn;
    }

    public E getBuffer() {
        return buffer;
    }
}
