package  fr.enst.pact34.whistlepro.api2.phantoms;

import  fr.enst.pact34.whistlepro.api2.stream.DataListenerInterface;

/**
 * Created by mms on 15/03/16.
 */
public class FakeStreamDest<E> implements DataListenerInterface<E> {

    E bufferOut = null;

    public FakeStreamDest(E bufferOut) {
        this.bufferOut = bufferOut;
    }

    @Override
    public void bufferFilled() {

    }

    @Override
    public E getBufferToFill() {
        return bufferOut;
    }
}
