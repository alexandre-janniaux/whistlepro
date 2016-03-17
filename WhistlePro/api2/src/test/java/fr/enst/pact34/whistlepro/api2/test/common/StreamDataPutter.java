package fr.enst.pact34.whistlepro.api2.test.common;

import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamSourceBase;

/**
 * Created by mms on 15/03/16.
 */
public class StreamDataPutter<E extends StreamDataInterface<E>> extends StreamSourceBase<E> {


    public StreamDataPutter(E bufferOut) {
        super(bufferOut);
    }
}
