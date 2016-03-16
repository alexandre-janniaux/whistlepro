package fr.enst.pact34.whistlepro.api2.test.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamDest;

/**
 * Created by mms on 15/03/16.
 */
public class StreamDataEnd<E extends StreamDataInterface<E>> extends StreamDest<E> {

    E buffer = null;

    public StreamDataEnd(E bufferIn) {
        super(bufferIn);
        buffer=bufferIn;
    }

    public E getBuffer() {
        return buffer;
    }
}
