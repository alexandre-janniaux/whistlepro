package fr.enst.pact34.whistlepro.api2.test.common;

import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamSource;

/**
 * Created by mms on 15/03/16.
 */
public class StreamDataPutter<E extends StreamDataInterface<E>> extends StreamSource<E> {

    E buffer = null;

    public StreamDataPutter(E buffer) {
        this.buffer = buffer;
    }

    @Override
    protected E getBufferOut() {
        return buffer;
    }
}
