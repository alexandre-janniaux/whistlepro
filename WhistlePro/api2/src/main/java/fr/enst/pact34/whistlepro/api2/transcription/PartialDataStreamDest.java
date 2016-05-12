package fr.enst.pact34.whistlepro.api2.transcription;

import java.util.Observer;

import fr.enst.pact34.whistlepro.api2.stream.States;
import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamDataListenerInterface;

/**
 * Created by mms on 17/04/16.
 */
public class PartialDataStreamDest<E extends StreamDataInterface<E>> implements StreamDataListenerInterface<E> {

    private final PartialDataStreamParent parent;
    private E bufferIn = null;

    public PartialDataStreamDest(E bufferIn, PartialDataStreamParent parent) {
        this.bufferIn = bufferIn;
        this.parent = parent;
    }

    protected final E getBufferIn()
    {
        return bufferIn;
    }

    @Override
    public final void fillBufferIn(E data) {
        synchronized (data)
        {
            synchronized (bufferIn) {
                data.copyTo(bufferIn);
            }
        }
        synchronized (bufferIn) {
            E c = bufferIn.getNew();
            bufferIn.copyTo(c);
            parent.newData(this, c);
        }
    }

    @Override
    public int getInputState() {
        return States.INPUT_WAITING;
    }
}
