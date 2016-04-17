package fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 16/03/16.
 */
public class StreamDestBase<E extends StreamDataInterface<E>> implements StreamDataListenerInterface<E> {

    private E bufferIn = null;

    public StreamDestBase(E bufferIn) {
        this.bufferIn = bufferIn;
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
    }

    @Override
    public States getInputState() {
        return States.INPUT_WAITING;
    }
}
