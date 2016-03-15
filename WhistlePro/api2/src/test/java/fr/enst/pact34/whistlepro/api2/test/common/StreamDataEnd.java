package fr.enst.pact34.whistlepro.api2.test.common;

import fr.enst.pact34.whistlepro.api2.stream.DataListenerInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class StreamDataEnd<E extends StreamDataInterface<E>> implements DataListenerInterface<E> {

    E buffer = null;

    public StreamDataEnd(E buffer) {
        this.buffer = buffer;
    }

    @Override
    public void bufferFilled() {

    }

    @Override
    public E getBufferToFill() {
        return buffer;
    }
}
